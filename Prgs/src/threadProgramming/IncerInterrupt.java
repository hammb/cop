package threadProgramming;

public class IncerInterrupt extends Thread {
	private volatile boolean doRun = true;

	public void run() {
		int x = 0;
		while (!Thread.currentThread().isInterrupted() && this.doRun) {

			x += 1;
			try {
				Thread.sleep(1000);
				Util.format("x=%d\n", x);
			} catch (InterruptedException e) {

			}
		}
	}

	public void quit() {
		doRun = false;
		this.interrupt();
	}
}