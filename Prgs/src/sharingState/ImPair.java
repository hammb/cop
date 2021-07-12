package sharingState;

import producerConsumer.Util;

public class ImPair {
	private final Integer e1;
	private final Integer e2;

	public ImPair(Integer e1, Integer e2) {
		this.e1 = e1;
		this.e2 = e2;
	}

	public Integer getOne() {
		return e1;
	}

	public Integer getTwo() {
		return e2;
	}

	public Integer getSum() {
		return e1 + e2;
	}

	static ImPair ip = new ImPair(0, 0);
	static volatile Integer val = Integer.valueOf(0);
	static volatile boolean run = true;

	static class Incer extends Thread {
		public void run() {
			while (run) {
				ip = new ImPair(val, val);
				val += 1;
			}
		}
	}

	static class Peeker extends Thread {
		public void run() {
			while (run) {
				if (ip.getSum() % 2 == 1)
					Util.format("BAD\n");
			}
		}
	}

	public static void main(String[] args) {
		new Incer().start();
		for (int i = 0; i < 100; i++) {
			new Peeker().start();
		}
		Util.sleep(2000);
		run = false;
	}

}