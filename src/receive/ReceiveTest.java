package receive;

import java.io.IOException;

import send.Send;

import com.rabbitmq.client.ShutdownSignalException;

import junit.framework.TestCase;

/**
 * The class <code>ReceiveTest</code> contains tests for the class {@link
 * <code>Receive</code>}
 *
 * @pattern JUnit Test Case
 *
 * @generatedBy CodePro at 5/29/15 8:56 AM
 *
 * @author BXR5378
 *
 * @version $Revision$
 */
public class ReceiveTest extends TestCase {
	Send sender;
	Receive receiver;
	/**
	 * Construct new test instance
	 *
	 * @param name the test name
	 */
	public ReceiveTest(String name) {
		super(name);
	}

	/**
	 * Perform pre-test initialization
	 *
	 * @throws Exception
	 *
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		sender = new Send();
		receiver = new Receive();
		String clear;
	}

	/**
	 * Perform post-test clean up
	 *
	 * @throws Exception
	 *
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		sender.close();
		receiver.close();
	}

	/**
	 * Run the void listen() method test
	 * @throws InterruptedException 
	 * @throws IOException 
	 * @throws ShutdownSignalException 
	 */
	public void testReceiveOnce() throws ShutdownSignalException, IOException, InterruptedException {
		String testString = "testStr";
		sender.sendData(testString);
		String received = receiver.receiveOnce();
		assertEquals(testString, received);
	}
	
	public void testMultipleReceive() throws ShutdownSignalException, IOException, InterruptedException {
		sender.sendData("test1");
		sender.sendData("test2");
		sender.sendData("test3");
		sender.sendData("test4");
		String message = receiver.receiveOnce();
		message = receiver.receiveOnce();
		message = receiver.receiveOnce();
		String otherString = receiver.receiveOnce();
		assertEquals("test3", message);
	}
	
	public void testWorkReceivers() throws ShutdownSignalException, IOException, InterruptedException {
		sender.sendData("test1");
		String message1 = receiver.receiveOnce();
		sender.sendData("wait");
		sender.sendData("test4");
		String message2 = receiver.retriveOnceTime();
		message2 = receiver.retriveOnceTime();
		assertEquals("test4", message2);
	}
}