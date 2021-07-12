package producerConsumer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Hat busy wait

public class ProdConTryLock {
	public static void main(String[] args) {
		Lock qlock = new ReentrantLock();
		int nMax = 1_000_000;
		LinkedList<Integer> queue = new LinkedList<Integer>();

		ProducerTryLock producer = new ProducerTryLock(queue, qlock, nMax);
		ConsumerTryLock consumer1 = new ConsumerTryLock(queue, qlock);
		ConsumerTryLock consumer2 = new ConsumerTryLock(queue, qlock);

		producer.start();
		consumer1.start();
		consumer2.start();
		try {
			producer.join();
			consumer1.done.set(true);
			consumer2.done.set(true);
			// no longer notifyAll
			consumer1.join();
			consumer2.join();
			Util.format("sum %d\n (%d+%d)\n misses: %d\n", consumer1.sum + consumer2.sum, consumer1.sum, consumer2.sum,
					consumer1.misses + consumer2.misses);
		} catch (InterruptedException e) {
		}
	}
}
