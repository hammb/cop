package a1;

import java.awt.geom.Point2D;

public abstract class AbstractParticle {
	
	private double x, y;	

	public AbstractParticle() {
		// initally near the center
		this.x = Math.random()/2.0 + 0.25;
		this.y = Math.random()/2.0 + 0.25;
	}

	// all values in [0..1]
	final private static double sanitize(double v) {
		if (v > 1.0) {
			return 1.0;
		}
		if (v < 0.0) {
			return 0.0;
		}
		return v;
	}

	// a small random movement
	final public void move() {
		this.setPosition(this.x + (Math.random()/20.0-0.025),
						 this.y + (Math.random()/20.0-0.025));
	}

	final public Point2D.Double getPosition() {
		return new Point2D.Double(x, y);
	}

	final public double getPositionX() {
		return x;
	}

	final public double getPositionY() {
		return y;
	}

	final public void setPosition(double x, double y) {
		this.x = sanitize(x);
		this.y = sanitize(y);
	}

	final public void setPosition(Point2D.Double point) {
		this.x = sanitize(point.getX());
		this.y = sanitize(point.getY());
	}

	/** pauses changing the position 
	 */
	abstract public void pause();

	/** continues changing the position randomly in small steps using move.
	 */
	abstract public void cont();

	/** stops changing the position. There won't be any other call
	 * to pause, cont, or stop after stop.
	 */
	abstract public void stop();
	
}