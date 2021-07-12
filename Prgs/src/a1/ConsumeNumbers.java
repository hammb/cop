package a1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Semaphore;

// Instances of ConsumeNumbers may retrieve several numbers (one each 
// by calling next) from the SocketServer provided by ServeNumbers. 
// Do not change this file. Look at main for a simple usage of ConsumeNumbers.
public class ConsumeNumbers {
	private final static String HOST = "localhost";
	private final static int PORT = ServeNumbers.PORT;

	// We need to ratelimit the connection according to the available Backlog buffer
	private final static Semaphore ratelimiter = new Semaphore(ServeNumbers.BACKLOG);
	
	// Horrible design on purpose, one connection per number.
	// However, we must ratelimit it, such that even huge number of instances do not allow 
	// more connections than the Backlog-Limit on the ServeNumbers. Does not help
	// against running several programs though. Should be fine for our use case...
	// We do no longer experience troubles if too many try to connect, however
	// we also do not feel the pain if requesting to many in parallel and needing
	// them; we only experience the pain if we request too many and do not need them.
	// Thus, on one exercise people will just find out where the ratelimit is and
	// unfortunately, it won't get worse after that (I may change the exercise, which
	// (originally was designed with jersey and worked quite well).
	public Long next() {
		Socket socket = null;
		DataOutputStream dos = null;
		DataInputStream dis = null;
		long value;
		boolean read = false;
		boolean acquired = false;
		try {
			try {
				ratelimiter.acquire();
				acquired = true;
			} catch (InterruptedException ignore) {
				System.err.println("CosumeNumbers: interrupted while acquiring rate limit");
			}
			socket = new Socket(HOST, PORT);
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			dos.writeInt(ServeNumbers.CMD_NEXT);
			value = dis.readLong();
			read = true;
			dos.writeInt(ServeNumbers.CMD_END);
		} catch (IOException e) {
			try {
				if (dos != null) dos.close();
			} catch (IOException ignore) {
			}
			try {
				if (dis != null) dis.close();
			} catch (IOException ignore) {
			}
			throw new RuntimeException(e);
		} finally {
			if (acquired) {
				ratelimiter.release();
			}
			try {
				if (socket != null) socket.close();			
			} catch (IOException ignore) {
			}
		}
		if (read) {
			return value;
		} else {
			throw new RuntimeException("read but could not send CMD_END");
		}
	}

	public static void main(String[] args) {
		final ConsumeNumbers consumeNumbers = new ConsumeNumbers();
		for (int i=0; i<17; i++) {
		    long number = consumeNumbers.next();
			System.out.format("%d ",  number);
			System.out.flush();
		}
		System.out.println("done");		
	}
}