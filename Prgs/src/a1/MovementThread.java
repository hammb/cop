package a1;

public class MovementThread extends Thread {

	Particle p;

	public MovementThread(Particle p) {
		this.p = p;
	}

	@Override
	public void run() {
		while (this.p.move_bool && !isInterrupted()) {
			try {
				this.p.move();
				Thread.sleep(p.MSPERFRAME);
			} catch (InterruptedException e) {
				if (!this.p.move_bool) {
					return;
				}
			}
		}

	}

	public void quit() {
		this.p.move_bool = false;
		this.interrupt();
	}
}