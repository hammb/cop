package sharingState;

import java.util.concurrent.atomic.AtomicLong;

public class ThreadLocalClass {

	static class Counter {
		private int count;

		public Counter() {
			count = 0;
		}

		public void inc() {
			count += 1;
		}

		public int val() {
			return count;
		}
	}

	static ThreadLocal<Counter> tcnt = new ThreadLocal<Counter>() {
		public Counter initialValue() {
			return new Counter();
		}
	};
	static AtomicLong allc = new AtomicLong();
	static int gRounds = 100_000;
	
	public static void main(String[] args) {
		Thread t[] = new Thread[10];
		for (int i = 0; i < t.length; i++)
			t[i] = new Thread() {
				public void run() {
					int rounds = gRounds;
					while (rounds-- > 0)
						tcnt.get().inc();
					allc.addAndGet(tcnt.get().val());
				}
			};
		for (int i = 0; i < t.length; i++)
			t[i].start();
		for (int i = 0; i < t.length; i++)
			Util.join(t[i]);
		if (allc.longValue() != t.length * gRounds)
			System.out.format("ERR %ld \n", allc.longValue());
		else
			System.out.format("fine\n");
	}
}
