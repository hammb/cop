package producerConsumer;

import java.util.LinkedList;
import java.util.Queue;

public class ProdConWaitNotify {
	public static void main(String[] args) {
		int howMany = 1_000_000;
		Queue<Integer> queue = new LinkedList<>();
		Object qlock = new Object();
		ProducerNotify producer = new ProducerNotify(queue, qlock, howMany);
		ConsumerWait consumer = new ConsumerWait(queue, qlock);
		producer.start();
		consumer.start();
		try {
			producer.join();
			consumer.done.set(true);
			consumer.interrupt();
			consumer.join();
			Util.format("sum %d misses: %d\n", consumer.sum, consumer.misses);
		} catch (InterruptedException e) {
		}
	}
}
