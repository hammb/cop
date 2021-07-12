package threadProgramming;

public class Reentrant {
	public synchronized void f() {
		Util.format("f\n");
		g();
	}

	public synchronized void g() {
		Util.format("g\n");
	}

	public static void main(String[] args) {
		Reentrant reentrant = new Reentrant();
		reentrant.g();
		reentrant.f();
	}
}