package producerConsumer;

import java.util.Queue;
import java.util.concurrent.locks.Lock;

public class ProducerTryLock extends Thread {
	private Queue<Integer> queue;
	private Lock qlock;
	private int howMany;

	public ProducerTryLock(Queue<Integer> queue, Lock qlock, int howMany) {
		this.queue = queue;
		this.qlock = qlock;
		this.howMany = howMany;
	}

	public void run() {
		for (int i = 1; i <= howMany; i++) {
			Thread.yield();
			qlock.lock();
			try {
				this.queue.add(i);
			} finally {
				qlock.unlock();
			}
		}
	}
}
