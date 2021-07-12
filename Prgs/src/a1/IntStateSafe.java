package a1;

public class IntStateSafe implements IntState{

	private int counter;
	
	
	public IntStateSafe() {
		counter = 0;
	}
	
	@Override
	public synchronized void inc(int amount) {
		this.counter += amount;
		
	}

	@Override
	public synchronized void dec(int amount) {
		this.counter -= amount;
		
	}

	@Override
	public int getValue() {
		return this.counter;
	}

	@Override
	public synchronized void setValue(int value) {
		this.counter = value;
		
	}

}
