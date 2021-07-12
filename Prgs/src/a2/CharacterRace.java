package a2;

public class CharacterRace {
	
	CharacterGeneratorThread threads[];
	int num_threads = 1; 
	
	SharedList sl;
	
	public CharacterRace() {
		generateThreads();
	}
	
	public void generateThreads() {
		
		this.sl = new SharedList();
		
		this.threads = new CharacterGeneratorThread[this.num_threads];
		
		for (int i = 0; i < this.num_threads; i++) {
			this.threads[i] = new CharacterGeneratorThread(this.sl);
		}
		
		for (int i = 0; i < this.num_threads; i++) {
			this.threads[i].start();
		}
		
		sl.countOccurrences(this.num_threads, this.threads);
		
		
	}
		
	public static void main(String[] args) {
		new CharacterRace();
	}

}
