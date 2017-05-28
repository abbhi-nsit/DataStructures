package com.akash.docs.DS.tree;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Stack;

public final class BinarySearchTree<V extends Comparable<V>> implements Serializable {

	private static final long serialVersionUID = 42145L;
	private transient volatile Node<V> rootNode;
	private transient int size = 0;

	public BinarySearchTree() {
	}

	public BinarySearchTree(V rootData) {
		Objects.requireNonNull(rootData);
		rootNode = new Node<>();
		rootNode.setValue(rootData);
		size = 1;
	}

	public boolean add(V nodeData) {
		Objects.requireNonNull(nodeData);
		Node<V> newNode = new Node<>();
		newNode.setValue(nodeData);
		if (rootNode == null) {
			rootNode = newNode;
			size++;
			return true;
		}
		Node<V> tempNodeParent = null;
		Node<V> tempNode = rootNode;
		boolean isLeft = false;
		while (tempNode != null) {
			int compareResult = tempNode.getValue().compareTo(nodeData);
			if (compareResult == 0) {
				return false;
			}
			if (compareResult > 0) {
				tempNodeParent = tempNode;
				tempNode = tempNode.getLeft();
				isLeft = true;
			} else {
				tempNodeParent = tempNode;
				tempNode = tempNode.getRight();
				isLeft = false;
			}
		}
		if (isLeft) {
			tempNodeParent.setLeft(newNode);
		} else {
			tempNodeParent.setRight(newNode);
		}
		size++;
		return true;
	}

	public boolean delete(V nodeData) {
		Objects.requireNonNull(nodeData);
		if (rootNode == null) {
			return false;
		}
		Node<V> tempNodeParent = search(nodeData);
		Node<V> nodeToDelete = null;
		if (tempNodeParent == null) {
			return false;
		} else if (tempNodeParent.getLeft().getValue().compareTo(nodeData) == 0) {
			nodeToDelete = tempNodeParent.getLeft();
			deleteNode(nodeToDelete, tempNodeParent, true);
		} else {
			nodeToDelete = tempNodeParent.getRight();
			deleteNode(nodeToDelete, tempNodeParent, false);
		}
		size--;
		return true;
	}

	private void deleteNode(Node<V> nodeToDelete, Node<V> parentNode, boolean isLeft) {
		if (nodeToDelete.getLeft() == null && nodeToDelete.getRight() == null) {
			nodeToDelete.setValue(null);
			if (isLeft)
				parentNode.setLeft(null);
			else
				parentNode.setRight(null);
		} else if (nodeToDelete.getLeft() == null) {
			if (isLeft)
				parentNode.setLeft(nodeToDelete.getRight());
			else
				parentNode.setRight(nodeToDelete.getRight());
			nodeToDelete.setValue(null);
			nodeToDelete.setLeft(null);
			nodeToDelete.setRight(null);
		} else if (nodeToDelete.getRight() == null) {
			if (isLeft)
				parentNode.setLeft(nodeToDelete.getLeft());
			else
				parentNode.setRight(nodeToDelete.getLeft());
			nodeToDelete.setValue(null);
			nodeToDelete.setLeft(null);
			nodeToDelete.setRight(null);
		} else {
			boolean isLeftInorderSucceser = false;
			Node<V> inorderSucceserParent = nodeToDelete;
			Node<V> inorderSucceser = nodeToDelete.getRight();
			while (inorderSucceser.getLeft() != null) {
				isLeftInorderSucceser = true;
				inorderSucceserParent = inorderSucceser;
				inorderSucceser = inorderSucceser.getLeft();
			}
			nodeToDelete.setValue(inorderSucceser.getValue());
			deleteNode(inorderSucceser, inorderSucceserParent, isLeftInorderSucceser);
		}
	}

	public boolean find(V nodeData) {
		Objects.requireNonNull(nodeData);
		return search(nodeData) != null;
	}

	/**
	 * It will return parent for data if data exists
	 * 
	 * @param data
	 * @return
	 */
	private Node<V> search(V nodeData) {
		Objects.requireNonNull(nodeData);
		Node<V> tempNodeParent = null;
		Node<V> tempNode = rootNode;
		while (tempNode != null) {
			int compareResult = tempNode.getValue().compareTo(nodeData);
			if (compareResult == 0) {
				return tempNodeParent;
			} else if (compareResult > 0) {
				tempNodeParent = tempNode;
				tempNode = tempNode.getLeft();
			} else {
				tempNodeParent = tempNode;
				tempNode = tempNode.getRight();
			}
		}
		return null;
	}

	public String getInOrderTraversal() {
		StringBuffer nodesContent = new StringBuffer();
		doInOrderTraversal(rootNode, nodesContent);
		if (nodesContent.length() > 0) {
			nodesContent.deleteCharAt(nodesContent.length() - 1);
		}
		return nodesContent.toString();
	}

