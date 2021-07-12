package nonblocking;

public class SimulatedCAS {
	private int value;

	public synchronized int get() {
		return value;
	}

	public int compareAndSwap(int want, int set) {
		synchronized (this) {
			int old = value;
			if (old == want)
				value = set;
			return old;
		}
	}

	public boolean compareAndSet(int want, int set) {
		int v = compareAndSwap(want, set);
		return (want == v);
	}
}