package producerConsumer;

import java.util.LinkedList;
import java.util.concurrent.locks.Lock;

public class MyProducerNotify extends Thread {

	Lock qLock;
	int nMax;
	LinkedList<Integer> list;

	public MyProducerNotify(Lock qLock, LinkedList<Integer> list, int nMax) {
		this.qLock = qLock;
		this.nMax = nMax;
		this.list = list;
	}

	public void run() {
		for (int i = 0; i < this.nMax; i++) {
			Thread.yield();
			synchronized (this.qLock) {
				this.list.add(i);
				this.qLock.notify();
			}
		}
	}
}
