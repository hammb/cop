package threadProgramming;

import java.util.concurrent.Semaphore;

public class SemaphoreLock {
	private Semaphore lock = new Semaphore(1);

	public void f() throws InterruptedException {
		lock.acquire();
		try {
			Util.format("f\n");
			g();
		} finally {
			lock.release();
		}
	}

	public void g() throws InterruptedException {
		lock.acquire();
		try {
			Util.format("g\n");
		} finally {
			lock.release();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		SemaphoreLock classic = new SemaphoreLock();
		classic.g();
		classic.f(); // hangs forever
	}
}