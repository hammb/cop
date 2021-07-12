package a3;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

public class synchronizedQueueTest {
	
	@Test
	public void test() throws InterruptedException {
		
		Queue<Integer> notSynchronizedQueue = new LinkedList<Integer>();
		
		Queue<Integer> notSynchronizedQueueTemplate = new LinkedList<Integer>();
		SynchronizedQueue<Integer> synchronizedQueue = new SynchronizedQueue<Integer>(notSynchronizedQueueTemplate);
		
		long sum = 0;
		long SyncSum = 0;
		long AsyncSum = 0;
		
		for(int i = 0; i < 100000; i ++) {
			notSynchronizedQueue.add(i);
			synchronizedQueue.add(i);
			sum += i;
		}
		
		ReadAndSumNumbers[] threadsSync = new ReadAndSumNumbers[10];
		ReadAndSumNumbers[] threadsAsync = new ReadAndSumNumbers[10];

		int i;
		for (i = 0; i < 10; i++) {
			threadsSync[i] = new ReadAndSumNumbers(synchronizedQueue);
			threadsAsync[i] = new ReadAndSumNumbers(notSynchronizedQueue);
		}

		for (i = 0; i < 10; i++) {
			threadsSync[i].start();
			threadsAsync[i].start();
		}
		
		for (i = 0; i < 10; i++) {
			threadsSync[i].join();
			SyncSum += threadsSync[i].getSum();
			threadsAsync[i].join();
			AsyncSum += threadsAsync[i].getSum();
			
		}
		
		System.out.println(sum);
		System.out.println(SyncSum);
		System.out.println(AsyncSum);
		
		Assert.assertEquals(SyncSum, sum);
	}
	
	public class ReadAndSumNumbers extends Thread{

		Queue<Integer> queue;
		long sum = 0;
		
		public ReadAndSumNumbers(Queue<Integer> queue) {
			this.queue = queue;
		}

		public long getSum() {
			return sum;
		}

		@Override
		public void run() {
			while(!this.queue.isEmpty()) {
				this.sum += this.queue.remove();
			}
		}
	}
}
