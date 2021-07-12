package threadProgramming;

public class Incer extends Thread {
	private volatile boolean doRun = true;

	public void run() {
		int x = 0;
		while (doRun) {
			x += 1;
			// at most one second sleep
			Util.sleep(1000);
			Util.format("x=%d\n", x);
		}
	}

	public void quit() {
		doRun = false;
	}
}