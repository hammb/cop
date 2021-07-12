package producerConsumer;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Consumer extends Thread {
	private Queue<Integer> queue;
	private Object qlock;
	AtomicBoolean done = new AtomicBoolean(false);
	long sum = 0, misses = 0;

	public Consumer(Queue<Integer> queue, Object qlock) {
		this.queue = queue;
		this.qlock = qlock;
	}

	public void run() {
		while (!done.get()) {
			Integer value = null;
			synchronized (qlock) {
				if (!queue.isEmpty()) {
					value = queue.remove();
				}
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
