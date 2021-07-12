package threadProgramming;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Starvation extends Thread {
	final static int NOTH = 20;
	final static int SPECIAL = 19;
	static Lock[] locks = new Lock[NOTH];
	static int[] values = new int[NOTH];

	static {
		for (int i = 0; i < locks.length; i++) {
			locks[i] = new ReentrantLock();
		}
	}

	private int id;

	public Starvation(int id) {
		this.id = id;
	}

	public void run() {
		while (!isInterrupted()) {
			if (id == SPECIAL) {
				doSpecial();
			} else {
				doNormal();
			}
		}
	}

	public void doNormal() {
		if (locks[id].tryLock()) {
			values[id] += 1;
			locks[id].unlock();
		}
	}

	public void doSpecial() {
		boolean[] haveIt = new boolean[NOTH];
		boolean hasAll = true;
		for (int i = 0; i < locks.length; i++) {
			haveIt[i] = locks[i].tryLock();
			if (!haveIt[i]) {
				hasAll = false;
			}
		}
		if (hasAll) {
			values[id] += 1;
		}
		for (int i = 0; i < locks.length; i++) {
			if (haveIt[i]) {
				locks[i].unlock();
			}
		}
	}

	public static void main(String[] args) {
		
		Starvation[] threads = new Starvation[NOTH];
		for (int i = 0; i < NOTH; i++) {
			threads[i] = new Starvation(i);
		}
		for (int i = 0; i < NOTH; i++) {
			threads[i].start();
		}
		Util.sleep(3000);
		for (int i = 0; i < NOTH; i++) {
			threads[i].interrupt();
		}
		for (int i = 0; i < NOTH; i++) {
			Util.format("%02d: %8d ", i, values[i]);
			if ((i + 1) % 2 == 0) {
				Util.format("\n");
			}
		}
	}
}
