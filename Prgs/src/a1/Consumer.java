package a1;

import java.util.concurrent.Semaphore;

public class Consumer {

	private Semaphore lock;
	long endSum = 0;
	int num_numbers = 0;
	int num_threads = 1;
	ConsumerThread threads[];

	ConsumeNumbers consumer = new ConsumeNumbers();

	public Consumer(int num_threads) {
		this.num_threads = num_threads;
		this.lock = new Semaphore(1);
		this.callThreads();
	}

	public void callThreads() {

		this.threads = new ConsumerThread[this.num_threads];

		for (int i = 0; i < this.num_threads; i++) {
			this.threads[i] = new ConsumerThread(this);
		}

		for (int i = 0; i < this.num_threads; i++) {
			this.threads[i].start();
		}

		for (int i = 0; i < this.num_threads; i++) {
			try {
				this.threads[i].join();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}

		// System.out.print(endSum);
	}

	public long getNextNumber() {

		try {
			lock.acquire();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		try {
			num_numbers++;

			if (num_numbers % 10 == 0) {
				System.out.print(".");
			}

			if (num_numbers % 100 == 0) {
				System.out.print(",");
			}

			if (num_numbers % 72 == 0) {
				System.out.print("\n");
			}

			if (endSum % 10000 == 1717) {
				quitThreads();
			}

			return this.consumer.next();

		} finally {
			lock.release();
		}
	}

	public void quitThreads() {
		for (int i = 0; i < this.num_threads; i++) {
			this.threads[i].quit();
		}
	}

	public void sum(long nextLong) {

		try {
			lock.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			endSum += nextLong;

		} finally {
			lock.release();
		}
	}

	public static void main(String[] args) {

		new Consumer(1000);

	}

}