	private void doInOrderTraversal(Node<V> node, StringBuffer nodesContent) {
		if (node != null) {
			doInOrderTraversal(node.getLeft(), nodesContent);
			nodesContent.append(node.getValue()).append(",");
			doInOrderTraversal(node.getRight(), nodesContent);
		}
	}

	public static class Node<V extends Comparable<V>> {
		private Node<V> left;
		private Node<V> right;
		private V value;

		public Node<V> getLeft() {
			return left;
		}

		public void setLeft(Node<V> left) {
			this.left = left;
		}

		public Node<V> getRight() {
			return right;
		}

		public void setRight(Node<V> right) {
			this.right = right;
		}

		public V getValue() {
			return value;
		}

		public void setValue(V value) {
			this.value = value;
		}
	}

	private void writeObject(java.io.ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();
		s.writeInt(size);
		internalWriteEntriesInOrder(rootNode, s);
	}

	private void readObject(java.io.ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		int size = s.readInt();
		this.size = size;
		for (int i = 0; i < size; i++) {
			@SuppressWarnings("unchecked")
			V data = (V) s.readObject();
			add(data);
		}
	}

	private void internalWriteEntriesInOrder(Node<V> node, java.io.ObjectOutputStream s) throws IOException {
		if (node != null) {
			internalWriteEntriesInOrder(node.getLeft(), s);
			s.writeObject(node.getValue());
			internalWriteEntriesInOrder(node.getRight(), s);
		}
	}
	
	// utility methods

	public void printNodesByLevel() {
		Queue<Node<V>> queue = new LinkedList<Node<V>>();
		queue.offer(rootNode);
		int level = 0;
		while (!queue.isEmpty()) {
			int nodeCount = queue.size();
			level++;
			System.out.println("<-----level : " + level + " ------>");
			while (nodeCount > 0) {
				nodeCount--;
				Node<V> temp = queue.poll();
				System.out.print(temp.getValue() + " ");
				if (temp.left != null) {
					queue.offer(temp.left);
				}
				if (temp.right != null) {
					queue.offer(temp.right);
				}
			}
			System.out.println();
		}
	}

	public int getNodeLevel(V data) {
		if (data == null)
			return -1;
		Queue<Node<V>> queue = new LinkedList<Node<V>>();
		queue.offer(rootNode);
		int level = 0;
		while (!queue.isEmpty()) {
			int nodeCount = queue.size();
			level++;
			while (nodeCount > 0) {
				nodeCount--;
				Node<V> temp = queue.poll();
				if (temp.getValue().equals(data)) {
					return level;
				}
				if (temp.left != null) {
					queue.offer(temp.left);
				}
				if (temp.right != null) {
					queue.offer(temp.right);
				}
			}
		}
		return -1;
	}

	public void levelOrderTraversal() {
		int h = getHeight();
		for (int i = 1; i <= h; i++) {
			traverseBst(rootNode, i);
		}
		System.out.println();
	}

	private void traverseBst(Node<V> node, int counter) {
		if (counter > 1) {
			if (node.left != null) {
				traverseBst(node.left, counter - 1);
			}
			if (node.right != null) {
				traverseBst(node.right, counter - 1);
			}
		} else {
			System.out.print(node.getValue() + " ");
		}
	}

	public int getHeight() {
		Queue<Node<V>> queue = new LinkedList<Node<V>>();
		queue.offer(rootNode);
		int level = 0;
		while (!queue.isEmpty()) {
			int nodeCount = queue.size();
			level++;
			while (nodeCount > 0) {
				nodeCount--;
				Node<V> temp = queue.poll();
				if (temp.left != null) {
					queue.offer(temp.left);
				}
				if (temp.right != null) {
					queue.offer(temp.right);
				}
			}
		}
		return level;
	}

	public void spiralLevelOrderTraversal() {
		Stack<Node<V>> stack1 = new Stack<>();
		Stack<Node<V>> stack2 = new Stack<>();
		stack1.push(rootNode);
		while (!stack1.empty() || !stack2.empty()) {
			while (!stack1.empty()) {
				Node<V> temp = stack1.pop();
				System.out.print(temp.getValue() + " ");
				if (temp.left != null) {
					stack2.push(temp.left);
				}
				if (temp.right != null) {
					stack2.push(temp.right);
				}
			}
			while (!stack2.empty()) {
				Node<V> temp = stack2.pop();
				System.out.print(temp.getValue() + " ");
				if (temp.right != null) {
					stack1.push(temp.right);
				}
				if (temp.left != null) {
					stack1.push(temp.left);
				}
			}
		}
		System.out.println();
	}
}