package sharingState;

import producerConsumer.Util;

public class UnsafePublication {
	static Value val = new Value();

	static class Value {
		long x = 0, y = 0, z = 0;

		public Value() {
			x = System.nanoTime() % 17;
			y = System.nanoTime() % 17;
			z = 42 - (x + y);
		}

		public void bad() {
			if (x + y + z != 42) {
				Util.format("BAD\n");
				System.exit(0);
			}
		}
	}

	public static void main(String[] args) {
		Thread publish = new Thread() {
			public void run() {
				for (int i = 10_000_000; i >= 0; i -= 1) {
					val = new Value();
				}
			}
		};
		Thread[] reads = new Thread[1000];
		for (int j = 0; j < reads.length; j += 1) {
			reads[j] = new Thread(() -> {
				for (int i = 10_000_000; i >= 0; i -= 1) {
					val.bad();
				}
			});
		}
		for (Thread read : reads)
			read.start();
		publish.start();
		Util.join(publish);
		Util.joinall(reads);

	}

}
