package threadProgramming;

public class IthPrimeRunnable implements Runnable{
	private int i;
	private int ithPrime;

	public IthPrimeRunnable(int i) {
		this.i = i; // must be greater 0
	}

	public void run() {
		int j = i;
		PrimeGen primeGen = new PrimeGen();
		while (j-- > 0) {
			ithPrime = primeGen.next();
		}
		Util.format("done %d \n", i);
	}

	public int getIthPrime() {
		return ithPrime;
	}
}
