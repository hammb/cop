package a4;

import java.util.Arrays;

public class ParMergeSortSplit implements SortAlg {

	int numThreads = 1;
	
	@Override
	public void sort(int[] a) {
		SortAndMergeThread aThread = new SortAndMergeThread(a);
		aThread.start();
		try {
			aThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int[] result = aThread.intArr;
		
		for (int i = 0; i < result.length; i++)
			a[i] = result[i];

	}

	@Override
	public int setNumThreads(int numThreads) {
		this.numThreads = numThreads;
		return numThreads;
	}

	public class SortAndMergeThread extends Thread {

		int[] intArr;
		
		public SortAndMergeThread(int[] a) {
			this.intArr = a;
		}

		public void run() {
			if (this.intArr.length < 10000) {
				Arrays.sort(this.intArr);
				return; 
			}
			
			SortAndMergeThread split1Thread = new SortAndMergeThread(
					Arrays.copyOfRange(this.intArr, 0, this.intArr.length / 2));
			SortAndMergeThread split2Thread = new SortAndMergeThread(
					Arrays.copyOfRange(this.intArr, this.intArr.length / 2, this.intArr.length));

			split1Thread.start();
			split2Thread.start();
			
			try {
				split1Thread.join();
				split2Thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.intArr = merge(split1Thread.intArr, split2Thread.intArr);
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
