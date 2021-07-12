package producerConsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TwoConsumerBlockingQueue {

	public static void main(String[] args) {
		int howMany = 1_000_000;

		BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
		ProducerBlockingQueue producer = new ProducerBlockingQueue(queue, howMany);
		
		ConsumerBlockingQueue consumer1 = new ConsumerBlockingQueue(queue);
		ConsumerBlockingQueue consumer2 = new ConsumerBlockingQueue(queue);
		producer.start();
		consumer1.start();
		consumer2.start();
		
		try {
			producer.join();
			consumer1.interrupt();
			consumer2.interrupt();
			consumer1.join();
			consumer2.join();
			Util.format("sum %d\n (%d+%d)\n misses: %d\n", consumer1.sum + consumer2.sum, consumer1.sum, consumer2.sum,
					consumer1.misses + consumer2.misses);
		} catch (InterruptedException e) {
		}
	}
}
