package send;

import java.io.IOException;
import java.util.Scanner;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class Send {
	
	private final static String QUEUE_NAME = "queue";
	private ConnectionFactory factory;
	private Connection connection;
	private Channel channel;
	
	public Send() throws IOException {
		factory = new ConnectionFactory();
		factory.setHost("localhost");
	    connection = factory.newConnection();
	    channel = connection.createChannel();
	    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	}
	
	public void sendData(String data) throws java.io.IOException {
	    String message = data;
	    channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
	    System.out.println(" [x] Sent '" + message + "'");
	}
	
	public void close() throws IOException {
		if (channel!=null){
			channel.close();
	        connection.close();
		}
	}
	/*
	public static void main(String args[]) throws IOException{
		Send send = new Send();
		Scanner sc = new Scanner(System.in);
		System.out.println(" [x] To end type end");
		boolean flag = true;
		while(flag){
			String message = sc.next();
			if (message.equals("end")){
				send.close();
			}
			else{
				send.sendData(message);
			}
		}
	}*/
	
}