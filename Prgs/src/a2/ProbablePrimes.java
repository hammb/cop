package a2;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;


public class ProbablePrimes extends Thread{

	AtomicInteger counter;
	BigInteger current_int;
	Runnable atEnd;
	
	public ProbablePrimes(AtomicInteger counter, BigInteger current_int, Runnable atEnd) {
		this.atEnd = atEnd;
		this.current_int = current_int;
		this.counter = counter;
	}

	/*public void sequential() {
		System.out.println("- Sequential execution -");
		int counter = 0;

		long startTime = System.nanoTime();

		while (lower_bound.compareTo(upper_bound) != 0) {
			if (lower_bound.isProbablePrime(1)) {
				counter++;
			}

			lower_bound = lower_bound.add(BigInteger.ONE);
		}

		long endTime = System.nanoTime();
		long timeElapsed = endTime - startTime;

		System.out.println("Execution time in milliseconds: " + timeElapsed / 1000000);
		System.out.println(counter);
	}*/

	@Override
	public void run() {
		if (current_int.isProbablePrime(Integer.MAX_VALUE)) {
			counter.incrementAndGet();
		}
		
		if(atEnd != null) {
			atEnd.run();
		}
		
	}

	public static void main(String[] args) throws InterruptedException {
		
		int num_cores = Runtime.getRuntime().availableProcessors();
		
		num_cores = 100;
		
		MySemaphore ms = new MySemaphore(num_cores);
		
		AtomicInteger counter = new AtomicInteger(0);
		
		BigInteger lower_bound = new BigDecimal("1000E100").toBigInteger();// 10E1000
		BigInteger upper_bound = new BigDecimal("1000E100").add(new BigDecimal("10E3")).toBigInteger();
		long startTime = System.nanoTime();
		while (lower_bound.compareTo(upper_bound) != 0) {
			ms.acquire();
			
			ProbablePrimes probable_primes_calculator = new ProbablePrimes( counter, lower_bound, () -> {
				try {
					ms.release();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			});
			
			probable_primes_calculator.start();

			lower_bound = lower_bound.add(BigInteger.ONE);
		}
		
		for (int i = 0; i < num_cores; i++) {
			ms.acquire();
		}
		
		long endTime = System.nanoTime();
		long timeElapsed = endTime - startTime;

		System.out.println("Execution time in milliseconds: " + timeElapsed / 1000000);
		System.out.println(counter);
		//probable_primes_calculator.sequential();

	}
	
	
}


