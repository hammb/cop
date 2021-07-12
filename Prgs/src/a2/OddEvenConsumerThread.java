package a2;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;

public class OddEvenConsumerThread extends Thread {
	private MyCondition condition;

	private Lock lock;

	private LinkedList<Integer> llist;

	AtomicBoolean done;

	long sum = 0;

	boolean oddEven = false;

	public OddEvenConsumerThread(MyCondition condition, Lock lock, LinkedList<Integer> llist, AtomicBoolean done,
			boolean oddEven) {
		this.condition = condition;
		this.lock = lock;
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
			
			synchronized (this.lock) {
				while (this.llist.isEmpty() && !this.done.get()) {
					try {
						this.condition.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (!this.llist.isEmpty())
					if (((Integer) this.llist.peek()).intValue() % 2 == 0 && this.oddEven) {
						value = this.llist.remove();
					} else if (((Integer) this.llist.peek()).intValue() % 2 != 0 && !this.oddEven) {
						value = this.llist.remove();
					}
			}
			if (value != null)
				this.sum += value.intValue();
		}
	}
}
