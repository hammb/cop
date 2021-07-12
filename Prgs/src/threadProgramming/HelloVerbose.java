package threadProgramming;

public class HelloVerbose implements Runnable {
	
	@Override
	public void run() {
		System.out.println("Hello Java");

	}
	// main programm
	public static void main(String[] args) {
		Runnable doit = new HelloVerbose();
		// Executes in main thread
		doit.run();
	}
}
