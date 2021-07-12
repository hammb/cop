package a2;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class OddEvenQueuesConsumerThread extends Thread {

	private LinkedBlockingQueue<Integer> llist;

	AtomicBoolean done;

	long sum = 0;

	boolean oddEven = false;

	public OddEvenQueuesConsumerThread(LinkedBlockingQueue<Integer> llist, AtomicBoolean done, boolean oddEven) {
		this.llist = llist;
		this.done = done;
		this.oddEven = oddEven;
	}

	public long getSum() {
		return this.sum;
	}

	public void run() {

		while (!this.done.get()) {

			Integer value = null;

			while (!this.llist.isEmpty() && !this.done.get()) {
				value = this.llist.remove();
				this.sum += value.intValue();
			}
			
			
		}
		
	}
}


