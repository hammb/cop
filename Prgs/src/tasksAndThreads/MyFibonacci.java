package tasksAndThreads;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.FutureTask;

public class MyFibonacci {

	public static Integer forkjoin(int num_threads, Integer n) {
		ForkJoinPool forkJoinPool = new ForkJoinPool(num_threads);
		MyRecursiveFibonacciTask mrft = new MyRecursiveFibonacciTask(n);
		Util.resetTime();
		forkJoinPool.invoke(mrft);
		Util.format("%4d msecs total\n", Util.getTimeMillis());
		return mrft.join();
	}
	
	public static Integer callfut(Integer n) {
		FutureTask<Integer> future = new FutureTask<>(new MyFibonacciCallable(n));
		Util.resetTime();
		new Thread(future).start();
		
		try {
			Integer result = future.get();
			Util.format("%4d msecs total\n", Util.getTimeMillis());
			return result;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static void main(String[] args) {
		System.out.println(callfut(40));
		System.out.println(forkjoin(4, 40));
	
	}

}
