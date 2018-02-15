package amazon;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.activemq.ActiveMQConnectionFactory;

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

public class AmazonMQExample extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) throws JMSException {

		// Create a connection factory.
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"ssl://b-180629ee-2111-4330-a5b7-caa37767887a-1.mq.us-east-2.amazonaws.com:61617");

		connectionFactory.setUserName("admin");
		connectionFactory.setPassword("Activemq@123");

		// Create a pooled connection factory.
		PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
		pooledConnectionFactory.setConnectionFactory(connectionFactory);
		pooledConnectionFactory.setMaxConnections(10);

		// Establish a connection for the producer.
		Connection producerConnection = pooledConnectionFactory.createConnection();
		producerConnection.start();

		// Create a session.
		Session producerSession = producerConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// Create a queue named "MyQueue".
		// Destination producerDestination = producerSession.createQueue("MyQueue");

		Destination producerDestination = producerSession.createTopic("VirtualTopic.Test");

		// Create a producer from the session to the queue.
		MessageProducer producer = producerSession.createProducer(producerDestination);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

		// Create a message.
		String text = "Hello from Amazon MQ!";
		TextMessage producerMessage = producerSession.createTextMessage(text);

		// Send the message.
		producer.send(producerMessage);
		System.out.println("Message sent.");

		// Clean up the producer.
		producer.close();
		producerSession.close();
		producerConnection.close();
		pooledConnectionFactory.stop();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
		// Create a connection factory.
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"ssl://b-180629ee-2111-4330-a5b7-caa37767887a-1.mq.us-east-2.amazonaws.com:61617");

		connectionFactory.setUserName("admin");
		connectionFactory.setPassword("Activemq@123");

		// Create a pooled connection factory.
		PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
		pooledConnectionFactory.setConnectionFactory(connectionFactory);
		pooledConnectionFactory.setMaxConnections(10);

		// Establish a connection for the producer.
		Connection producerConnection = pooledConnectionFactory.createConnection();
		producerConnection.start();

		// Create a session.
		Session producerSession = producerConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// Create a queue named "MyQueue".
		// Destination producerDestination = producerSession.createQueue("MyQueue");

		Destination producerDestination = producerSession.createTopic("VirtualTopic.Test");

		// Create a producer from the session to the queue.
		MessageProducer producer = producerSession.createProducer(producerDestination);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

		// Create a message.
		String text = "Hello from Amazon MQ!";
		TextMessage producerMessage = producerSession.createTextMessage(text);

		// Send the message.
		producer.send(producerMessage);
		System.out.println("Message sent.");

		// Clean up the producer.
		producer.close();
		producerSession.close();
		producerConnection.close();
		pooledConnectionFactory.stop();

		}
		catch(Exception e) {
			System.out.println("Stuff be broken");

		}
	}
}
