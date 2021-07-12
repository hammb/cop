package threadProgramming;

public class RunIthPrimeMany {
	public static void main(String[] args) throws InterruptedException {
		int[] is = { 1234, 1235, 1236 };
		IthPrime[] ith = { null, null, null };
		for (int j = 0; j < is.length; j++) {
			ith[j] = new IthPrime(is[j]);
		}
		for (int j = 0; j < is.length; j++) {
			ith[j].start();
		}
		for (int j = 0; j < is.length; j++) {
			ith[j].join();
			int p = ith[j].getIthPrime();
			String msg = "%dth prime: %d\n";
			Util.format(msg, is[j], p);
		}
	}
}
