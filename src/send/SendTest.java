package send;

import java.io.IOException;

import com.rabbitmq.client.ShutdownSignalException;

import receive.Receive;
import junit.framework.TestCase;

/**
 * The class <code>SendTest</code> contains tests for the class {@link
 * <code>Send</code>}
 *
 * @pattern JUnit Test Case
 *
 * @generatedBy CodePro at 5/29/15 8:56 AM
 *
 * @author BXR5378
 *
 * @version $Revision$
 */
public class SendTest extends TestCase {
	Send sender;
	Receive receiver;
	/**
	 * Construct new test instance
	 *
	 * @param name the test name
	 */
	public SendTest(String name) {
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
	 * Run the void sendData(String) method test
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws ShutdownSignalException 
	 */
	public void testSendDataString() throws IOException, ShutdownSignalException, InterruptedException {
		String testString = "testStr";
		sender.sendData(testString);
		String received = receiver.receiveOnce();
		assertEquals(testString, received);
	}
	
	public void testMultiple() throws IOException, ShutdownSignalException, InterruptedException {
		Send newSender = new Send();
		String testString = "testStr";
		sender.sendData("asfd");
		newSender.sendData("sender2");
		String received = receiver.receiveOnce();
		received = receiver.receiveOnce();
		assertEquals("sender2", received);
	}
}
