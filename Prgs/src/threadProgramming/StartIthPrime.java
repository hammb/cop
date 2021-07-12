package threadProgramming;

public class StartIthPrime {

	public static void main(String[] args) {
		
		// extend Thread
		IthPrime ithPrime = new IthPrime(4);
		ithPrime.start();

		// implement Runnable
		IthPrimeRunnable ithPrimeR = new IthPrimeRunnable(4);
		Thread thread = new Thread(ithPrimeR);
		thread.start(); //NOT run
	}

}
