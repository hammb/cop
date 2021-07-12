package nonblocking;

import java.util.concurrent.atomic.AtomicReference;

public class NonblockingStack<T> {

	private AtomicReference<NodeStack<T>> head = new AtomicReference<>();

	public class NodeStack<T> {
		T ele;
		NodeStack<T> next;

		public NodeStack(T ele) {
			this.ele = ele;
		}
	}

	public void push(T ele) {
		NodeStack<T> newNode = new NodeStack<T>(ele);
		boolean ok = false;

		do {
			NodeStack<T> oldNode = head.get();
			newNode.next = oldNode;
			ok = head.compareAndSet(oldNode, newNode);
		} while (!ok);
	}

	public T pop() {
		NodeStack<T> oldHead;
		if (head.get() == null) {
			return null;
		}
		boolean ok = false;

		do {
			oldHead = head.get();
			ok = head.compareAndSet(oldHead, oldHead.next);

		} while (!ok);
		return oldHead.ele;
	}
}
