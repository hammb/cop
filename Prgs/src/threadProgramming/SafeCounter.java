package threadProgramming;

public class SafeCounter implements Counter {
	private int counter;

	public SafeCounter() {
		counter = 0;
	}

	public synchronized void inc() {
		counter += 1;
		//synchronized (this) {
		//	counter += 1;
		//}
	}

	public synchronized int get() {
		return counter;
	}
}