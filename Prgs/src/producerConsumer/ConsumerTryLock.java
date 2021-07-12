package producerConsumer;

import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;

public class ConsumerTryLock extends Thread {
	private Queue<Integer> queue;
	private Lock qlock;
	AtomicBoolean done = new AtomicBoolean(false);
	long sum = 0, misses = 0;

	public ConsumerTryLock(Queue<Integer> queue, Lock qlock) {
		this.queue = queue;
		this.qlock = qlock;
	}

	public void run() {
		while (!done.get()) {
			Integer value = null;
			boolean hL; // holdLock
			try {
				hL = qlock.tryLock(100, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				continue; // no lock
			}
			if (!hL)
				continue; // no lock
			try { // hold lock
				if (!queue.isEmpty())
					value = queue.remove();
			} finally {
				qlock.unlock();
			}
			if (value != null) {
				sum += value;
			} else {
				Thread.yield();
				misses += 1;
			}
		}
	}
}
