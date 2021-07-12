package a3;

import java.util.Queue;

public class MyCollections {

	static <E> Queue<E> synchronizedQueue(Queue<E> queue){
		return new SynchronizedQueue<>(queue);
	}

}
