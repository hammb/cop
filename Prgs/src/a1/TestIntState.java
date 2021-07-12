package a1;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TestIntState {

	public static void main(String[] args) {

		oneCounterForAllThreads();
		oneCounterPerThread();

	}

	public static void oneCounterForAllThreads() {
		System.out.println("One counter for all threads\n");
		
		int num_numbers = 10_000_000;
		int num_threads = 1000;
		int num_numbers_per_thread = num_numbers / num_threads;

		Random rand = new Random();
		int upperbound = 1001;
		int int_random;

		int[] random_numbers_all = new int[num_numbers];
		int[][] random_numbers_subsets = new int[num_threads][num_numbers_per_thread];

		IntStateUnsafe unsafe_opertions = new IntStateUnsafe();
		IntStateSafe safe_opertions = new IntStateSafe();

		Thread[] state_counter_threads_unsafe = new Thread[num_threads];
		Thread[] state_counter_threads_safe = new Thread[num_threads];

		// generate random values from 0-1000
		int set = 0;
		int correct_answer = 0;
		for (int i = 0; i <= num_numbers; i++) {

			if (i % num_numbers_per_thread == 0 && i > 1) {
				state_counter_threads_unsafe[set] = new StateCounter(unsafe_opertions, random_numbers_subsets[set]);
				state_counter_threads_safe[set] = new StateCounter(safe_opertions, random_numbers_subsets[set]);
				set++;
				if (i == num_numbers) {
					break;
				}
			}

			int_random = rand.nextInt(upperbound);
			random_numbers_all[i] = int_random;
			random_numbers_subsets[set][i - num_numbers_per_thread * set] = int_random;

			correct_answer += int_random;

		}

		long startTime = System.nanoTime();
		for (int i = 0; i < num_threads; i++) {
			state_counter_threads_unsafe[i].start();
		}
		for (int i = 0; i < num_threads; i++) {
			try {
				state_counter_threads_unsafe[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		long endTime = System.nanoTime();
		long timeElapsed = endTime - startTime;
		System.out.println("Execution unsafe: Time in milliseconds: " + timeElapsed / 1000000);

		startTime = System.nanoTime();
		for (int i = 0; i < num_threads; i++) {
			state_counter_threads_safe[i].start();
		}
		for (int i = 0; i < num_threads; i++) {
			try {
				state_counter_threads_safe[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		endTime = System.nanoTime();
		timeElapsed = endTime - startTime;
		System.out.println("Execution safe: Time in milliseconds: " + timeElapsed / 1000000);

		System.out.println("Right answer: " + correct_answer);
		System.out.println("Unsafe: " + unsafe_opertions.getValue());
		System.out.println("Safe: " + safe_opertions.getValue() + "\n---------------\n");
	}

	public static void oneCounterPerThread() {
		System.out.println("One counter per thread\n");
		
		int num_numbers = 10_000_000;
		int num_threads = 1000;
		int num_numbers_per_thread = num_numbers / num_threads;

		Random rand = new Random();
		int upperbound = 1001;
		int int_random;

		int[] random_numbers_all = new int[num_numbers];
		int[][] random_numbers_subsets = new int[num_threads][num_numbers_per_thread];

		IntStateUnsafe[] unsafe_opertions = new IntStateUnsafe[num_threads];
		IntStateSafe[] safe_opertions = new IntStateSafe[num_threads];

		Thread[] state_counter_threads_unsafe = new Thread[num_threads];
		Thread[] state_counter_threads_safe = new Thread[num_threads];

		// generate random values from 0-1000
		int thread_index = 0;
		int correct_answer = 0;
		for (int i = 0; i <= num_numbers; i++) {

			if (i % num_numbers_per_thread == 0 && i > 1) {
				unsafe_opertions[thread_index] = new IntStateUnsafe();
				safe_opertions[thread_index] = new IntStateSafe();

				state_counter_threads_unsafe[thread_index] = new StateCounter(unsafe_opertions[thread_index],
						random_numbers_subsets[thread_index]);
				state_counter_threads_safe[thread_index] = new StateCounter(safe_opertions[thread_index],
						random_numbers_subsets[thread_index]);
				thread_index++;
				if (i == num_numbers) {
					break;
				}
			}

			int_random = rand.nextInt(upperbound);
			random_numbers_all[i] = int_random;
			random_numbers_subsets[thread_index][i - num_numbers_per_thread * thread_index] = int_random;

			correct_answer += int_random;

		}
		
		int unsafe_val = 0;
		
		long startTime = System.nanoTime();
		for (int i = 0; i < num_threads; i++) {
			state_counter_threads_unsafe[i].start();
		}
		for (int i = 0; i < num_threads; i++) {
			try {
				state_counter_threads_unsafe[i].join();
				unsafe_val += unsafe_opertions[i].getValue();
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		long endTime = System.nanoTime();
		long timeElapsed = endTime - startTime;
		System.out.println("Execution unsafe: Time in milliseconds: " + timeElapsed / 1000000);

		
		int safe_val = 0;
		startTime = System.nanoTime();
		for (int i = 0; i < num_threads; i++) {
			state_counter_threads_safe[i].start();
		}
		for (int i = 0; i < num_threads; i++) {
			try {
				state_counter_threads_safe[i].join();
				safe_val += safe_opertions[i].getValue();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		endTime = System.nanoTime();
		timeElapsed = endTime - startTime;
		System.out.println("Execution safe: Time in milliseconds: " + timeElapsed / 1000000);

		System.out.println("Right answer: " + correct_answer);
		System.out.println("Unsafe: " + unsafe_val);
		System.out.println("Safe: " + safe_val + "\n---------------\n");
	}

}
