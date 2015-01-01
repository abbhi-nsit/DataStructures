package com.akash.linkedlist;

public class MyLinkedList {

	private Node root;

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	//logic should be changed.
	//last node must be maintained
	public void add(int value) {
		Node node = getRoot();
		if (node == null) {
			setRoot(createNewNode(value, null));
		} else {
			while (node.getNext() != null) {
				node = node.getNext();
			}
			node.setNext(createNewNode(value, null));
		}
	}

	private Node createNewNode(int value, Node next) {
		Node node = new Node();
		node.setValue(value);
		node.setNext(next);
		return node;
	}

	public void delete(int value) {
		Node node = getRoot();
		if (node != null && node.getValue() == value) {
			setRoot(node.getNext());
			return;
		}
		Node tempNode = node;
		node = node.getNext();
		while (node != null) {
			if (node.getValue() == value) {
				tempNode.setNext(node.getNext());
				node.setNext(null);
				break;
			} else {
				tempNode = node;
				node = node.getNext();
			}
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		Node node = getRoot();
		while (node != null) {
			sb.append(node.getValue());
			node = node.getNext();
			if (node != null) {
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	public void reverse() {

		// Initialization of 4 Node variables
		Node temp1 = null;
		Node temp2 = getRoot();
		Node temp3 = null;
		Node temp4 = null;
		if (temp2 != null) {
			temp3 = temp2.getNext();
		}
		if (temp3 != null) {
			temp4 = temp3.getNext();
		}

		// iterating over Node variables and reversing 2 nodes in one iteration
		while (!(temp2 == null || temp3 == null)) {
			reverseFirstTwoElements(temp1, temp2, temp3);
			temp1 = temp3;
			temp2 = temp4;
			if (temp2 != null) {
				temp3 = temp2.getNext();
			}
			if (temp3 != null) {
				temp4 = temp3.getNext();
			}
		}

		// Base case condition, N=2m (where m is any integer)
		if (temp2 == null) {
			setRoot(temp1);
		}

		// Base case condition, N=2m+1 (where m is any integer)
		if (temp3 == null) {
			temp2.setNext(temp1);
			setRoot(temp2);
		}
	}

	private void reverseFirstTwoElements(Node temp1, Node temp2, Node temp3) {
		temp3.setNext(temp2);
		temp2.setNext(temp1);
	}

	/**
	 * Input 1 : 1,2,3,4,5 <br>
	 * Input 2 : 11,12,13,14,15 <br>
	 * Output : 1,11,2,12,3,13,4,14,5,15 <br>
	 * 
	 * @param l1
	 * @param l2
	 * @return
	 */
	public static MyLinkedList mergeList(MyLinkedList l1, MyLinkedList l2) {
		Node temp1 = l1.getRoot();
		Node temp2 = l2.getRoot();
		Node temp3 = null;

		// If l1 is empty then return l2
		if (temp1 == null) {
			return l2;
		}

		// Traversing parallel on both list until l1 and l2 are equal
		while (temp1 != null && temp2 != null) {
			temp3 = temp2;
			l2.setRoot(temp2.getNext());
			temp3.setNext(temp1.getNext());
			temp1.setNext(temp3);
			temp2 = l2.getRoot();
			temp1 = (temp1.getNext() != null) ? temp1.getNext().getNext()
					: null;
		}

		// when l1 is smaller than l2 , so at the end of parallel traversal
		if (temp1 == null && temp3 != null && temp3.getNext() == null) {
			temp3.setNext(l2.getRoot());
		}

		return l1;
	}

	/**
	 * reverse a linked list after n nodes. <br>
	 * eg: 1,2,3,4,5,6,7,8 <br>
	 * reverInParts(3) <br>
	 * 3,2,1,6,5,4,8,7 <br>
	 * 
	 * reverInParts(4) <br>
	 * 4,3,2,1,8,7,6,5 <br>
	 * 
	 * @param n
	 */
	//still in progress
	public void reverInParts(int n) {
		int counter=1;
		
		// Initialization of 4 Node variables
		Node temp1 = null;
		Node temp2 = getRoot();
		Node temp3 = null;
		Node temp4 = null;
		if (temp2 != null) {
			temp3 = temp2.getNext();
		}
		if (temp3 != null) {
			temp4 = temp3.getNext();
		}

		// iterating over Node variables and reversing 2 nodes in one iteration
		while (!(temp2 == null || temp3 == null)) {
			temp3.setNext(temp2);
			temp2.setNext(temp1);
			temp1 = temp3;
			temp2 = temp4;
			if (temp2 != null) {
				temp3 = temp2.getNext();
			}
			if (temp3 != null) {
				temp4 = temp3.getNext();
			}
		}

		// Base case condition, N=2m (where m is any integer)
		if (temp2 == null) {
			setRoot(temp1);
		}

		// Base case condition, N=2m+1 (where m is any integer)
		if (temp3 == null) {
			temp2.setNext(temp1);
			setRoot(temp2);
		}
	}
	
	public void printReverse() {
		System.out.print("[");
		printReverseList(getRoot());
		System.out.print("]");
	}
	
	private void printReverseList(Node node) {
		if(node==null) {
			return;
		}
		if(node.getNext()==null) {
			System.out.print(node.getValue());
		} else {
			printReverseList(node.getNext());
			System.out.print(",");
			System.out.print(node.getValue());
		}
	}
}