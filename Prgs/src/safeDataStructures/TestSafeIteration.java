package safeDataStructures;

import java.util.concurrent.atomic.AtomicInteger;

public class TestSafeIteration {

	public static void main(String[] args) {
		SynchronizedLinkedList<Integer> slist = new SynchronizedLinkedList<Integer>();
		
		final AtomicInteger sum = new AtomicInteger();
		Runnable summing = () -> {
			synchronized (slist) {
			/*	for (Integer e : slist) {
					sum.addAndGet(e);
				}*/ // TODO
			}
		};
		for (int i = 0; i < 100_000; i += 1) {
			slist.add(Integer.valueOf(17));
		}
		Thread t = new Thread(summing);
		t.start();
		for (int i = 0; i < 100_000; i += 1) {
			slist.add(Integer.valueOf(17));
		}
		Util.join(t);
		Util.format("Sum: %d\n", sum.intValue());

	}

}
