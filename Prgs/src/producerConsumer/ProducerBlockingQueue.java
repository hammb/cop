package producerConsumer;

import java.util.concurrent.BlockingQueue;

public class ProducerBlockingQueue extends Thread {
	private BlockingQueue<Integer> queue;
	private int howMany;

	public ProducerBlockingQueue(BlockingQueue<Integer> queue, int howMany) {
		this.queue = queue;
		this.howMany = howMany;
	}

	public void run() {
		for (int i = 1; i <= howMany; i++) {
			Thread.yield();
			try {
				queue.put(i);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
