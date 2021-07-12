package a2;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;

public class OddEvenProducerThread extends Thread {
	private MyCondition condition;

	private Lock lock;

	private LinkedList<Integer> llist;

	private Random random;

	AtomicBoolean done;

	int num_numbers = 5000;

	public OddEvenProducerThread(MyCondition condition, Lock lock, LinkedList<Integer> llist, AtomicBoolean done) {
		this.condition = condition;
		this.lock = lock;
		this.llist = llist;
		this.done = done;
		this.random = new Random();
	}

	public void run() {
		for (int i = 0; i < this.num_numbers; i++) {
			int sleepTime = this.random.nextInt(4);
			int value = this.random.nextInt(400);
			boolean added = false;
			synchronized (this.lock) {
				added = this.llist.add(Integer.valueOf(value));
				this.condition.signal();
			}
			if (added)
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
	}
}