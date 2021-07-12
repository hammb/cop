package threadProgramming;

public class DeadlockEmbrace {
	private Object lock1, lock2;

	public DeadlockEmbrace() {
		lock1 = new Object();
		lock2 = new Object();
	}

	public void doIt12() {
		synchronized (lock1) {
			synchronized (lock2) {
			}
		}
	}

	public void doIt21() {
		synchronized (lock2) {
			synchronized (lock1) {
			}
		}
	}
}
