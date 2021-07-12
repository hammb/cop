package tasksAndThreads;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.Random;

public class FirstTenRace {

	public static void main(String[] args) {
		ExecutorService exs = Executors.newCachedThreadPool();
		CompletionService<Integer> cs = new ExecutorCompletionService<>(exs);
		final Random random = new Random();

		for (int i = 0; i < 100; i += 1) {
			final int id = i;
			
			Callable<Integer> ci = () -> {
				Util.sleep(random.nextInt(1000));
				return id;
			};
			
			cs.submit(ci);
		}
		Util.resetTime();
		for (int i = 0; i < 10; i += 1) {
			Future<Integer> fi;
			
			try {
				fi = cs.take();
				Util.format("%d ", fi.get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		Util.format("\n 10: %4d msecs ", Util.getTimeMillis());
		for (int i = 0; i < 90; i += 1) {
				try {
					cs.take();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // drain
		}
		
		Util.format("\nall: %4d msecs\n", Util.getTimeMillis());
		exs.shutdown();

	}

}
