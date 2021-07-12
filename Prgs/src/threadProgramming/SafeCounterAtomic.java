package threadProgramming;

import java.util.concurrent.atomic.AtomicInteger;

public class SafeCounterAtomic implements Counter {
	private AtomicInteger counter;

	public SafeCounterAtomic() {
		counter = new AtomicInteger();
	}

	public void inc() {
		// often useful to get new value
		counter.incrementAndGet();
	}

	public int get() {
		return counter.intValue();
	}
}