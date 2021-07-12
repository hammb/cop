package threadProgramming;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantExplicit {
	private ReentrantLock lock = new ReentrantLock();

	public void f() {
		lock.lock(); // acquire
		try {
			Util.format("f\n");
			g();
		} finally {
			lock.unlock(); // release
		}
	}

	public void g() {
		lock.lock();
		Util.format("g\n");
		lock.unlock();
	}

	public static void main(String[] args) {
		Reentrant reentrant = new Reentrant();
		reentrant.g();
		reentrant.f();
	}
}