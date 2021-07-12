package threadProgramming;

public class LongRunningCallback extends Thread {
	private int ith, prime;
	private UsePrime whenDone;

	public LongRunningCallback(int ith, UsePrime whenDone) {
		this.ith = ith;
		this.whenDone = whenDone;
	}

	public void run() {
		PrimeGen primeGen = new PrimeGen();
		while (ith-- > 0) {
			prime = primeGen.next();
		}
		if (whenDone != null) {
			whenDone.usePrime(prime);
		}
	}
}
