package a5;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Assert;
import org.junit.Test;

public class unsafeOddCoupleTest {

	@Test
	public void testNonBlocking() throws InterruptedException {
		runThreadsNonBlocking(1);
		runThreadsNonBlocking(2);
		runThreadsNonBlocking(17);
		runThreadsNonBlocking(42);
	}

	@Test
	public void testUnsafe() throws InterruptedException {
		//runThreadsUnsafe(1);
		
	}

	@Test
	public void testSafe() throws InterruptedException {
		runThreadsLock(1);
		runThreadsLock(2);
		runThreadsLock(17);
		runThreadsLock(42);
	}

	public void runThreadsNonBlocking(int num_threads) throws InterruptedException {

		System.out.println("Test with " + num_threads + " Threads");

		NonBlockingOddCouple uec = new NonBlockingOddCouple();

		int[] number_array = new int[1_000_000];

		for (int i = 1; i <= 1_000_000; i++) {
			number_array[i - 1] = i;
		}

		AtomicBoolean checkRunnableRunning = new AtomicBoolean(true);
		OddSumRunnable osr = new OddSumRunnable(uec, number_array);
		CheckOddSumRunnable cosr = new CheckOddSumRunnable(uec, checkRunnableRunning);

		Thread[] threads = new Thread[num_threads];

		for (int i = 0; i < num_threads; i++) {
			threads[i] = new Thread(osr);
		}

		Thread checkThread = new Thread(cosr);
		checkThread.start();

		for (int i = 0; i < num_threads; i++) {
			threads[i].start();
		}

		for (int i = 0; i < num_threads; i++) {
			threads[i].join();
		}

		checkRunnableRunning.set(false);

		checkThread.join();
	}

	public void runThreadsUnsafe(int num_threads) throws InterruptedException {

		System.out.println("Test with " + num_threads + " Threads");

		UnsafeOddCouple uec = new UnsafeOddCouple();

		int[] number_array = new int[1_000_000];

		for (int i = 1; i <= 1_000_000; i++) {
			number_array[i - 1] = i;
		}

		AtomicBoolean checkRunnableRunning = new AtomicBoolean(true);
		OddSumRunnable osr = new OddSumRunnable(uec, number_array);
		CheckOddSumRunnable cosr = new CheckOddSumRunnable(uec, checkRunnableRunning);

		Thread[] threads = new Thread[num_threads];

		for (int i = 0; i < num_threads; i++) {
			threads[i] = new Thread(osr);
		}

		Thread checkThread = new Thread(cosr);
		checkThread.start();

		for (int i = 0; i < num_threads; i++) {
			threads[i].start();
		}

		for (int i = 0; i < num_threads; i++) {
			threads[i].join();
		}

		checkRunnableRunning.set(false);

		checkThread.join();
	}

	public void runThreadsLock(int num_threads) throws InterruptedException {

		System.out.println("Test with " + num_threads + " Threads");

		LockOddCouple uec = new LockOddCouple();

		int[] number_array = new int[1_000_000];

		for (int i = 1; i <= 1_000_000; i++) {
			number_array[i - 1] = i;
		}

		AtomicBoolean checkRunnableRunning = new AtomicBoolean(true);
		OddSumRunnable osr = new OddSumRunnable(uec, number_array);
		CheckOddSumRunnable cosr = new CheckOddSumRunnable(uec, checkRunnableRunning);

		Thread[] threads = new Thread[num_threads];

		for (int i = 0; i < num_threads; i++) {
			threads[i] = new Thread(osr);
		}

		Thread checkThread = new Thread(cosr);
		checkThread.start();

		for (int i = 0; i < num_threads; i++) {
			threads[i].start();
		}

		for (int i = 0; i < num_threads; i++) {
			threads[i].join();
		}

		checkRunnableRunning.set(false);

		checkThread.join();
	}

	class OddSumRunnable implements Runnable {

		OddCouple uec;
		int[] number_array;

		public OddSumRunnable(OddCouple uec, int[] number_array) {
			this.uec = uec;
			this.number_array = number_array;
		}

		@Override
		public void run() {
			for (int i = 0; i < number_array.length; i++) {
				this.uec.setX(number_array[i]);
				this.uec.setY(number_array[i]);

			}
		}

	}

	class CheckOddSumRunnable implements Runnable {

		OddCouple uec;
		AtomicBoolean checkRunnableRunning;

		public CheckOddSumRunnable(OddCouple uec, AtomicBoolean checkRunnableRunning) {
			this.uec = uec;
			this.checkRunnableRunning = checkRunnableRunning;
		}

		@Override
		public void run() {
			while (this.checkRunnableRunning.get()) {
				Assert.assertNotSame("" + this.uec.getSum(), 0, this.uec.getSum() % 2);
			}
		}
	}
}
