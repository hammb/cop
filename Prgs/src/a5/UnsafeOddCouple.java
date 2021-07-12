package a5;

public class UnsafeOddCouple implements OddCouple{

	int x = 1;
	int y = 0;

	@Override
	public int getX() {
		
		return this.x;
	}

	@Override
	public void setX(int x) {
		
		this.x = x;
		
		if((x + this.y) % 2 == 0) {
			this.x = x + 1;
		}
		
	}

	@Override
	public int getY() {
		
		return this.y;
	}

	@Override
	public void setY(int y) {
		
		this.y = y;
		
		if((y + this.x) % 2 == 0) {
			this.y = y + 1;
		}
	}

	@Override
	public int getSum() {
		
		return this.x + this.y;
	}
	
	public static void main(String[] args) {
		
		UnsafeOddCouple uoc = new UnsafeOddCouple();
		uoc.setX(2);
		uoc.setY(2);
		
		System.out.println(uoc.getSum());
	}

}
