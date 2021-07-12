package threadProgramming;

class LongRunning extends Thread {
	int ith;
	int prime;

	public LongRunning(int ith) {
		this.ith = ith;
	}

	public void run() {
		PrimeGen primeGen = new PrimeGen();
		while (this.ith-- > 0) {
			this.prime = primeGen.next();
		}
	}

	public int getPrime() {
		return this.prime;
	}
}