package a1;

public class StateCounter extends Thread{
	
	IntState opertions;
	int[] random_numbers_subset;
	
	public StateCounter(IntState unsafe_opertions, int[] random_numbers_subset) {
		this.opertions = unsafe_opertions;
		this.random_numbers_subset = random_numbers_subset;
	}
	
	public void run() {
		for(int i = 0; i < this.random_numbers_subset.length; i ++) {
			
			opertions.inc(random_numbers_subset[i]);
			
		}
	}
}
