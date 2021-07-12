package producerConsumer;

import java.util.Queue;

public class ProducerNotify extends Thread {
	private Queue<Integer> queue;
	private Object qlock;
	private int howMany;

	public ProducerNotify(Queue<Integer> queue, Object qlock, int howMany) {
		this.queue = queue;
		this.qlock = qlock;
		this.howMany = howMany;
	}

	public void run() {
		for (int i = 1; i <= howMany; i++) {
			Thread.yield();
			synchronized (qlock) {
				this.queue.add(i);
				this.qlock.notify();
			}
		}
	}
}
