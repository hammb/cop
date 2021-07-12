package a1;

public class IntStateUnsafe implements IntState{

	private int counter;
	
	
	public IntStateUnsafe() {
		counter = 0;
	}
	
	@Override
	public void inc(int amount) {
		this.counter += amount;
		
	}

	@Override
	public void dec(int amount) {
		this.counter -= amount;
		
	}

	@Override
	public int getValue() {
		return this.counter;
	}

	@Override
	public void setValue(int value) {
		this.counter = value;
		
	}

}
