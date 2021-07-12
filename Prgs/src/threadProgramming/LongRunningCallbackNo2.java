package threadProgramming;

public class LongRunningCallbackNo2 extends Thread {
	private int ith, prime;
	
	public int getPrime() {
		return prime;
	}

	private Runnable whenDone;

	public LongRunningCallbackNo2(int ith, Runnable whenDone) {
		this.ith = ith;
		this.whenDone = whenDone;
	}

	public void run() {
		PrimeGen primeGen = new PrimeGen();
		while (ith-- > 0) {
			prime = primeGen.next();
		}
		if (whenDone != null) {
			whenDone.run();
		}
	}
}
