package threadProgramming;

public class IthPrime extends Thread {
	private int i;
	private int ithPrime;

	public IthPrime(int i) {
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