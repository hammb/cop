package a1;

public class Particle extends AbstractParticle {

	public final static int FPS = 20;
	public final static int MSPERFRAME = 1000 / FPS;

	MovementThread mt;
	volatile boolean move_bool;
	boolean stop_bool;

	public Particle() {
		stop_bool = false;
		cont();
	}
	
	@Override
	public void pause() {
		this.mt.quit();
	}

	@Override
	public void cont() {
		if (!this.stop_bool) {
			this.move_bool = true;
			mt = new MovementThread(this);
			mt.start();
		}
	}

	@Override
	public void stop() {
		this.stop_bool = true;
		this.mt.quit();
	}

}
