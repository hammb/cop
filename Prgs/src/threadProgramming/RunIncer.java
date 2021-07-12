package threadProgramming;

public class RunIncer {
	public static void main(String[] args) {
		Incer incer = new Incer();
		incer.start();
		Util.sleep(3000); // at most 3 secs
		incer.quit(); // not stop
		boolean alive = incer.isAlive();
		Util.format("Alive1?: %b\n", alive);
		Util.join(incer);
		alive = incer.isAlive();
		Util.format("Alive2?: %b\n", alive);
		
		IncerInterrupt inter_incer = new IncerInterrupt();
		inter_incer.start();
		Util.sleep(3000); // at most 3 secs
		inter_incer.quit(); // not stop
		alive = inter_incer.isAlive();
		Util.format("Alive1?: %b\n", alive);
		Util.join(inter_incer);
		alive = inter_incer.isAlive();
		Util.format("Alive2?: %b\n", alive);
	}
}
