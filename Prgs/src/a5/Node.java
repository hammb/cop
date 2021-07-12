package a5;

public class Node {
	
	int value;
	
	Node left;
	Node right;
	

	Node(int value) {
		this.value = value;
		this.right = null;
		this.left = null;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

}
