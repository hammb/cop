package a2;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class OddEvenQueuesManagerThread extends Thread {

	private LinkedBlockingQueue<Integer> lbq;
	private LinkedBlockingQueue<Integer> lbq_odd;
	private LinkedBlockingQueue<Integer> lbq_even;

	AtomicBoolean done;

	int num_numbers = 5000;

	public OddEvenQueuesManagerThread(LinkedBlockingQueue<Integer> lbq, LinkedBlockingQueue<Integer> lbq_odd,
			LinkedBlockingQueue<Integer> lbq_even, AtomicBoolean done) {
		this.lbq = lbq;
		this.lbq_odd = lbq_odd;
		this.lbq_even = lbq_even;
		this.done = done;
	}

	public void run() {
		while (!this.done.get()) {
			if (!this.lbq.isEmpty()) {
				if (((Integer) this.lbq.peek()).intValue() % 2 == 0) {
					this.lbq_even.add(this.lbq.remove());
				} else if (((Integer) this.lbq.peek()).intValue() % 2 != 0) {
					this.lbq_odd.add(this.lbq.remove());
				}
			}
		}
	}
}