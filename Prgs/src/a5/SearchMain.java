package a5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class SearchMain {

	int[] numbers;
	int n = 4_000_000;

	public SearchMain() {

		// Prepare numbers Array

		List<Integer> numbers = new ArrayList<>();

		for (int i = 1; i <= n; i++) {
			numbers.add(Integer.valueOf(i));
		}

		Collections.shuffle(numbers);

		this.numbers = new int[numbers.size()];

		for (int i = 0; i < numbers.size(); i++) {
			this.numbers[i++] = numbers.get(i);
		}

	}

	public boolean testAddAndContainsSerially() {

		System.out.println("Test Serially");
		
		UnsafeSearch search = new UnsafeSearch();

		for (int i = 0; i < this.numbers.length; i++) {
			search.add(this.numbers[i]);
		}

		for (int i = 0; i < this.numbers.length; i++) {
			if (!search.contains(this.numbers[i])) {
				return false;
			}
		}
		return true;
	}
	
	public boolean testAddAndContainsConcurrent(int num_threads, Search search) throws InterruptedException {

		System.out.println("Test concurrently " + num_threads + " Threads");
		
		int steps = this.numbers.length / num_threads;
		
	    CountDownLatch latch = new CountDownLatch(num_threads);
	    
	    Thread[] threads = new Thread[num_threads];
	    
	    int[][] numbers_parts = new int[num_threads][];
	   
	    for (int i = 0; i < num_threads; i++) {
	    	numbers_parts[i] = Arrays.copyOfRange(this.numbers, i * steps, (i + 1) * steps);
	    }
	    
	    for (int i = 0; i < num_threads; i++) {
	    	threads[i] = new Inserter(numbers_parts[i], latch, search); 
	    }
	    
	    for (int i = 0; i < num_threads; i++) {
	    	threads[i].start(); 
	    }
	    long start = System.currentTimeMillis();    
	    latch.await();
	    System.out.format("Time inserting Values %d ms \n", System.currentTimeMillis() - start );
	    System.out.println("");
	    
	    for (int i = 0; i < this.numbers.length; i++) {
	    	
			if (!search.contains(this.numbers[i])) {
				return false;
			}
		}
		return true;
	      
	}

	public static void main(String[] args) throws InterruptedException {
		
		SearchMain sm = new SearchMain();
//		
		System.out.println(sm.testAddAndContainsSerially());
//		
		UnsafeSearch unsafeBinTree = new UnsafeSearch();
//		
//		System.out.println(sm.testAddAndContainsConcurrent(2, unsafeBinTree));
		System.out.println(sm.testAddAndContainsConcurrent(4, unsafeBinTree));
//		System.out.println(sm.testAddAndContainsConcurrent(10, unsafeBinTree));
//		System.out.println(sm.testAddAndContainsConcurrent(16, unsafeBinTree));
//		
		LockSearch lockSearch = new LockSearch();
//		
//		System.out.println(sm.testAddAndContainsConcurrent(2, lockSearch));
		System.out.println(sm.testAddAndContainsConcurrent(4, lockSearch));
//		System.out.println(sm.testAddAndContainsConcurrent(10, lockSearch));
//		System.out.println(sm.testAddAndContainsConcurrent(16, lockSearch));
		
		int values[] = {2, 4, 8, 16, 32};
		
		for(int i = 0; i < values.length; i ++) {
			for(int j = 0; j < values.length; j ++) {
				LockNSearch lockNSearch = new LockNSearch(values[i]);
				System.out.println("\nUsing " + values[i] + " Depth");
				System.out.println(sm.testAddAndContainsConcurrent(values[j], lockNSearch));
			}
		}
		
		
	}

}
