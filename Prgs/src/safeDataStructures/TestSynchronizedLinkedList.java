package safeDataStructures;

public class TestSynchronizedLinkedList {

	public static void main(String[] args) {
		SynchronizedLinkedList<Integer> slist = new SynchronizedLinkedList<Integer>();
		Runnable drain = () -> {

			try {
				// slist is a synchronizedList
				synchronized (slist) {//CLient side locking
					while (!slist.isEmpty()) {
						slist.remove(0);
					}
				}
			} catch (IndexOutOfBoundsException e) {
				Util.format("OUCH\n");
			}
		};
		for (int i = 0; i < 100000; i += 1) {
			slist.add(Integer.valueOf(17));
		}
		Thread[] ts = new Thread[10];
		for (int i = 0; i < ts.length; i += 1) {
			ts[i] = new Thread(drain);
			ts[i].start();
		}
		Util.joinall(ts);

	}

}
