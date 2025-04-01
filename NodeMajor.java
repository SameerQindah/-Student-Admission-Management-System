package application;

public class NodeMajor {
	// variables
	private Object element;
	private NodeMajor prev, next;

	// constructor
	public NodeMajor(Object element) {

		this(element, null, null);

	}

	// constructor
	public NodeMajor(Object element, NodeMajor prev, NodeMajor next) {

		this.element = element;
		this.prev = prev;
		this.next = next;

	}

	// getter and setter
	public Object getElement() {
		return element;
	}

	public void setElement(Object element) {
		this.element = element;
	}

	public NodeMajor getPrev() {
		return prev;
	}

	public void setPrev(NodeMajor prev) {
		this.prev = prev;
	}

	public NodeMajor getNext() {
		return next;
	}

	public void setNext(NodeMajor next) {
		this.next = next;
	}

}