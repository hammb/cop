package a2;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class OddEvenQueues {

	long odd = 0;
	long even = 0;

	LinkedBlockingQueue<Integer> lbq = new LinkedBlockingQueue<Integer>();
	LinkedBlockingQueue<Integer> lbq_odd = new LinkedBlockingQueue<Integer>();
	LinkedBlockingQueue<Integer> lbq_even = new LinkedBlockingQueue<Integer>();

	AtomicBoolean done = new AtomicBoolean(false);

	public void start() throws InterruptedException {
		OddEvenQueuesConsumerThread consumer1 = new OddEvenQueuesConsumerThread(this.lbq_odd, this.done, false);
		OddEvenQueuesConsumerThread consumer2 = new OddEvenQueuesConsumerThread(this.lbq_even, this.done, true);

		OddEvenQueuesManagerThread manager = new OddEvenQueuesManagerThread(this.lbq, this.lbq_odd, this.lbq_even,
				this.done);

		Thread[] threads = new Thread[10];

		int i;
		for (i = 0; i < 10; i++) {
			threads[i] = new OddEvenQueuesProducerThread(this.lbq, this.done);
		}

		for (i = 0; i < 10; i++) {
			threads[i].start();
		}

		consumer1.start();
		consumer2.start();
		manager.start();

		for (i = 0; i < 10; i++) {
			threads[i].join();
		}

		System.out.println("done");

		this.done.set(true);

		consumer1.join();
		consumer2.join();
		manager.join();

		System.out.println("Sum odd: " + consumer1.getSum() + " | Sum even: " + consumer2.getSum());
		
	}

	public static void main(String[] args) throws InterruptedException {
		OddEvenQueues oe = new OddEvenQueues();
		oe.start();
	}

}
