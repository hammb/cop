package a5;

import java.util.concurrent.atomic.AtomicStampedReference;

public class NonBlockingOddCouple implements OddCouple {
	int x = 0;
	int y = 1;

	public int getX() {
		return this.x;
	}

	public void setX(int x) {

		boolean ok = false;

		do {
			int oldX = this.x;
			int oldY = this.y;

			AtomicStampedReference<Integer> ref = new AtomicStampedReference<>(this.x, 0);

			if ((x + oldY) % 2 == 0) {
				ok = ref.compareAndSet(oldX, x + 1, 0, ref.getStamp() + 1);
			} else {
				ok = ref.compareAndSet(oldX, x, 0, ref.getStamp() + 1);
			}
		} while (!ok);
	}

	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		boolean ok = false;

		do {
			int oldX = this.x;
			int oldY = this.y;

			AtomicStampedReference<Integer> ref = new AtomicStampedReference<>(this.y, 0);

			if ((oldX + y) % 2 == 0) {
				ok = ref.compareAndSet(oldY, y + 1, 0, ref.getStamp() + 1);
			} else {
				ok = ref.compareAndSet(oldY, y, 0, ref.getStamp() + 1);
			}
		} while (!ok);
	}

	public int getSum() {
		return this.x + this.y;
	}
}