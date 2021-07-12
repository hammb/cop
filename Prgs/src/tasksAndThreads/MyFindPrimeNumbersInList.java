package tasksAndThreads;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadFactory;

public class MyFindPrimeNumbersInList {

	static public class MyCallable implements Callable<Integer> {

		int[] list_of_numbers;

		public MyCallable(int[] list_of_numbers) {
			this.list_of_numbers = list_of_numbers;
		}

		@Override
		public Integer call() throws Exception {
			Integer counter = 0;

			for (int i = 0; i < this.list_of_numbers.length; i++) {
				if (checkForPrime(this.list_of_numbers[i])) {
					counter++;
				}
			}

			return counter;
		}

		static boolean checkForPrime(int inputNumber) {
			boolean isItPrime = true;

			if (inputNumber <= 1) {
				isItPrime = false;

				return isItPrime;
			} else {
				for (int i = 2; i <= inputNumber / 2; i++) {
					if ((inputNumber % i) == 0) {
						isItPrime = false;

						break;
					}
				}

				return isItPrime;
			}
		}

	}

	public static void CallableFuture(int[] list_of_numbers) {
		List<FutureTask<Integer>> tasks = new ArrayList<>();
		FutureTask<Integer> task;

		task = new FutureTask<>(new MyCallable(list_of_numbers));
		
		tasks.add(task);
		Util.resetTime();
		new Thread(task).start();

		task = tasks.get(0);

		try {
			int resFirst = task.get();
			Util.format("%4d msecs total\n", Util.getTimeMillis());
			System.out.println(resFirst + "\n");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void fixedThreadPool(int[] list_of_numbers) {
		int cores = Runtime.getRuntime().availableProcessors();

		int steps = list_of_numbers.length / cores;
		int last_step_add = list_of_numbers.length - (steps * cores);

		ExecutorService ftp = Executors.newFixedThreadPool(cores);

		List<FutureTask<Integer>> tasks = new ArrayList<>();
		FutureTask<Integer> task;
		for (int i = 0; i < cores; i++) {
			if (i == cores - 1) {
				task = new FutureTask<>(new MyCallable(
						Arrays.copyOfRange(list_of_numbers, (i * steps), ((i + 1) * steps) + last_step_add)));
			} else {
				task = new FutureTask<>(
						new MyCallable(Arrays.copyOfRange(list_of_numbers, (i * steps), (i + 1) * steps)));
			}

			tasks.add(task);
			ftp.submit(task);
		}

		int res = 0;
		Util.resetTime();
		for (int i = 0; i < tasks.size(); i++) {
			ftp.submit(tasks.get(i));
		}

		for (int i = 0; i < tasks.size(); i++) {
			task = tasks.get(i);
			try {
				res += task.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Util.format("%4d msecs total\n", Util.getTimeMillis());
		System.out.println(res + "\n");

		ftp.shutdown();
	}
	
	public static void cachedThreadPool(int[] list_of_numbers) {
		int cores = Runtime.getRuntime().availableProcessors();

		int steps = list_of_numbers.length / cores;
		int last_step_add = list_of_numbers.length - (steps * cores);

		ExecutorService ftp = Executors.newCachedThreadPool();

		List<FutureTask<Integer>> tasks = new ArrayList<>();
		FutureTask<Integer> task;
		for (int i = 0; i < cores; i++) {
			if (i == cores - 1) {
				task = new FutureTask<>(new MyCallable(
						Arrays.copyOfRange(list_of_numbers, (i * steps), ((i + 1) * steps) + last_step_add)));
			} else {
				task = new FutureTask<>(
						new MyCallable(Arrays.copyOfRange(list_of_numbers, (i * steps), (i + 1) * steps)));
			}

			tasks.add(task);
			ftp.submit(task);
		}

		int res = 0;
		Util.resetTime();
		for (int i = 0; i < tasks.size(); i++) {
			ftp.submit(tasks.get(i));
		}

		for (int i = 0; i < tasks.size(); i++) {
			task = tasks.get(i);
			try {
				res += task.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Util.format("%4d msecs total\n", Util.getTimeMillis());
		System.out.println(res + "\n");

		ftp.shutdown();
	}

	public static void main(String[] args) {

		int num_numbers = 500_000;
		int[] list_of_numbers = new int[num_numbers];

		for (int i = 0; i < num_numbers; i++) {
			list_of_numbers[i] = i;
		}

		shuffleArray(list_of_numbers);
		
		System.out.println("CallableFuture");
		CallableFuture(list_of_numbers);

		System.out.println("fixedThreadPool");
		fixedThreadPool(list_of_numbers);

		System.out.println("cachedThreadPool");
		cachedThreadPool(list_of_numbers);
		
	}

	// Fisher–Yates shuffle
	static void shuffleArray(int[] ar) {
		Random rnd = new Random();
		for (int i = ar.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			// Simple swap
			int a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
	}

}
