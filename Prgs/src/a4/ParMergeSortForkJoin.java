package a4;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParMergeSortForkJoin implements SortAlg {

	int numThreads = 1;

	@Override
	public void sort(int[] a) {
		ForkJoinPool forkJoinPool = new ForkJoinPool(numThreads);
		RecursiveSortTask sortTask = new RecursiveSortTask(a);
		int[] result = forkJoinPool.<int[]>invoke(sortTask);
		for (int i = 0; i < result.length; i++)
			a[i] = result[i];
		forkJoinPool.shutdown();
	}

	@Override
	public int setNumThreads(int numThreads) {
		this.numThreads = numThreads;
		return numThreads;
	}

	public class RecursiveSortTask extends RecursiveTask<int[]> {

		int[] intArr;

		public RecursiveSortTask(int[] intArr) {
			this.intArr = intArr;
		}

		@Override
		protected int[] compute() {

			if (this.intArr.length < 1000) {
				Arrays.sort(this.intArr);
				return this.intArr;
			}

			RecursiveSortTask split1 = new RecursiveSortTask(
					Arrays.copyOfRange(this.intArr, 0, this.intArr.length / 2));
			RecursiveSortTask split2 = new RecursiveSortTask(
					Arrays.copyOfRange(this.intArr, this.intArr.length / 2, this.intArr.length));

			invokeAll(split1, split2);

			return merge(split1.join(), split2.join());
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

			for(i = i; i < split1.length; i++) {
				result[k] = split1[i];
				k++;
			}
				
			for(j = j; j < split2.length; j++) {
				result[k] = split2[j];
				k++;
			}	
				
			return result;
		}

	}

}
