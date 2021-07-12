package a4;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class ParMergeSortExecutor implements SortAlg {

	int numThreads = 1;

	AtomicBoolean ended = new AtomicBoolean(false);
	LinkedBlockingQueue<int[]> mergeArrays = new LinkedBlockingQueue<>();
	
	@Override
	public void sort(int[] a) {
		boolean end = false;
		int origSize = a.length;

		ExecutorService executor = Executors.newFixedThreadPool(this.numThreads);
		executor.submit(new SortTask(mergeArrays, a, executor));

		int[] split1 = new int[0];
		int[] split2 = new int[0];

		while (!end) {

			try {
				split1 = this.mergeArrays.take();

				if (split1.length >= origSize) {
					end = true;
					break;
				}

				split2 = this.mergeArrays.take();

				executor.submit(new MergeTask(this.mergeArrays, split1, split2));
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}

		for (int i = 0; i < split1.length; i++) {
			a[i] = split1[i];
		}

		executor.shutdown();
	}

	@Override
	public int setNumThreads(int numThreads) {
		this.numThreads = numThreads;
		return numThreads;
	}

	public class SortTask implements Runnable {

		int[] a;
		LinkedBlockingQueue<int[]> mergeArrays;
		ExecutorService executor;

		public SortTask(LinkedBlockingQueue<int[]> mergeArrays, int[] a, ExecutorService executor) {
			this.mergeArrays = mergeArrays;
			this.a = a;
			this.executor = executor;
		}

		@Override
		public void run() {
			if (this.a.length < 1000) {
				Arrays.sort(this.a);
				try {
					this.mergeArrays.put(this.a);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			}

			int[] split1 = Arrays.copyOfRange(this.a, 0, this.a.length / 2);
			int[] split2 = Arrays.copyOfRange(this.a, this.a.length / 2, this.a.length);

			this.executor.submit(new SortTask(this.mergeArrays, split1, this.executor));
			this.executor.submit(new SortTask(this.mergeArrays, split2, this.executor));
		}

	}

	public class MergeTask implements Runnable {

		int[] split1;
		int[] split2;
		LinkedBlockingQueue<int[]> mergeArrays;

		public MergeTask(LinkedBlockingQueue<int[]> mergeArrays, int[] split1, int[] split2) {
			this.mergeArrays = mergeArrays;
			this.split1 = split1;
			this.split2 = split2;
		}

		@Override
		public void run() {
			try {
				this.mergeArrays.put(merge(this.split1, this.split2));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public int[] merge(int[] split1, int[] split2) {

			int[] result = new int[split1.length + split2.length];

			int i = 0, j = 0, k = 0;

			while (split1.length > i && split2.length > j) {
				if (split1[i] > split2[j]) {
					result[k] = split2[j];
					j++;
				} else {
					result[k] = split1[i];
					i++;
				}

				k++;
			}

			for (i = i; i < split1.length; i++) {
				result[k] = split1[i];
				k++;
			}

			for (j = j; j < split2.length; j++) {
				result[k] = split2[j];
				k++;
			}

			return result;
		}

	}

}
