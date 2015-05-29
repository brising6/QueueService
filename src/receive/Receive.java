package receive;

import java.io.IOException;
import java.util.ArrayList;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class Receive {
	
	private final static String QUEUE_NAME = "queue";
	private ConnectionFactory factory;
	private Connection connection;
	private Channel channel;
	private QueueingConsumer consumer;
	
	public Receive() throws IOException{
		factory = new ConnectionFactory();
		factory.setHost("localhost");
		connection = factory.newConnection();
		channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		consumer = new QueueingConsumer(channel);
		channel.basicConsume(QUEUE_NAME, true, consumer);
		System.out.println(" [x] Receive node set up on port " + connection.getPort());
	}
	
	public void listen() throws java.io.IOException, ShutdownSignalException, InterruptedException {
		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			System.out.println(" [x] Received Message: '" + message + "'");
		}
	}
	
	public String receiveOnce() throws java.io.IOException, ShutdownSignalException, InterruptedException {
		QueueingConsumer.Delivery delivery = consumer.nextDelivery();
		String message = new String(delivery.getBody());
		System.out.println(" [x] Received Message: '" + message + "'");
		return message;
	}
	
	public void close() throws IOException{
		if (channel!=null){
			channel.close();
	        connection.close();
		}
	}


	public static void main(String args[]) throws IOException, ShutdownSignalException, InterruptedException{
		Receive rec = new Receive();
		System.out.println(" [x] To stop press CTRL+C");
		rec.listen();
	}
}
