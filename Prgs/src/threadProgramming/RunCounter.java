package threadProgramming;

public class RunCounter {
	private int noTimes = 10_000_000, noIncers = 2;

	public RunCounter() {
	}

	public RunCounter(int noTimes, int noIncers) {
		this.noTimes = noTimes;
		this.noIncers = noIncers;
	}

	public int run(Counter counter) {
		Thread[] threads = new Thread[noIncers];
		for (int i = 0; i < threads.length; i += 1)
			threads[i] = new CounterIncer(counter, noTimes);
		for (int i = 0; i < threads.length; i += 1)
			threads[i].start();
		for (int i = 0; i < threads.length; i += 1)
			Util.join(threads[i]);
		return counter.get();
	}
	public static void main(String[] args) {
		RunCounter rc = new RunCounter();
		UnsafeCounter uc = new UnsafeCounter();
		System.out.println(rc.run(uc));
		
	}
}