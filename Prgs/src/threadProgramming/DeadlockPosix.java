package threadProgramming;

import java.util.concurrent.Semaphore;

public class DeadlockPosix {
	Semaphore semaphore = new Semaphore(1);

	public void doIt() throws InterruptedException {
		semaphore.acquire();
		Util.println("doIt");
		semaphore.release();
	}

	public void doDoIt() throws InterruptedException {
		semaphore.acquire();
		Util.println("doDoIt");
		doIt();
		semaphore.release();
	}

	public static void main(String[] args) {
		DeadlockPosix deadlock = new DeadlockPosix();
		try {
			deadlock.doIt(); // ok
			deadlock.doDoIt(); // deadlock
		} catch (InterruptedException e) {
		}
		// will never go that far
	}
}