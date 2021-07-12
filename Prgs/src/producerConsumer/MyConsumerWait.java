package producerConsumer;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;

public class MyConsumerWait extends Thread {

	LinkedList<Integer> list;
	Lock qLock;
	AtomicBoolean running = new AtomicBoolean(true);
	long sum = 0, misses = 0;

	public MyConsumerWait(Lock qLock, LinkedList<Integer> list) {
		this.qLock = qLock;
		this.list = list;
	}

	public void run() {
		while (running.get()) {
			Integer value = null;

			synchronized (qLock) {
				try {
					while (this.list.isEmpty()) {

						this.qLock.wait();
						
					}
					value = this.list.remove();
				} catch (InterruptedException e) {
					
				}
			}

			if (value != null) {
				this.sum += value;
			} else {
				this.misses += 1;
			}
		}
	}
}
