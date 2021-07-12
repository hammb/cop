package safeDataStructures;

import java.util.Iterator;
import java.util.LinkedList;

public class SynchronizedLinkedList<T> {
	private LinkedList<T> list;

	public SynchronizedLinkedList() {
		list = new LinkedList<>();
	}

	public synchronized boolean offer(T t) {
		return list.offer(t);
	}

	public T poll() {
		synchronized (this) {
			return list.poll();
		}
	}

	public synchronized boolean isEmpty() {
		return list.isEmpty();
	}

	public synchronized void remove(int i) {
		list.remove(i);
		
	}

	public synchronized void add(Integer valueOf) {
		list.add((T) valueOf);
		
	}
	
	public Iterator<T> iterator() {
        return list.iterator();
    }
}