package safeDataStructures;

import java.util.concurrent.ConcurrentHashMap;

public class MyConcurrentHashMap {

	public static void main(String[] args) {
		ConcurrentHashMap<Integer, Integer> shm = new ConcurrentHashMap<Integer, Integer>();

		shm.putIfAbsent(1, 1);
		shm.put(1, 2);
		
	}

}
