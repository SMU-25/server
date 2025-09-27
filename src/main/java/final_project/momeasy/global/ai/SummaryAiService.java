package final_project.momeasy.global.ai;

import final_project.momeasy.common.enums.RecordState;
import final_project.momeasy.domain.fever_record.entity.FeverRecord;
import final_project.momeasy.domain.fever_record.exception.FeverRecordErrorCode;
import final_project.momeasy.domain.fever_record.exception.FeverRecordException;
import final_project.momeasy.domain.fever_record.repository.FeverRecordRepository;
import final_project.momeasy.domain.fever_report.dto.FeverReportRequestDTO;
import final_project.momeasy.domain.room_condition.entity.RoomCondition;
import final_project.momeasy.domain.room_condition.repository.RoomConditionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SummaryAiService {
    private final OpenAiChatModel openAiChatModel;
    private final FeverRecordRepository feverRecordRepository;
    private final RoomConditionRepository roomConditionRepository;

    public String create_summary(FeverReportRequestDTO.FeverReportCreateDTO feverReportRequestDTO, Long childId) {
        return generateSummary(feverReportRequestDTO.getOuting(), childId);
    }

    public String update_summary(FeverReportRequestDTO.FeverReportUpdateDTO feverReportRequestDTO, Long childId) {
        return generateSummary(feverReportRequestDTO.getOuting(), childId);
    }

    private String generateSummary(String outing, Long childId) {
        // 최근 발열 기록
        FeverRecord feverRecord = feverRecordRepository.findRecentFeverRecord(childId, RecordState.HUMAN)
                .orElseThrow(() -> new FeverRecordException(FeverRecordErrorCode.NOT_FOUND));
        float recentHighFever = feverRecord.getFever();
        LocalDateTime highFeverTime = feverRecord.getCreatedAt();

        // 고열 당시 환경
        List<RoomCondition> roomConditions = roomConditionRepository
                .findByChildIdAndCreatedAtBetween(childId, highFeverTime, highFeverTime.plusMinutes(5));
        RoomCondition roomCondition = roomConditions.getFirst();
        float temperature = roomCondition.getTemperature();
        float humidity = roomCondition.getHumidity();

        // 최근 체온 측정
        FeverRecord recentFeverRecord = feverRecordRepository.findTopByChildIdAndRecordStateOrderByCreatedAtDesc(childId,RecordState.HUMAN)
                .orElseThrow(() -> new FeverRecordException(FeverRecordErrorCode.NOT_FOUND));
        float recentFever = recentFeverRecord.getFever();
        LocalDateTime recentFeverTime = recentFeverRecord.getCreatedAt();

        String prompt = String.format("""
            다음은 아동의 건강 모니터링 기록입니다. AI는 아래 양식을 그대로 유지하며, 각 항목의 'AI 판단:' 부분에만 내용을 한 줄 분량으로 작성해주세요.
            기타 텍스트는 변경하지 말고, 그대로 유지해주세요. 체온이 37.0°C 이상이면 발열로 간주하면 됩니다. 최근 체온 측정의 판단에는 최근 발열 기록과 비교하여
            작성해주세요.

            1. 최근 발열 기록
            - 측정 시간: %s
            - 체온: %.1f°C
            - AI 판단: 

            2. 최근 외출
            - 외출 정보: %s
            - AI 판단: 

            3. 최근 체온 측정
            - 측정 시간: %s
            - 체온: %.1f°C
            - AI 판단: 

            4. 고열 당시 환경
            - 실내 온도: %.1f℃
            - 습도: %.1f%%
            - AI 판단: 
            """,
                highFeverTime,
                recentHighFever,
                outing,
                recentFeverTime,
                recentFever,
                temperature,
                humidity);

        return openAiChatModel.call(prompt);
    }
}
