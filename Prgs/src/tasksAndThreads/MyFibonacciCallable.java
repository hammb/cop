package tasksAndThreads;

import java.util.concurrent.Callable;

public class MyFibonacciCallable implements Callable<Integer>{

	Integer n;
	
	public MyFibonacciCallable(Integer n) {
		this.n = n;
	}
	
	@Override
	public Integer call() throws Exception {
		return fibonacci(this.n);
	}
	
	public Integer fibonacci(Integer n) {
		
		if(n == 0) {
			return 0;
		}
		
		if(n == 1) {
			return 1;
		}
		
		return fibonacci(n-1) + fibonacci(n-2);
	}

}
