package producerConsumer;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConsumerWait extends Thread {
	private Queue<Integer> queue;
	private Object qlock;
	AtomicBoolean done = new AtomicBoolean(false);
	long sum = 0, misses = 0;

	public ConsumerWait(Queue<Integer> queue, Object qlock) {
		this.queue = queue;
		this.qlock = qlock;
	}

	public void run() {
		while (!done.get()) {
			Integer value = null;
			synchronized (qlock) {
				try {
					while (queue.isEmpty()) {
						qlock.wait();
					}
					value = queue.remove();
				} catch (InterruptedException e) {
					// ignore, value == null
				}
			}
			if (value != null) {
				sum += value;
			} else {
				misses += 1;
			}
		}
	}
}
