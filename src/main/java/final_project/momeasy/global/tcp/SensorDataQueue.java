package final_project.momeasy.global.tcp;

import jakarta.persistence.Column;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class SensorDataQueue {
    public static final int CAPACITY = 100000;
    public final BlockingQueue<String> queue = new LinkedBlockingQueue<String>(CAPACITY);
}
