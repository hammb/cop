package producerConsumer;

import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyProdConWaitNotify {

	public static void main(String[] args) {
		
		Lock qLock = new ReentrantLock();
		int nMax = 1_000_000;
		LinkedList<Integer> list = new LinkedList<Integer>();
		
		MyProducerNotify mpw = new MyProducerNotify(qLock, list, nMax);
		MyConsumerWait mcw = new MyConsumerWait(qLock, list);

		mpw.start();
		mcw.start();
		
		try {
			mpw.join();
			mcw.interrupt();
			mcw.running.compareAndSet(true, false);
			mcw.join();
			Util.format("sum %d misses: %d\n", mcw.sum, mcw.misses);
		} catch (InterruptedException e) {
			
		}
	}

}
