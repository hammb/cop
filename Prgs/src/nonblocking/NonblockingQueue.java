package nonblocking;

import java.util.concurrent.atomic.AtomicReference;

public class NonblockingQueue<T> {

	private static class Node<T> {
		public final T ele;
		public final AtomicReference<Node<T>> next;

		public Node(T ele, Node<T> next) {
			this.ele = ele;
			this.next = new AtomicReference<>(next);
		}
	}

	private final Node<T> dummy = new Node<>(null, null);
	private AtomicReference<Node<T>> head, tail;

	public NonblockingQueue() {
		head = new AtomicReference<>();
		tail = new AtomicReference<>();
		head.set(dummy);
		tail.set(dummy);
	}

	public boolean put(T ele) {
		Node<T> newNode = new Node<>(ele, null);
		while (true) { // until actually put
			Node<T> curTail = tail.get();
			Node<T> tailNext = curTail.next.get();
			if (curTail == tail.get()) {
				if (tailNext != null) { // intermediate
					tail.compareAndSet(curTail, tailNext);
				} else { // completed
					if (curTail.next.compareAndSet(null, newNode)) {
						tail.compareAndSet(curTail, newNode);
						return true;
					}
				}
			}
		}
	}

	public T take() {
		while (true) {
			Node<T> curHead = head.get();
			Node<T> curTail = tail.get();
			Node<T> first = curHead.next.get();

			if (curHead == head.get()) {
				if (curHead == curTail) {
					if (first == null) {
						return null; // empty
					} else { // intermediate
						// update tail
						tail.compareAndSet(curTail, first);
					}
				} else {
					T ret = first.ele; // node value first
					if (head.compareAndSet(curHead, first)) {
						// old first will be new dummy
						return ret;
					}
				}
			}

		}
	}

}
