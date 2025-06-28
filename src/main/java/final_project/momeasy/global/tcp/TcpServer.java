package final_project.momeasy.global.tcp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

@RequiredArgsConstructor
@Component
@Slf4j
public class TcpServer implements CommandLineRunner {

    private final SensorDataQueue sensorDataQueue;
    private final int port = 12345;

    @Override
    public void run(String... args) throws Exception {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            log.info("TCP Server is running on port {}", port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleClient(clientSocket)).start();
            }
        }catch(Exception e) {
            log.error("[Socket Error] {}", e.getMessage(), e);
        }
    }

    private void handleClient(Socket socket) {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                log.info("Received: {}", line);
                sensorDataQueue.queue.offer(line);
            }
        } catch (IOException e) {
            log.error("[Socket Error] {}", e.getMessage(), e);  // 꼭 로깅
        }
    }
}
