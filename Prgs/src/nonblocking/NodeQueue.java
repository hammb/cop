package nonblocking;

import java.util.concurrent.atomic.AtomicReference;

public class NodeQueue<T> {

	T ele;
	AtomicReference<NodeQueue<T>> next ;

	public NodeQueue(T ele, NodeQueue<T> next) {
		this.ele = ele;
		this.next = new AtomicReference<>(next);
	}
	
	public NodeQueue(T ele) {
		this.ele = ele;
		this.next = null;
	}

	
}
