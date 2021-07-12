package a1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

// Run ServeNumbers as is independently. It serves numbers from 1 to MAX 
// serialized as a String via a SocketServer on the port PORT.
// Each request will take 50-150 ms to complete.
// Use an instance of ConsumeNumbers to consume a number.
// Do not change this file.

public class ServeNumbers {
	final static int MAX = 10_000;
	final static int PORT = 2417;
	final static int BACKLOG = 256; // default is 50
	// the backlog is most likely hurting, 
	// on linux this is often (cat /proc/sys/net/core/somaxconn) 128
	// we should not expect any higher number to work, but we may try

	final static int CMD_NEXT = 1;
	final static int CMD_END = 0;	
	
	private AtomicLong number;
	private Random random;
	private long max;
	private ServeNumbers(int max) {
		number = new AtomicLong(0);
		random = new Random();
		this.max = max;
	}

	// serve the next number
	// We simulate load (cpu time) plus wait tune and want to delay
	// 50 - 150 ms wall time between serving the next number
	public long next() {
		long ret = number.incrementAndGet();
		try {
			load(3); // cpuload in milliseconds; feel it, if too many are requested
			int sleepTime = 47 + random.nextInt(101);
			Thread.sleep(sleepTime); // sleep base + 0..100 ms, serve slowly
		} catch (InterruptedException ignore) {
		}
		return ret > max ? 0 : ret;
	}
	
	public void load(int ms) {
		long start = System.currentTimeMillis();
		while (System.currentTimeMillis() < start+ms) {
			int val = random.nextInt(); // load
			val = val < 0 ? 1 : val;
			int[] arr = new int[val%10000]; // and memory
			for (int i=0; i < arr.length; ++i) {
				arr[i] = val;
				val += 1;
			}
			val = val < 0 ? 1 : val;
			if (val <= -1) { // avoid clever opts?
				throw new RuntimeException("can never happen");
			}
		}
	}
	
	private class Handler implements Runnable {
		final private Socket socket;
		final private DataOutputStream dos;
		final private DataInputStream dis;
		public Handler(Socket socket) {
			this.socket = socket;
			try {
				dos = new DataOutputStream(socket.getOutputStream());
				dis = new DataInputStream(socket.getInputStream());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		@Override public void run() {
			try {
				boolean done = false;
				while (!done) {
					int command = dis.readInt();
					switch (command) {
					case CMD_NEXT:
						long value = next();
						dos.writeLong(value);
						break;
					case CMD_END:
						done = true;
						break;
					default:
						throw new RuntimeException("unknown command: " + command);
					}
				}
			} catch (EOFException ignore) {
				// ignore, did not set DONE
			} catch (IOException e) {
				throw new RuntimeException(e);
			} finally {
				try {
					dis.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				try {
					dos.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				try {
					socket.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	public void serve() throws IOException {
		ExecutorService processor = Executors.newCachedThreadPool();
		ServerSocket server = new ServerSocket(PORT, BACKLOG);
		try {
			while (!Thread.interrupted()) {
				Socket socket = server.accept();
				Handler handler = new Handler(socket);
				processor.execute(handler);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			server.close();
		}
	}
	
	public static void main(String[] args) {
		ServeNumbers serveNumbers = new ServeNumbers(MAX);
		try {
			serveNumbers.serve();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
