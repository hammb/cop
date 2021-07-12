package a3;

import java.util.Map;

public class BenchMapFillingThread extends Thread{

	Map<Integer, Integer> map;
	Integer[] numbers;
	
	public BenchMapFillingThread(Map<Integer, Integer> map, Integer[] numbers) {
		this.map = map;
		this.numbers = numbers;
	}
	
	public void run() {
		for(int i = 0; i < this.numbers.length; i ++) {
			map.put(this.numbers[i], this.numbers[i]);
		}
	}
}
