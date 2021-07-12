package a3;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import a1.AbstractParticle;
// Particles implementation similar to first assignment, but in 
// separate collection of particles as wrapper.
// Change this class to have the movement done by a fixed-size
// thread pool and an executor and using a different passive 
// Particle implementation, where the three abstract methods
// are just empty.
public class Particles implements Iterable<AbstractParticle> {
	private List<AbstractParticle> particles;
	public Particles() {
		particles = new ArrayList<>();
	}
	public void addParticle() {
		particles.add(new Particle());
	}
	public void clear() {
		for (AbstractParticle p : particles) {
			p.stop();
		}
		particles.clear();
	}
	public void cont() {
		for (AbstractParticle p: particles) {
			p.cont();
		}
	}
	public void pause() {
		for (AbstractParticle p: particles) {
			p.pause();
		}
	}
	public void stop() {
		clear();
	}
	public int size() {
		return particles.size();
	}
	public Iterator<AbstractParticle> iterator() {
		return particles.iterator();
	}
}