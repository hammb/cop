package a3;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BenchMap {

	HashMap<Integer, Integer> hashMap = new HashMap<>();

	Map<Integer, Integer> synMap = Collections.synchronizedMap(new HashMap<>());

	ConcurrentHashMap<Integer, Integer> conMap = new ConcurrentHashMap<>();

	int num_numbers = 1_000_000;
	Integer[] numbers = new Integer[num_numbers];

	public BenchMap() {
		for (int i = 0; i < this.num_numbers; i++) {
			this.numbers[i] = Integer.valueOf(i);
		}

		List<Integer> numbers = Arrays.asList(this.numbers);
		Collections.shuffle(numbers);
		this.numbers = numbers.<Integer>toArray(this.numbers);
	}

	public void measurePerformance(Map<Integer, Integer> map) {
		double duration = 0;
		int iterations = 1;
		double result = 0;

		do {
			iterations++;
			long startTime = System.nanoTime();
			for (int j = 0; j < iterations; j++) {
				for (int i = 0; i < this.numbers.length; i++)
					map.put(this.numbers[i], this.numbers[i]);
				map.clear();
			}
			long endTime = System.nanoTime();
			duration = (endTime - startTime) / 1_000_000.;
		} while (duration < 2_000.);

		result = duration / (double) iterations;
		System.out.printf("%f ms/iteration\n", result);
	}

	public void measurePerformanceConcurrently(Map<Integer, Integer> map, int num_threads) throws InterruptedException {

		int chunk_size = num_numbers / num_threads;
		double duration_latest = 0;
		double duration = 0;
		double result = 0;
		int iterations = 1;

		Thread[] threads = new Thread[num_threads];
		do {

			iterations++;

			for (int j = 0; j < iterations; j++) {
				for (int i = 0; i < 10; i++) {
					int start = i * chunk_size;
					int end = (i + 1) * chunk_size;
					threads[i] = new BenchMapFillingThread(map, Arrays.copyOfRange(numbers, start, end));
				}
				long startTime = System.nanoTime();
				for (int i = 0; i < 10; i++) {
					threads[i].start();
				}

				for (int i = 0; i < 10; i++) {
					threads[i].join();
				}
				map.clear();
				long endTime = System.nanoTime();
				duration_latest = (endTime - startTime) / 1_000_000.;
				duration += duration_latest;
			}

		} while (duration < 2_000.);
		result = duration / (double) iterations;
		System.out.printf("%f ms/iteration\n", result);

	}

	public void measurePerformanceRetrieveConcurrently(Map<Integer, Integer> map, int num_threads) throws InterruptedException {

		for (int i = 0; i < this.numbers.length; i++)
			map.put(this.numbers[i], this.numbers[i]);

		int chunk_size = num_numbers / num_threads;
		double duration = 0;

		Thread[] threads = new Thread[num_threads];

		for (int i = 0; i < 10; i++) {
			int start = i * chunk_size;
			int end = (i + 1) * chunk_size;
			threads[i] = new BenchMapRetrievingThread(map, Arrays.copyOfRange(numbers, start, end));
		}
		
		long startTime = System.nanoTime();
		for (int i = 0; i < 10; i++) {
			threads[i].start();
		}

		for (int i = 0; i < 10; i++) {
			threads[i].join();
		}
		map.clear();
		long endTime = System.nanoTime();
		duration = (endTime - startTime) / 1_000_000.;

		System.out.printf("%f ms/iteration\n", duration);

	}
	
	public void measurePerformanceRetrieve(Map<Integer, Integer> map) throws InterruptedException {

		for (int i = 0; i < this.numbers.length; i++)
			map.put(this.numbers[i], this.numbers[i]);

		
		double duration = 0;

		long startTime = System.nanoTime();
			for (int i = 0; i < this.numbers.length; i++)
				map.put(this.numbers[i], this.numbers[i]);
			map.clear();
		
		long endTime = System.nanoTime();
		
		duration = (endTime - startTime) / 1_000_000.;
		System.out.printf("%f ms/iteration\n", duration);

	}

	public static void main(String[] args) throws InterruptedException {
		BenchMap bm = new BenchMap();

		System.out.println("measurePerformance\n");
		bm.measurePerformance(bm.hashMap);
		bm.measurePerformance(bm.synMap);
		bm.measurePerformance(bm.conMap);

		System.out.println("measurePerformanceConcurrently 10\n");
		bm.measurePerformanceConcurrently(bm.synMap, 10);
		bm.measurePerformanceConcurrently(bm.conMap, 10);

		System.out.println("measurePerformanceConcurrently 100\n");
		bm.measurePerformanceConcurrently(bm.synMap, 100);
		bm.measurePerformanceConcurrently(bm.conMap, 100);
		
		System.out.println("measurePerformanceRetrieve hashMap\n");
		bm.measurePerformanceRetrieve(bm.hashMap);
		
		System.out.println("measurePerformanceRetrieveConcurrently synMap 1 10 100\n");
		bm.measurePerformanceRetrieve(bm.synMap);
		bm.measurePerformanceRetrieveConcurrently(bm.synMap, 10);
		bm.measurePerformanceRetrieveConcurrently(bm.synMap, 100);
		
		System.out.println("measurePerformanceRetrieveConcurrently conMap 1 10 100\n");
		bm.measurePerformanceRetrieve(bm.conMap);
		bm.measurePerformanceRetrieveConcurrently(bm.conMap, 10);
		bm.measurePerformanceRetrieveConcurrently(bm.conMap, 100);
	}

}
