package threadProgramming;

public class UnsafeCounter implements Counter {
	private int counter;

	public UnsafeCounter() {
		counter = 0;
	}

	public void inc() {
		counter += 1;
	}

	public int get() {
		return counter;
	}
}
