package a3;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class MyThreadPerTaskExecutor implements Executor {

	int max_num_threads;
	AtomicInteger curr_num_threads = new AtomicInteger(0);
	AtomicBoolean running = new AtomicBoolean(true);
	private LinkedBlockingQueue<Runnable> runnables = new LinkedBlockingQueue<>();

	public MyThreadPerTaskExecutor(int max_num_threads) {
		this.max_num_threads = max_num_threads;
		TaskThread th = new TaskThread(this.running, this.runnables, this.max_num_threads);
		th.start();
	}

	@Override
	public void execute(Runnable runnable) {

		if (curr_num_threads.get() <= max_num_threads) {
			try {
				runnables.put(runnable);
			} catch (InterruptedException e) {

			}
		}

	}

	public void shutDown() {
		this.running.set(false);
		this.runnables.clear();
	}

	public class TaskThread extends Thread {

		AtomicBoolean running;
		AtomicInteger curr_num_threads;
		LinkedBlockingQueue<Runnable> runnables;
		int max_num_threads;

		public TaskThread(AtomicBoolean running, LinkedBlockingQueue<Runnable> runnables, int max_num_threads) {
			this.running = running;
			this.runnables = runnables;
			this.max_num_threads = max_num_threads;

		}

		public void run() {
			Runnable task;

			while (this.running.get()) {
				if (!this.runnables.isEmpty()) {
					task = this.runnables.remove();
					task.run();
				} else {

				}
			}
		}
	}

	public static void main(String[] args) {
		MyThreadPerTaskExecutor mtpte = new MyThreadPerTaskExecutor(20);

		Runnable task = () -> {

			try {
				Thread.sleep(300);
			} catch (InterruptedException interruptedException) {

			}
			System.out.println("I woke up!");

		};

		for (int i = 0; i < 10; i++) {
			mtpte.execute(task);
		}

		mtpte.shutDown();
	}

}
