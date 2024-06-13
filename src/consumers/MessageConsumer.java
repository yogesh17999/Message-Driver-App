package consumers;

import entity.Message;
import entity.MessageQueue;

import java.util.logging.Logger;

public class MessageConsumer implements Runnable {
    Logger logger = Logger.getLogger(MessageConsumer.class.getName());

    private final MessageQueue messageQueue;

    public MessageConsumer(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                logger.info("Consumer Thread Started "+Thread.currentThread().getName());
                Message message = messageQueue.getMessage();
                messageQueue.processMessage(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
