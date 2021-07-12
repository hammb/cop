package threadProgramming;

import java.util.concurrent.Semaphore;

public class CounterMonitor implements Counter {
	private Semaphore lock;
	private int counter;

	public CounterMonitor() {
		counter = 0;
		lock = new Semaphore(1);
	}

	public void inc() {
		try {
			lock.acquire();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		try {
			counter += 1; // if might throw...
		} finally {
			lock.release();
		}
	}

	public int get() {
		int ret;
		lock.acquireUninterruptibly();
		try {
			ret = counter;
		} finally {
			lock.release();
		}
		return ret;
	}

}
