package a5;

public class UnsafeSearch implements Search {

	Node root = null;

	@Override
	public void add(int value) {
		this.root = addNode(this.root, value);
	}

	private Node addNode(Node node, int value) {
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

	private Node containsNode(Node node, int value) {
		if (node == null) {
			return null;
		}
		if (value == node.getValue()) {
			return node;
		} else if (value > node.getValue()) {
			return containsNode(node.getRight(), value);
		} else { // if (value < node.getValue()) {
			return containsNode(node.getLeft(), value);
		}
	}

	@Override
	public boolean contains(int value) {
		return (containsNode(this.root, value) != null);
	}

}
