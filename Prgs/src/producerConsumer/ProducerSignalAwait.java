package producerConsumer;

import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ProducerSignalAwait extends Thread {
	private Queue<Integer> queue;
	private Lock qlock;
	private Condition qcondition;
	private int howMany;

	public ProducerSignalAwait(Queue<Integer> queue, Lock qlock, int howMany, Condition qcondition) {
		this.queue = queue;
		this.qlock = qlock;
		this.howMany = howMany;
		this.qcondition = qcondition;
	}

	public void run() {
		for (int i = 1; i <= howMany; i++) {
			Thread.yield();
			qlock.lock();
			try {
				this.queue.add(i);
				qcondition.signal();
			} finally {
				qlock.unlock();
			}
		}
	}
}
