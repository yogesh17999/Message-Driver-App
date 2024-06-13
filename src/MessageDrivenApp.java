import consumers.MessageConsumer;
import entity.MessageQueue;
import producers.MessageProducer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class MessageDrivenApp {
    public static void main(String[] args) {
        MessageQueue messageQueue = new MessageQueue(new LinkedBlockingQueue<>(5));

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(new MessageProducer(messageQueue));
        executor.execute(new MessageConsumer(messageQueue));

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Total messages processed successfully: " + messageQueue.getSuccessCount());
        System.out.println("Total errors encountered: " + messageQueue.getErrorCount());
    }
}