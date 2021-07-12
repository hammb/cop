package threadProgramming;

public class TwoCounter implements Counter {
	private Counter counter1;
	private Counter counter2;

	public TwoCounter() {
		counter1 = new SafeCounter();
		counter2 = new SafeCounter();
	}

	public void inc() {
		counter1.inc();
		counter2.inc();
	}

	public int get() {
		return counter1.get() + counter2.get();
	}
}