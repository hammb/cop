package a5;

import java.util.concurrent.CountDownLatch;

public class Inserter extends Thread {
	
	int[] numbers;
	CountDownLatch latch;
	Search search;
	
	public Inserter(int[] numbers, CountDownLatch latch, Search search) {
		this.numbers = numbers;
		this.latch = latch;
		this.search = search;
	}
	
	@Override
	public void run() {
		//System.out.println("X: " + numbers[0] + "," + numbers.length);
		for(int i = 0; i < this.numbers.length; i++) {
			this.search.add(this.numbers[i]);
		}
		//System.out.println("Y: " + numbers[numbers.length-10]);
		this.latch.countDown();
	}
}
