package entity;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

public class MessageQueue {

    Logger logger = Logger.getLogger(MessageQueue.class.getName());

    private BlockingQueue<Message> messageQueue;
    private int successCount = 0;
    private int errorCount = 0;

    public MessageQueue(BlockingQueue<Message> messageQueue) {
        this.messageQueue = messageQueue;
    }

    public BlockingQueue<Message> getMessageQueue() {
        return messageQueue;
    }

    public void setMessageQueue(BlockingQueue<Message> messageQueue) {
        this.messageQueue = messageQueue;
    }

    public void addMessage(Message message) {
        try {
            messageQueue.put(message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public Message getMessage() throws InterruptedException {
        return messageQueue.take();
    }

    public void processMessage(Message message) {
        try {
            if (Math.random() < 0.5) {
                throw new RuntimeException("Error occurred while processing message: " + message.getContent());
            }
            successCount++;
            System.out.println("Consumed message: " + message.getContent());
            logger.info("Processed message: " + message.getContent() + " Size Of Queue: " + messageQueue.size());
        } catch (RuntimeException e) {
            errorCount++;
            System.out.println("Consumed message: " + message.getContent());
            logger.info("Error processing message: " + e.getMessage() + " Size Of Queue: " + messageQueue.size());
        }
    }

    public int getSuccessCount() {
        return successCount;
    }

    public int getErrorCount() {
        return errorCount;
    }
}
