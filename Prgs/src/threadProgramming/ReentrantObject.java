package threadProgramming;

public class ReentrantObject {
	private Object lock = new Object();

	public void f() {
		synchronized (lock) {
			Util.format("f\n");
			g();
		}
	}

	public void g() {
		synchronized (lock) {
			Util.format("g\n");
		}
	}

	public static void main(String[] args) {
		ReentrantObject reentrant = new ReentrantObject();
		reentrant.g();
		reentrant.f();
	}
}