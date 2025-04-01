package application;

public class StudentNode {
	// Variables
	private Object element;
	private StudentNode next;

	// constructor
	public StudentNode(Object element) {
		this(element, null);
	}

	// constructor
	public StudentNode(Object element, StudentNode next) {
		this.element = element;
		this.next = next;
	}

	// getter and setter
	public Object getElement() {
		return element;
	}

	public void setElement(Object element) {
		this.element = element;
	}

	public StudentNode getNext() {
		return next;
	}

	public void setNext(StudentNode next) {
		this.next = next;
	}

}