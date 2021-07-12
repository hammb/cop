package threadProgramming;

public class SafeCounterLock implements Counter {
	private int counter;
	private Object lock;

	public SafeCounterLock() {
		counter = 0;
		lock = new Object();
	}

	public void inc() {
		synchronized (lock) {
			counter += 1;
		}
	}

	public int get() {
		synchronized (lock) {
			return counter;
		}
	}
}