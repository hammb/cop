package a2;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class OddEven {

	long odd = 0;
	long even = 0;

	ReentrantLock lock = new ReentrantLock();

	MyCondition condition = new MyCondition(lock);

	LinkedList<Integer> llist = new LinkedList<Integer>();

	AtomicBoolean done = new AtomicBoolean(false);

	public void start() throws InterruptedException {
		OddEvenConsumerThread consumer1 = new OddEvenConsumerThread(this.condition, this.lock, this.llist, this.done,
				false);
		OddEvenConsumerThread consumer2 = new OddEvenConsumerThread(this.condition, this.lock, this.llist, this.done,
				true);

		Thread[] threads = new Thread[10];

		int i;
		for (i = 0; i < 10; i++) {
			threads[i] = new OddEvenProducerThread(this.condition, this.lock, this.llist, this.done);
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

		synchronized (this.lock) {
			this.condition.signalAll();
		}

		consumer1.join();
		consumer2.join();

		System.out.println("Sum odd: " + consumer1.getSum() + " | Sum even: " + consumer2.getSum());
		System.out.println("Awaits: " + this.condition.getCounter());
	}

	public static void main(String[] args) throws InterruptedException {
		OddEven oe = new OddEven();
		oe.start();
	}

}
