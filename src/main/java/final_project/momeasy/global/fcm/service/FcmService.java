package final_project.momeasy.global.fcm.service;

import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.ApsAlert;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MessagingErrorCode;
import com.google.firebase.messaging.Notification;
import final_project.momeasy.common.enums.DeviceType;
import final_project.momeasy.global.fcm.exception.FcmErrorCode;
import final_project.momeasy.global.fcm.exception.FcmException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FcmService {

    private final FcmTokenService tokenService;

    @Async
    public void sendNotification(String targetToken, String title, String body) {
        Message message = Message.builder()
                .setToken(targetToken)
                .setNotification(Notification.builder().setTitle(title).setBody(body).build())
                .build();
        doSend(targetToken, message);
    }

    @Async
    public void sendNotification(String targetToken, DeviceType deviceType, String title, String body, String deeplink, String type) {
        Message message = buildPerDeviceMessage(targetToken, deviceType, title, body, deeplink, type);
        doSend(targetToken, message);
    }


    @Async
    public void sendAnnouncementToTopic(String topic, String title, String body, String deeplink, String type) {
        AndroidConfig android = AndroidConfig.builder()
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .setChannelId("announcement")
                        .build())
                .putData("deeplink", deeplink == null ? "" : deeplink)
                .putData("type", type == null ? "" : type)
                .build();

        ApnsConfig apns = ApnsConfig.builder()
                .setAps(Aps.builder()
                        .setAlert(ApsAlert.builder().setTitle(title).setBody(body).build())
                        .setContentAvailable(true)
                        .build())
                .putCustomData("deeplink", deeplink == null ? "" : deeplink)
                .putCustomData("type", type == null ? "" : type)
                .build();

        Message message = Message.builder()
                .setTopic(topic)
                .setAndroidConfig(android)
                .setApnsConfig(apns)
                .build();

        doSend(null, message);
    }

    private Message buildPerDeviceMessage(String token, DeviceType deviceType, String title, String body, String deeplink, String type) {
        if (deviceType == DeviceType.IOS) {
            return Message.builder()
                    .setToken(token)
                    .setNotification(Notification.builder().setTitle(title).setBody(body).build())
                    .putData("deeplink", deeplink == null ? "" : deeplink)
                    .putData("type", type == null ? "" : type)
                    .build();
        } else if (deviceType == DeviceType.ANDROID) {
            return Message.builder()
                    .setToken(token)
                    .putData("title", title == null ? "" : title)
                    .putData("body", body == null ? "" : body)
                    .putData("deeplink", deeplink == null ? "" : deeplink)
                    .putData("type", type == null ? "" : type)
                    .build();
        } else {
            return Message.builder()
                    .setToken(token)
                    .setNotification(Notification.builder().setTitle(title).setBody(body).build())
                    .build();
        }
    }

    private void doSend(String targetTokenOrNull, Message message) {
        try {
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("FCM sent: {}", response);
        } catch (FirebaseMessagingException e) {
            MessagingErrorCode code = e.getMessagingErrorCode(); // ✅ Admin SDK 권장 방식
            log.warn("FCM failed: code={}, msg={}", code, e.getMessage(), e);

            if (code == MessagingErrorCode.UNREGISTERED && targetTokenOrNull != null) {
                tokenService.deleteTokenSilentlyIfExists(targetTokenOrNull);
                throw new FcmException(FcmErrorCode.UNREGISTERED_TOKEN);
            }
            if (code == MessagingErrorCode.INVALID_ARGUMENT) {
                throw new FcmException(FcmErrorCode.INVALID_REQUEST);
            }
            throw new FcmException(FcmErrorCode.UNKNOWN_SENDER_ERROR);
        } catch (Exception e) {
            log.error("FCM unexpected error", e);
            throw new FcmException(FcmErrorCode.UNKNOWN_SENDER_ERROR);
        }
    }
}
