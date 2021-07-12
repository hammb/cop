package threadProgramming;

public class RunIthPrime {

	public static void main(String[] args) throws InterruptedException {
		int i = 4;
		
		if (args.length > 0) {
			i = Integer.parseInt(args[0]);
		}
		
		IthPrime ithPrime = new IthPrime(i);
		
		ithPrime.start();
		
		Util.format("Something useful\n");
		// may throw InterruptedException
		
		ithPrime.join();
		
		int p = ithPrime.getIthPrime();
		
		String msg = "%dth prime: %d\n";
		Util.format(msg, i, p);
	}
}
