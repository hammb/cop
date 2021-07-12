package a2;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class CharacterGeneratorThread extends Thread {

	boolean work = true;
	SharedList list;
	int min = 0;
	int max = 20;

	public CharacterGeneratorThread(SharedList list) {
		this.list = list;
	}

	public void run() {
		char letter = 'a';
		while (this.work && !isInterrupted()) {
			try {
				TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(this.min, this.max + 1));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			letter = ((int) letter == 123) ? 'a' : letter; // check if beyond z
			this.list.add(letter);
			letter++;
		}
	}

	public void quit() {
		this.work = false;
		this.interrupt();
	}

}
