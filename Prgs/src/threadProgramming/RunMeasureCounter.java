package threadProgramming;

public class RunMeasureCounter {
	public static void main(String[] args) {
		RunCounter runC = new RunCounter();
		Counter uCounter = new UnsafeCounter();
		Counter sCounter = new SafeCounter();
		Util.resetTime();
		runC.run(uCounter);
		long umillis = Util.getTimeMillis();
		Util.resetTime();
		runC.run(sCounter);
		long smillis = Util.getTimeMillis();
		Util.format("U: %d ms S: %d ms\n", umillis, smillis);
	}
}