package threadProgramming;

public class RunTwoCounterSafe {
	static int RUNS = 100000, diffs = 0;
	static Counter counter = new TwoCounterSafe();
	
	static Runnable peeker = () -> {
		for (int i = 0; i < RUNS; i += 1) {
			if (counter.get() % 2 == 1) {
				diffs += 1; // shouldn’t happen
			}
		}
	};


	// Peeker as before
	public static void main(String[] args) {
		RunCounter runCounter = new RunCounter();
		Thread threadPeeker = new Thread(peeker);
		threadPeeker.start();
		Util.resetTime();
		runCounter.run(counter);
		long time = Util.getTimeMillis();
		Util.format("%d Ops from %d tests in %d ms\n", diffs, RUNS, time);
		Util.join(threadPeeker);
		
		
		counter = new TwoCounterSafeEff();
		threadPeeker = new Thread(peeker);
		threadPeeker.start();
		Util.resetTime();
		runCounter.run(counter);
		time = Util.getTimeMillis();
		Util.format("%d Ops from %d tests in %d ms\n", diffs, RUNS, time);
	}
}