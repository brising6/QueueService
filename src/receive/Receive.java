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
		channel.basicQos(1);
		consumer = new QueueingConsumer(channel);
	    channel.basicConsume(QUEUE_NAME, false, consumer);
		
		System.out.println(" [x] Receive node set up on port " + connection.getPort());
	}
	
	public void listen() throws java.io.IOException, ShutdownSignalException, InterruptedException {
		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
			System.out.println(" [x] Received '" + message + "'");  
			if (message.equals("wait")){
				System.out.println(" [x] waiting...");
				Thread.sleep(12000);
			}
		}
	}
	
	public String receiveOnce() throws java.io.IOException, ShutdownSignalException, InterruptedException {
		QueueingConsumer.Delivery delivery = consumer.nextDelivery();
		String message = new String(delivery.getBody());
		channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		System.out.println(" [x] Received Message: '" + message + "'");
		return message;
	}
	
	public String retriveOnceTime() throws java.io.IOException, ShutdownSignalException, InterruptedException {
		QueueingConsumer.Delivery delivery = consumer.nextDelivery();
		String message = new String(delivery.getBody());
		channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		System.out.println(" [x] Received '" + message + "'");  
		if (message.split(" ")[0].equals("wait")){
			System.out.println(" [x] Performing Job");
			Thread.sleep(2000);
			System.out.println(" [x] Job Complete");
		}
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
