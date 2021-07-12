package nonblocking;

public class RunStack {

	public static void main(String[] args) {
		NonblockingStack<Integer> nbs = new NonblockingStack<>();
		
		
		System.out.println(nbs.pop());
		nbs.push(1);
		nbs.push(2);
		nbs.push(3);
		System.out.println(nbs.pop());
		System.out.println(nbs.pop());
		System.out.println(nbs.pop());
		System.out.println(nbs.pop());
	}

}
