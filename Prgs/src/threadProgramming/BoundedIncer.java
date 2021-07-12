package threadProgramming;

public class BoundedIncer {
	int bound;

	public BoundedIncer(int bound) {
		this.bound = bound;
	}

	public void run() {
		int x = 0;
		while (x < bound) {
			x += 1;
			Util.sleep(1000);
			Util.format("x=%d\n", x);
		}
	}
}
