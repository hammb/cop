package a3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.util.concurrent.TimeUnit.*;
import a1.AbstractParticle;

public class ParticlesExecutor implements Iterable<AbstractParticle> {

	ScheduledExecutorService stp;
	private List<AbstractParticle> particles = new ArrayList<>();

	AtomicBoolean paused = new AtomicBoolean(false);

	AtomicBoolean cleared = new AtomicBoolean(false);

	Semaphore lock = new Semaphore(1);

	public ParticlesExecutor() {
		int num_threads = 100;
		this.stp = Executors.newScheduledThreadPool(num_threads);
	}

	public void cont() {
		synchronized (this.lock) {
			this.paused.set(false);
			this.lock.notifyAll();
		}

	}

	public void pause() {
		
			this.paused.set(true);
		
	}

	public synchronized int size() {

		return this.particles.size();

	}

	public void addParticle() {
		Particle particle = new Particle();
		this.particles.add(particle);
		this.stp.scheduleAtFixedRate(new Task(particle, this.stp, this.paused), 100, 100, MILLISECONDS);
	}

	public void clear() {
		this.particles.clear();
		this.cleared.set(true);
	}

	public void stop() {
		clear();
		this.stp.shutdown();

	}

	@Override
	public Iterator<AbstractParticle> iterator() {
		return this.particles.iterator();
	}

	public class Task implements Runnable {

		AbstractParticle particle;
		ScheduledExecutorService executor;
		AtomicBoolean paused;

		public Task(AbstractParticle particle, ScheduledExecutorService executor, AtomicBoolean paused) {
			this.particle = particle;
			this.executor = executor;
			this.paused = paused;
		}

		public void run() {
			this.particle.move();
			synchronized (ParticlesExecutor.this.lock) {
				while (this.paused.get()) {

					try {
						ParticlesExecutor.this.lock.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				ParticlesExecutor.this.lock.notify();
			}
		}
	}
}
