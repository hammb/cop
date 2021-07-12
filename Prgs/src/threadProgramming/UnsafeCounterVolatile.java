package threadProgramming;

public class UnsafeCounterVolatile implements Counter {
	private volatile int counter;

	public UnsafeCounterVolatile() {
		counter = 0;
	}

	public void inc() {
		counter += 1;
	}

	public int get() {
		return counter;
	}
}