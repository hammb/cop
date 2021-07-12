package a4;

import java.util.Arrays;

public class NativeSort implements SortAlg {
	@Override public void sort(int[] a) {
		Arrays.sort(a);
	}
	@Override public int setNumThreads(int numThreads) {
		return 1; // well ignore, it is serial 
	}
}
