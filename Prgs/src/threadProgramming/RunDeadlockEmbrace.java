package threadProgramming;

public class RunDeadlockEmbrace {
	final static int RUNS = 1_000_000;

	public static void main(String[] args) {
		final DeadlockEmbrace deadlock = new DeadlockEmbrace();
		Thread t1 = new Thread(() -> {
			for (int i = 0; i < RUNS; i++)
				deadlock.doIt12();
		});
		Thread t2 = new Thread(() -> {
			for (int i = 0; i < RUNS; i++)
				deadlock.doIt21();
		});
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
		}
		// might never go that far
	}
}
