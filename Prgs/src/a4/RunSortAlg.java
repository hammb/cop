package a4;

import java.util.Random;
import java.util.Arrays;

public class RunSortAlg {
	final static int NUMBER_OF_RUNS=2;
	
	private static int numThreads;	
	static {
		int noCore = Runtime.getRuntime().availableProcessors();
		setNumThreads(noCore);
	}
	// makes sure that numThreads is a power of Two
	public static void setNumThreads(int n) {
		int powerOfTwo = Integer.highestOneBit(n);
		if (n != powerOfTwo) {
			n = powerOfTwo*2; // next higher power of two
		}
		numThreads = n;
	}

	private static long start = System.nanoTime();
	public static void resetTime() { 
		start = System.nanoTime();
	}
	public static long getTimeMillis() {
		return (System.nanoTime() - start)/1000000;
	}
	public static void reportTime(String msg, int noThreads, int size,
								 long minTime, long avgTime, long maxTime) {
		System.out.format("%23s #%2d %12d in [%6d   %6d~   %6d] msecs\n", 
						  msg, noThreads, size, minTime, avgTime, maxTime);
	}
	public static void reportResetTime(String msg, int size) {
		long used = getTimeMillis();
		System.out.format("%20s %12d in %6d msecs\n", 
						  msg, size, used);
		resetTime();
	}
	public static void reportResetTime(String msg) {
		long used = getTimeMillis();
		System.out.format("%20s                  in  %6d msecs\n", 
						  msg, used);
		resetTime();
	}

	static Random random = new Random();
	static int[] genRandom(int size) {
		int a[] = new int[size];
		for (int i=0; i<a.length; i++) {
			a[i] = random.nextInt();
		}
		return a;
	}

	static boolean isSorted(int[] a, int[] sorted) {
		if (a.length <= 0) {
			return true;
		}
		int previous = a[0];
		for (int i=0; i<a.length; i++) {
			if (previous > a[i]) {
				System.out.format("a[%d]=%d a[%d]=current=%d\n",
								  i-1, previous, i, a[i]);
				return false;
			}
			previous = a[i];
		}
		for (int i=0; i<a.length; i++) {
			if (a[i] != sorted[i]) {
				System.out.format("a[%d]=%d != sorted[%d]=%d\n",
								  i, a[i], i, sorted[i]);
				return false;
			}
		}
		return true;
	}

	public static void checkSort(SortAlg sortAlg, int[] orig) {
		int[] a = new int[orig.length];
		boolean alreadyRun = false;
		for (int noThreads=1; noThreads <= 8; noThreads *= 2) {
			long minTime = Long.MAX_VALUE;
			long maxTime = 0;
			long sumTime = 0;
			int usedNumThreads = sortAlg.setNumThreads(noThreads);
			if (usedNumThreads != noThreads && alreadyRun) {
				break;
			}
			alreadyRun = true;
			for (int i=0; i < NUMBER_OF_RUNS; i+=1) {
				System.arraycopy(orig, 0, a, 0, orig.length);
				resetTime();
				sortAlg.sort(a);
				long currentTime = getTimeMillis();
				if (currentTime < minTime) {
					minTime = currentTime;
				} 
				if (currentTime > maxTime) {
					maxTime = currentTime;
				}
				sumTime += currentTime;
			}
			reportTime(sortAlg.getClass().getName(), 
					   usedNumThreads, a.length,
					   minTime, sumTime/NUMBER_OF_RUNS, maxTime);
		}
		int[] b = new int[a.length];
		System.arraycopy(orig, 0, b, 0, orig.length);
		Arrays.sort(b);
		if (!isSorted(a, b)) {
			throw new RuntimeException("not sorted or not the same elements");
		}			
	}

	public static void header() {
		String msg = "%20s %5s %7s    [%6s %8s~ %8s] %s\n";
		System.out.format(msg, 
						  "SortAlgorithm", 
						  "#threads",
						  "a size",
						  "min",
						  "avg",
						  "max", 
						  "time");
	}

	public static void main(String[] args) {
		System.out.println("Running Sort Algorithms");
		System.out.format("  ~ # reported cores on this machine = %d\n", 
						  numThreads);
		// int[] sizes = {10000, 100000, 1000000, 2000000, 4000000, 10000000};
		int[] sizes = {10000, 100000, 1000000, 2000000, 4000000};
		if (args.length > 0) {
			sizes = new int[args.length];
			for (int i=0; i < args.length; i+=1) {
				sizes[i] = Integer.parseInt(args[0]);
			}
		}
		int[][] fields = new int[sizes.length][];
		resetTime();
		for (int i=0; i < sizes.length; i+=1) {
			fields[i] = genRandom(sizes[i]);
		}
		reportResetTime("Generation of fields");
		header();
		for (int[] a : fields) {
			checkSort(new NativeSort(), a);
			checkSort(new ParMergeSortForkJoin(), a);
			checkSort(new ParMergeSortExecutor(), a);
			checkSort(new ParMergeSortSplit(), a);
		}
	}

}