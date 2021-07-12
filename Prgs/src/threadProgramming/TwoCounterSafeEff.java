package threadProgramming;

public class TwoCounterSafeEff implements Counter {
	private Counter counter1;
	private Counter counter2;

	public TwoCounterSafeEff() {
		counter1 = new UnsafeCounter();
		counter2 = new UnsafeCounter();
	}

	public synchronized void inc() {
		counter1.inc();
		counter2.inc();
	}

	public synchronized int get() {
		return counter1.get() + counter2.get();
	}
}