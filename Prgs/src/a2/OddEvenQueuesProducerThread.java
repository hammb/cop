package a2;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class OddEvenQueuesProducerThread extends Thread {
	
	private LinkedBlockingQueue<Integer> llist;

	private Random random;

	AtomicBoolean done;

	int num_numbers = 1000;

	public OddEvenQueuesProducerThread(LinkedBlockingQueue<Integer> llist, AtomicBoolean done) {
		this.llist = llist;
		this.done = done;
		this.random = new Random();
	}

	public void run() {
		for (int i = 0; i < this.num_numbers; i++) {
			int sleepTime = this.random.nextInt(4);
			int value = this.random.nextInt(400);
			boolean added = false;

			added = this.llist.add(Integer.valueOf(value));

			if (added)
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
	}
}