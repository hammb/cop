package producerConsumer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TwoConsumerSignalAwait {

	public static void main(String[] args) {
		Lock qlock = new ReentrantLock();
		Condition qcondition = qlock.newCondition();
		int nMax = 1_000_000;
		LinkedList<Integer> queue = new LinkedList<Integer>();

		ProducerSignalAwait producer = new ProducerSignalAwait(queue, qlock, nMax, qcondition);
		ConsumerSignalAwait consumer1 = new ConsumerSignalAwait(queue, qlock, qcondition);
		ConsumerSignalAwait consumer2 = new ConsumerSignalAwait(queue, qlock, qcondition);

		// setup producer and consumer1/2
		producer.start();
		consumer1.start();
		consumer2.start();
		try {
			producer.join();
			consumer1.done.set(true);
			consumer2.done.set(true);
			qlock.lock();
			try {
				qcondition.signalAll();
			} finally {
				qlock.unlock();
			}
			consumer1.join();
			consumer2.join();
			Util.format("sum %d\n (%d+%d)\n misses: %d\n", consumer1.sum + consumer2.sum, consumer1.sum, consumer2.sum,
					consumer1.misses + consumer2.misses);
		} catch (InterruptedException e) {
		}
	}
}
