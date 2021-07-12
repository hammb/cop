package threadProgramming;

public class Unsynchronised {
	static int i = 0, j = 0, k = 0;

	public static class T extends Thread {
		public void run() {
			while (i == 0)
				k += 1;
			Util.format("done %d \n", j);
		}
	}

	public static void main(String[] args) {
		new T().start();
		i = 1;
		j = 1;
	}
}