package threadProgramming;

public class RunLongRunning {
	public static void main(String[] args) {
		int ith = 1000;
		LongRunning longRunning = new LongRunning(ith);
		longRunning.start();
		// something else that is useful
		Util.join(longRunning);
		int prime = longRunning.getPrime(); //Passiert nicht sofort, 
											//falls zwischen start() und join() zu lange dauert
		Util.format("%dth prime: %d\n", ith, prime);
	}
}
