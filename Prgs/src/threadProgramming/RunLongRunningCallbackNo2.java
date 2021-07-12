package threadProgramming;

public class RunLongRunningCallbackNo2 {
	public static void main(String[] args) {
		final int ith = 1000;
		
		final UsePrime whenDone = new UsePrime() {
			public void usePrime(int prime) {
				Util.format("%dth prime: %d\n", ith, prime);
			}
		};
		
		LongRunningCallbackNo2 longRunning = new LongRunningCallbackNo2(ith, () ->  {
			//int a = longRunning.getPrime();					
		});
		
		longRunning.start();
		// something else that is useful
		System.out.println("he");
		Util.join(longRunning); // prevent exit
		System.out.println("ho");
	}
}
