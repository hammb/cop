package threadProgramming;

public class CounterIncer extends Thread {
	private Counter counter;
	private int times;

	public CounterIncer(Counter counter, int times) {
		this.counter = counter;
		this.times = times;
	}

	public void run() {
		for (int i = 0; i < times; i += 1) {
			counter.inc();
		}
	}
}