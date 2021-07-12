package tasksAndThreads;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

public class TPTExecutor implements Executor {
	public void execute(Runnable command) {
		new Thread(command).start();
	}

	public static void main(String[] args) {
		Executor executor = new TPTExecutor();
		CountDownLatch latch = new CountDownLatch(4);
		Util.resetTime();
		for (int i = 4; i >= 1; i -= 1) {
			final int j = i;
			Runnable cmd = () -> {
				Util.format("%4d msecs\n", j * 200);
				Util.sleep(j * 200);
				latch.countDown();
			};
			executor.execute(cmd);
		}
		try {
			latch.await();
		} catch (InterruptedException ignored) {
		}
		Util.format("%4d msecs total\n", Util.getTimeMillis());
	}
}