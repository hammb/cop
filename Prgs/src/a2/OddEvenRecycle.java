package a2;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class OddEvenRecycle {

	long odd = 0;
	long even = 0;

	LinkedBlockingQueue<Integer> lbq = new LinkedBlockingQueue<Integer>();

	AtomicBoolean done = new AtomicBoolean(false);

	public void start() throws InterruptedException {
		OddEvenRecycleConsumer consumer1 = new OddEvenRecycleConsumer(this.lbq, this.done, false);
		OddEvenRecycleConsumer consumer2 = new OddEvenRecycleConsumer(this.lbq, this.done, true);

		Thread[] threads = new Thread[10];

		int i;
		for (i = 0; i < 10; i++) {
			threads[i] = new OddEvenRecycleProducer(this.lbq, this.done);
		}

		for (i = 0; i < 10; i++) {
			threads[i].start();
		}

		consumer1.start();
		consumer2.start();

		for (i = 0; i < 10; i++) {
			threads[i].join();
		}

		System.out.println("done");

		this.done.set(true);

		consumer1.join();
		consumer2.join();

		System.out.println("Sum odd: " + consumer1.getSum() + " | Sum even: " + consumer2.getSum());
		
	}

	public static void main(String[] args) throws InterruptedException {
		OddEvenRecycle oe = new OddEvenRecycle();
		oe.start();
	}

}
