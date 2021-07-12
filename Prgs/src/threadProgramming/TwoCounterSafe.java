package threadProgramming;

public class TwoCounterSafe implements Counter {
	private Counter counter1;
	private Counter counter2;

	public TwoCounterSafe() {
		counter1 = new SafeCounter();
		counter2 = new SafeCounter();
	}

	public synchronized void inc() {
		counter1.inc();
		counter2.inc();
	}

	public synchronized int get() {
		return counter1.get() + counter2.get();
	}
}