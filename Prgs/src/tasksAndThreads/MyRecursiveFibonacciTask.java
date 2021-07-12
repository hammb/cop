package tasksAndThreads;

import java.util.concurrent.RecursiveTask;

public class MyRecursiveFibonacciTask extends RecursiveTask<Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Integer n;
	
	public MyRecursiveFibonacciTask(Integer n) {
		this.n = n;
	}

	@Override
	protected Integer compute() {
		
		if(this.n == 0) {
			return 0;
		}
		
		if(this.n == 1) {
			return 1;
		}
		
		MyRecursiveFibonacciTask newTask1 = new MyRecursiveFibonacciTask(n - 1);
		MyRecursiveFibonacciTask newTask2 = new MyRecursiveFibonacciTask(n - 2);
		
		invokeAll(newTask1, newTask2);
		
		return newTask1.join() + newTask2.join();
	}


}
