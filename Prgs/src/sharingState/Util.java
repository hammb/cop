package sharingState;

import java.io.PrintStream;

public class Util {
	public final static PrintStream format(String fmt) {
		return System.out.format(fmt);
	}

	public final static PrintStream format(String fmt, Object args) {
		return System.out.format(fmt, args);
	}

	public final static PrintStream format(String fmt, Object args, int p) {
		return System.out.format(fmt, args, p);
	}

	private static long nanoSecs;

	public final static void resetTime() {
		nanoSecs = System.nanoTime();
	}

	public final static long getTimeMicros() {
		long current = System.nanoTime();
		return (current - nanoSecs) / 1_000;
	}

	public final static long getTimeMillis() {
		long current = System.nanoTime();
		return (current - nanoSecs) / 1_000_000;
	}

	public static void sleep(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void join(Thread thread) {
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void joinall(Thread[] threads) {
		try {
			for(int i = 0; i < threads.length; i ++) {
				threads[i].join();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void format(String fmt, long count, long millis) {
		System.out.format(fmt, count, millis);
	}

	public static void format(String string, int diffs, int runs, int i) {
		System.out.format(string, diffs, runs, i);

	}

	public static void format(String string, int diffs, int rUNS, long time) {
		System.out.format(string, diffs, rUNS, time);

	}

	public static void println(String string) {
		System.out.println(string);

	}

	public static void format(String string, long l, long sum, long sum2, long m) {
		System.out.format(string, l, sum, sum2, m);

	}
}
