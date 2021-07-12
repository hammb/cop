package a5;

import java.util.concurrent.locks.ReentrantLock;

public class LockNSearch implements Search {

	private int treeDepth;

	private Node root;

	private ReentrantLock mainLock = new ReentrantLock();

	private ReentrantLock[] subLocks;
	private UnsafeSearch[] subTrees;

	public LockNSearch(int treeDepth) {
		this.treeDepth = treeDepth;
		this.root = null;

		int leafs = (int) Math.pow(2., treeDepth - 1);
		this.subLocks = new ReentrantLock[leafs];

		for (int i = 0; i < this.subLocks.length; i++) {
			this.subLocks[i] = new ReentrantLock();
		}

		this.subTrees = new UnsafeSearch[leafs];
	}

	@Override
	public void add(int value) {
		this.mainLock.lock();

		if (this.root == null) {
			this.root = new Node(value);
			this.mainLock.unlock();
		} else {
			addNode(this.root, value, 1, 0);
		}
	}

	private void addNode(Node node, int value, int depth, int index) {

		if (depth == this.treeDepth) {
			try {
				this.subLocks[index].lock();
			} finally {
				this.mainLock.unlock();
			}

			if (this.subTrees[index] == null) {
				this.subTrees[index] = new UnsafeSearch();
			}

			this.subTrees[index].add(value);
			this.subLocks[index].unlock();
			return;
		}

		if (value < node.value) {
			if (node.left == null && depth != this.treeDepth - 1) {
				node.left = new Node(value);
				this.mainLock.unlock();
			} else {
				addNode(node.left, value, depth + 1, index * 2);
			}
		} else if (value == node.value) {
			this.mainLock.unlock();
		} else {

			if (node.right == null && depth != this.treeDepth - 1) {
				node.right = new Node(value);
				this.mainLock.unlock();
			} else {
				addNode(node.right, value, depth + 1, index * 2 + 1);
			}
		}
	}

	private boolean containsNode(Node node, int value, int depth, int index) {
		if (depth == this.treeDepth) {

			try {
				this.subLocks[index].lock();
			} finally {
				this.mainLock.unlock();
			}

			if (this.subTrees[index] == null) {
				this.subLocks[index].unlock();
				return false;
			}

			try {
				return this.subTrees[index].contains(value);
			} finally {
				this.subLocks[index].unlock();
			}
		}

		if (node == null) {
			this.mainLock.unlock();
			return false;
		}

		if (value == node.value) {
			this.mainLock.unlock();
			return true;
		}

		if (value < node.value) {
			return containsNode(node.left, value, depth + 1, 2 * index);
		}

		return containsNode(node.right, value, depth + 1, 2 * index + 1);
	}

	@Override
	public boolean contains(int value) {
		this.mainLock.lock();

		if (this.root == null) {
			this.mainLock.unlock();
			return false;
		}

		if (this.root.value == value) {
			this.mainLock.unlock();
			return true;
		}

		return containsNode(this.root, value, 1, 0);
	}

	public static void main(String[] args) {

		LockNSearch lns = new LockNSearch(1);

	}

}
