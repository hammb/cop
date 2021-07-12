package a2;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class OddEvenRecycleConsumer extends Thread {

	private LinkedBlockingQueue<Integer> llist;

	AtomicBoolean done;

	long sum = 0;

	boolean oddEven = false;

	public OddEvenRecycleConsumer(LinkedBlockingQueue<Integer> llist, AtomicBoolean done, boolean oddEven) {
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
				
				if (value % 2 == 0 && !this.oddEven) {
					this.llist.add(value);
					continue;
				}
				
				if (value % 2 != 0 && this.oddEven) {
					this.llist.add(value);
					continue;
				}
				
				if (value % 2 == 0 && this.oddEven) {
					this.sum += value.intValue();
				}
				
				if (value % 2 != 0 && !this.oddEven) {
					this.sum += value.intValue();
				}
				
				
			}
			
			
		}
		
	}
}
