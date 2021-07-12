package producerConsumer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TwoConsumerWaitTimeout extends Thread {
	private Queue<Integer> queue;
	private Object qlock;
	AtomicBoolean done = new AtomicBoolean(false);
	long sum = 0, misses = 0;

	public TwoConsumerWaitTimeout(Queue<Integer> queue, Object qlock) {
		this.queue = queue;
		this.qlock = qlock;
	}

	public void run() {
		while (!done.get()) {
			Integer value = null;
			try {
				synchronized (qlock) {
					while (queue.isEmpty() && !done.get()) {
						qlock.wait(100); // in msecs
					}
					if (!queue.isEmpty()) {
						value = queue.remove();
					}
				}
			} catch (InterruptedException e) {
			}
			if (value != null) {
				sum += value;
			} else {
				misses += 1;
			}
		}
	}

	public static void main(String[] args) {
		Lock qlock = new ReentrantLock();
		int nMax = 1_000_000;
		LinkedList<Integer> queue = new LinkedList<Integer>();

		ProducerNotify producer = new ProducerNotify(queue, qlock, nMax);
		TwoConsumerWaitTimeout consumer1 = new TwoConsumerWaitTimeout(queue, qlock);
		TwoConsumerWaitTimeout consumer2 = new TwoConsumerWaitTimeout(queue, qlock);
		producer.start();
		consumer1.start();
		consumer2.start();
		try {
			producer.join();
			consumer1.done.set(true);
			consumer2.done.set(true);
			// no longer notifyAll
			consumer1.join();
			consumer2.join();
			Util.format("sum %d\n (%d+%d)\n misses: %d\n", consumer1.sum + consumer2.sum, consumer1.sum, consumer2.sum,
					consumer1.misses + consumer2.misses);
		} catch (InterruptedException e) {
		}
	}
}
