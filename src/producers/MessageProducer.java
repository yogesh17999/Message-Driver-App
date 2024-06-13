package producers;

import entity.Message;
import entity.MessageQueue;

import java.util.logging.Logger;

public class MessageProducer implements  Runnable{

    Logger logger =  Logger.getLogger(MessageProducer.class.getName());
    private MessageQueue messageQueue;

    public MessageProducer(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            Message message = new Message( "Message " + i);
            messageQueue.addMessage(message);
            logger.info("Produces Thread Started "+Thread.currentThread().getName());
            logger.info("Message has been added to the queue");
            System.out.println("Produced message: " + message.getContent());
        }
    }
}
