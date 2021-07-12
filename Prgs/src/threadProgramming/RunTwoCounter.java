package threadProgramming;

public class RunTwoCounter {
	final static int RUNS = 100_000;
	static TwoCounter counter = new TwoCounter();
	static int diffs = 0;
	static Runnable peeker = () -> {
		for (int i = 0; i < RUNS; i += 1) {
			if (counter.get() % 2 == 1) {
				diffs += 1; // shouldn’t happen
			}
		}
	};

	public static void main(String[] args) {
		RunCounter runCounter = new RunCounter();
		Thread threadPeeker = new Thread(peeker);
		threadPeeker.start();
		runCounter.run(counter);
		Util.format("%d Ops from %d tests: %d%%\n", diffs, RUNS, diffs * 100 / RUNS);
	}
}