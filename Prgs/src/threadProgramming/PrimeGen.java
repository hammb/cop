package threadProgramming;

import java.util.ArrayList;
import java.util.List;

public class PrimeGen {
	
	private List<Integer> getDivs(int x) {
		List<Integer> divs = new ArrayList<>();
		for (int div = 2; div < x; div += 1) {
			if (x % div == 0) {
				divs.add(div);
			}
		}
		return divs;
	}

	private int prime = 1;

	public int next() {
		
		do { // start with two
			prime += 1;
		} while (!getDivs(prime).isEmpty());
		
		return prime;
	}
	
	public static void main(String[] args) {
		PrimeGen pgen = new PrimeGen();
		
		System.out.println(pgen.next());
		System.out.println(pgen.next());
		System.out.println(pgen.next());
	}
}