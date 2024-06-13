package Test;

import entity.Message;
import entity.MessageQueue;

import java.util.concurrent.LinkedBlockingQueue;

public class MessageQueueTest {

    public static void main(String[] args) {
        testSuccessfulMessageProcessing();
        testErrorInMessageProcessing();
        testQueueFull();
    }

    public static void testSuccessfulMessageProcessing() {
        MessageQueue messageQueue = new MessageQueue(new LinkedBlockingQueue<>(5));
        Message message = new Message("Test Message");
        messageQueue.processMessage(message);
        assert messageQueue.getSuccessCount() == 1;
        assert messageQueue.getErrorCount() == 0;
    }

    public static void testErrorInMessageProcessing() {
        MessageQueue messageQueue = new MessageQueue(new LinkedBlockingQueue<>(5));
        Message errorMessage = new Message("Error Message");
        messageQueue.processMessage(errorMessage);
        assert messageQueue.getSuccessCount() == 0;
        assert messageQueue.getErrorCount() == 1;
    }

    public static void testQueueFull() {
        int MAX_QUEUE_SIZE = 5;
        MessageQueue messageQueue = new MessageQueue(new LinkedBlockingQueue<>(MAX_QUEUE_SIZE));
        for (int i = 0; i < MAX_QUEUE_SIZE; i++) {
            messageQueue.addMessage(new Message("Message " + i));
        }
        Message extraMessage = new Message("Extra Message");

        messageQueue.addMessage(extraMessage);

        assert messageQueue.getMessageQueue().size() ==MAX_QUEUE_SIZE;

        assert messageQueue.getSuccessCount() == 0;
        assert messageQueue.getErrorCount() == 0;
        }
}
