package amazon;

/*
 * Copyright 2010-2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 */

import org.apache.activemq.jms.pool.PooledConnectionFactory;
import java.util.Random;
import java.util.ResourceBundle;
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
		ResourceBundle rb = ResourceBundle.getBundle("config");
		String url = rb.getString("connection.url");
		String usrname = rb.getString("username");
		String pwd = rb.getString("password");

		// Create a connection factory.
		
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

		connectionFactory.setUserName(usrname);
		connectionFactory.setPassword(pwd);
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
