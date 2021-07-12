package producerConsumer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TwoConsumerWait extends Thread {
	private Queue<Integer> queue;
	private Object qlock;
	AtomicBoolean done = new AtomicBoolean(false);
	long sum = 0, misses = 0;

	public TwoConsumerWait(Queue<Integer> queue, Object qlock) {
		this.queue = queue;
		this.qlock = qlock;
	}

	public void run() {
		while (!done.get()) {
			Integer value = null;
			try {
				synchronized (qlock) {
					while (queue.isEmpty() && !done.get()) {
						qlock.wait();
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
		
		TwoConsumerWait consumer1 = new TwoConsumerWait(queue, qlock);
		TwoConsumerWait consumer2 = new TwoConsumerWait(queue, qlock);
		producer.start();
		consumer1.start();
		consumer2.start();
		try {
			producer.join();
			consumer1.done.set(true);
			consumer2.done.set(true);
			synchronized (qlock) {
				qlock.notifyAll();
			}
			consumer1.join();
			consumer2.join();
			Util.format("sum %d\n (%d+%d)\n misses: %d\n", consumer1.sum + consumer2.sum, consumer1.sum, consumer2.sum,
					consumer1.misses + consumer2.misses);
		} catch (InterruptedException e) {
		}
	}
}
