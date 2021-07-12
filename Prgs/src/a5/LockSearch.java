package a5;

public class LockSearch implements Search {

	Node root = null;

	@Override
	public void add(int value) {
		this.root = addNode(this.root, value);
	}

	private synchronized Node addNode(Node node, int value) {
		if (node == null) {
			node = new Node(value);
			return node;
		}
		
		if (value > node.getValue()) {
			node.setRight(addNode(node.getRight(), value));
		} else if (value < node.getValue()) {
			node.setLeft(addNode(node.getLeft(), value));
		} 
		
		return node;
	}

	private synchronized Node containsNode(Node node, int value) {
		if (node == null || node.getValue() == value) {
			return node;
		}
		
		if (value > node.getValue()) {
			containsNode(node.getRight(), value);
		} else if (value < node.getValue()) {
			containsNode(node.getLeft(), value);
		} 
		
		return node;
	}
	
	@Override
	public boolean contains(int value) {
		return (containsNode(this.root, value) != null);
	}

}
