package producerConsumer;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ConsumerSignalAwait extends Thread {
	private Queue<Integer> queue;
	private Lock qlock;
	private Condition qcondition;
	AtomicBoolean done = new AtomicBoolean(false);
	long sum = 0, misses = 0;

	public ConsumerSignalAwait(Queue<Integer> queue, Lock qlock, Condition qcondition) {
		this.queue = queue;
		this.qlock = qlock;
		this.qcondition = qcondition;
	}

	public void run() {
		while (!done.get()) {
			Integer value = null;
			qlock.lock();
			try {
				while (queue.isEmpty() && !done.get())
					qcondition.await();
				if (!queue.isEmpty())
					value = queue.remove();
			} catch (InterruptedException ignore) {
			} finally {
				qlock.unlock();
			}
			if (value != null) {
				sum += value;
			} else {
				misses += 1;
			}
		}
	}
}
