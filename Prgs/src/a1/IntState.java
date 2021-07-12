package a1;

public interface IntState {

	/** increment value by fixed amount
	 * @param amount int, value by which to increment
	 */ 
	public void inc(int amount);

	/** decrement value by fixed amount
	 * @param amount int, value by which to decrement
	 */ 
	public void dec(int amount);

	/** return the current value
	 * @return int, current value
	 */ 
	public int getValue();

	/** set the value
	 * @param value int, set the value
	 */ 
	public void setValue(int value);

}
