package threadProgramming;

public class RunUnsafeCounter {
	public static void run() {
		Counter counter = new UnsafeCounter();
		RunCounter runC = new RunCounter();
		Util.resetTime();
		long count = runC.run(counter);
		long millis = Util.getTimeMillis();
		Util.format("UnSafe: %10d in %d msecs\n", count, millis);
	}

	public static void main(String[] args) {
		for (int i = 0; i < 7; i++) {
			run();
		}
	}
}
