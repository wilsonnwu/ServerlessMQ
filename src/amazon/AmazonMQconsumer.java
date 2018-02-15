package amazon;


import org.apache.activemq.jms.pool.PooledConnectionFactory;
import java.util.Random;
import java.util.UUID;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

public class AmazonMQconsumer {

    public static void main(String[] args) throws JMSException {

        // Create a connection factory.
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("ssl://b-180629ee-2111-4330-a5b7-caa37767887a-1.mq.us-east-2.amazonaws.com:61617");

        connectionFactory.setUserName("***");
        connectionFactory.setPassword("***");
        String clientid = UUID.randomUUID().toString();     
        //connectionFactory.setClientID("Virtual");
        // Establish a connection for the consumer.
        // Note: Consumers should not use PooledConnectionFactory.
        Connection consumerConnection = connectionFactory.createConnection();
        //connectionFactory.setClientID("DurableTest");

        consumerConnection.start();

        // Create a session.
        Session consumerSession = consumerConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);

       // Topic consumerDestination = consumerSession.createTopic("VirtualTopic.Test");
        
        //Topic consumerDestination = consumerSession.createTopic("Consumer.ApplicationA.VirtualTopic.Orders");

        
        Destination consumerDestination = new ActiveMQQueue("Consumer.VTest.VirtualTopic.Test");
        
        //Destination consumerDestination = consumerSession.createQueue("Consumer.VTest.VirtualTopic.Test");
        
        // Create a message consumer from the session.
      //MessageConsumer consumer = consumerSession.createDurableSubscriber((consumerDestination) ,"consumer");
       
       MessageConsumer consumer = consumerSession.createConsumer(consumerDestination);
       
       //MessageConsumer consumer1 = session.createDurableSubscriber(topic, "consumer1", "", false);
        // Begin to wait for messages.
       Message consumerMessage = consumer.receive(1000);

         //Receive the message when it arrives.
        TextMessage consumerTextMessage = (TextMessage) consumerMessage;
        System.out.println("Message received: " + consumerTextMessage.getText());
         //Clean up the consumer.
        consumer.close();
        consumerSession.close();
        consumerConnection.close();
    }

}
