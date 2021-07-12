package threadProgramming;

public class RunLongRunningCallback {
	public static void main(String[] args) {
		final int ith = 1000;
		final UsePrime whenDone = new UsePrime() {
			public void usePrime(int prime) {
				Util.format("%dth prime: %d\n", ith, prime);
			}
		};
		LongRunningCallback longRunning = new LongRunningCallback(ith, whenDone);
		longRunning.start();
		// something else that is useful
		Util.join(longRunning); // prevent exit
	}
}
