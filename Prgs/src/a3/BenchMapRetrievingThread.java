package a3;

import java.util.Map;

public class BenchMapRetrievingThread extends Thread{

	Map<Integer, Integer> map;
	Integer[] numbers;
	
	public BenchMapRetrievingThread(Map<Integer, Integer> map, Integer[] numbers) {
		this.map = map;
		this.numbers = numbers;
	}
	
	public void run() {
		for(int i = 0; i < this.numbers.length; i ++) {
			map.remove(numbers[i]);
		}
	}
}
