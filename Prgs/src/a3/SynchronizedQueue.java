package a3;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;



public class SynchronizedQueue<E> implements Queue<E> {

	private Queue<E> queue;

	public SynchronizedQueue(Queue<E> queue) {
		this.queue = queue;
	}

	@Override
	public synchronized boolean addAll(Collection<? extends E> arg0) {
		return this.queue.addAll(arg0);
	}

	@Override
	public synchronized void clear() {
		this.queue.clear();
	}

	@Override
	public synchronized boolean contains(Object arg0) {
		return this.queue.contains(arg0);
	}

	@Override
	public synchronized boolean containsAll(Collection<?> arg0) {
		return this.queue.containsAll(arg0);
	}

	@Override
	public synchronized boolean isEmpty() {
		return this.queue.isEmpty();
	}

	@Override
	public synchronized Iterator<E> iterator() {
		return this.queue.iterator();
	}

	@Override
	public synchronized boolean remove(Object arg0) {
		return this.queue.remove(arg0);
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		return this.queue.removeAll(arg0);
	}

	@Override
	public synchronized boolean retainAll(Collection<?>  arg0) {
		return this.queue.retainAll(arg0);
	}

	@Override
	public synchronized int size() {
		return this.queue.size();
	}

	@Override
	public synchronized Object[] toArray() {
		return this.queue.toArray();
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized Object[] toArray(Object[] arg0) {
		return this.queue.toArray(arg0);
	}

	@Override
	public synchronized boolean add(E arg0) {
		return this.queue.add(arg0);
	}

	@Override
	public synchronized E element() {
		return (E) this.queue.element();
	}

	@Override
	public synchronized boolean offer(E arg0) {
		return this.queue.offer(arg0);
	}

	@Override
	public synchronized E peek() {
		return this.queue.peek();
	}

	@Override
	public synchronized E poll() {
		return this.queue.poll();
	}

	@Override
	public synchronized E remove() {
		return this.queue.remove();
	}

}
