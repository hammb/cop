package producerConsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConsumerBlockingQueue extends Thread {
	private BlockingQueue<Integer> queue;
	AtomicBoolean done = new AtomicBoolean(false);
	long sum = 0, misses = 0;

	public ConsumerBlockingQueue(BlockingQueue<Integer> queue) {
		this.queue = queue;
	}

	public void run() {
		Integer value;
		try {
			while (!done.get()) {
				value = queue.take();
				sum += value;
			}
		} catch (InterruptedException e) {
			misses += 1;
			do { // drain
				value = queue.poll();
				if (value != null)
					sum += value;
			} while (value != null);
		}
	}
}
