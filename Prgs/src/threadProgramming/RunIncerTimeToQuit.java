package threadProgramming;

public class RunIncerTimeToQuit {
	public static void main(String[] args) {
		Incer incer = new Incer();
		incer.start();
		Util.sleep(3000); // at most 3 secs
		Util.resetTime();
		incer.quit(); // do not call stop
		Util.join(incer);
		long quit = Util.getTimeMillis();
		Util.format("quit: %d msecs\n", quit);
	}
}
