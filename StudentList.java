package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StudentList {
	private StudentNode Front, Back; // Front and back pointers for the list
	private int Size; // Size of the list

	// Constructor to initialize the student list
	public StudentList() {
		Front = Back = null;
		Size = 0;
	}

	// Getter and setter for the front of the list
	public StudentNode getFront() {
		return Front;
	}

	public void setFront(StudentNode front) {
		Front = front;
	}

	// Getter and setter for the back of the list
	public StudentNode getBack() {
		return Back;
	}

	public void setBack(StudentNode back) {
		Back = back;
	}

	// Getter and setter for the size of the list
	public int getSize() {
		return Size;
	}

	public void setSize(int size) {
		Size = size;
	}

	// Add a new student at the beginning of the list
	public void addFirst(Object element) {
		StudentNode newNode = new StudentNode(element);
		if (Size == 0) {
			Front = Back = newNode;
		} else {
			newNode.setNext(Front);
			Front = newNode;
		}
		Size++;
	}

	// Get the first element in the list
	public Object getFirst() {
		if (Size == 0)
			return null;
		else
			return Front.getElement();
	}

	// Add a new student at the end of the list
	public void addLast(Object element) {
		StudentNode newNode = new StudentNode(element);
		if (Size == 0) {
			Front = Back = newNode;
		} else {
			Back.setNext(newNode);
			Back = newNode;
		}
		Size++;
	}

	// Get the last element in the list
	public Object getLast() {
		if (Size == 0)
			return null;
		else
			return Back.getElement();
	}

	// Get an element at a specific index
	public Object get(int index) {
		if (Size == 0)
			return null;
		else if (index == 0)
			return getFirst();
		else if (index == Size - 1)
			return getLast();
		else if (index > 0 && index < Size - 1) {
			StudentNode current = Front;
			for (int i = 0; i < index; i++)
				current = current.getNext();
			return current.getElement();
		} else
			return null;
	}

	// Remove the first element in the list
	public boolean removeFirst() {
		if (Size == 0)
			return false;
		else if (Size == 1)
			Front = Back = null;
		else
			Front = Front.getNext();

		Size--;
		return true;
	}

	// Remove the last element in the list
	public boolean removeLast() {
		if (Size == 0)
			return false;
		else if (Size == 1)
			Front = Back = null;
		else {
			StudentNode current = Front;
			for (int i = 0; i < Size - 2; i++)
				current = current.getNext();

			current.setNext(null);
			Back = current;
		}
		Size--;
		return true;
	}

	// Print the entire list from a given node
	public void PrintList(StudentNode current) {
		if (current != null) {
			System.out.println(current.getElement());
			PrintList(current.getNext());
		}
	}

	// Insert an element at a specific index
	public void insert(int index, Object element) {
		if (index == 0)
			addFirst(element);
		else if (index >= getSize())
			addLast(element);
		else {
			StudentNode newNode = new StudentNode(element);
			StudentNode current = Front;
			for (int i = 0; i < index - 1; i++)
				current = current.getNext();

			newNode.setNext(current.getNext());
			current.setNext(newNode);
			Size++;
		}
	}

	// Remove a student by their ID
	public boolean remove(int id) {
		if (Size == 0)
			return false;

		StudentNode current = Front, previous = null;
		while (current != null) {
			Student student = (Student) current.getElement();
			if (student.getId() == id) {
				if (previous == null) {
					Front = Front.getNext();
				} else {
					previous.setNext(current.getNext());
				}
				if (current == Back) {
					Back = previous;
				}
				Size--;
				return true;
			}
			previous = current;
			current = current.getNext();
		}
		return false;
	}

	// Update a student's information by their ID
	public boolean update(int id, String newName, double newTawjihiGrade, double newPlacementTestScore) {
		StudentNode current = Front;
		while (current != null) {
			Student student = (Student) current.getElement();
			if (student != null && student.getId() == id) {
				student.setName(newName);
				student.setTawjihiGrade(newTawjihiGrade);
				student.setPlacementTestGrade(newPlacementTestScore);
				return true;
			}
			current = current.getNext();
		}
		return false;
	}

	// Search for a student by their ID
	public Student search(int id) {
		StudentNode current = Front;

		while (current != null) {
			Student student = (Student) current.getElement();
			if (student.getId() == id) {
				return student;
			}
			current = current.getNext();
		}
		return null;
	}

	// Insert a student into the list to be inserted in order by admission Mark
	public void insertSortStudent(Student element) {
		StudentNode newNode = new StudentNode(element);

		// Case 1: If the list is empty, add the element as the first node
		if (Size == 0) {
			addFirst(element);
		}
		// Case 2: Insert at the first if the new student's admission mark is higher
		// than the first student's
		else if (((Student) getFirst()).compareTo(element) < 0) {
			newNode.setNext(Front);
			Front = newNode;
			Size++;
		}
		// Case 3: Insert at the end if the new student's admission mark is lower than
		// the last student's
		else if (((Student) getLast()).compareTo(element) > 0) {
			addLast(element);
		}
		// Case 4: Insert in the correct position within the sorted list (descending by
		// admission mark)
		else {
			StudentNode current = Front;
			while (current.getNext() != null && ((Student) current.getNext().getElement()).compareTo(element) > 0) {
				current = current.getNext();
			}

			// Insert new node after the current node
			newNode.setNext(current.getNext());
			current.setNext(newNode);
			Size++;
		}
	}

	// Convert the list of students to an ObservableList
	public ObservableList<Student> toObservableList() {
		ObservableList<Student> observableStudents = FXCollections.observableArrayList();
		StudentNode current = Front;
		while (current != null) {
			observableStudents.add((Student) current.getElement());
			current = current.getNext();
		}
		return observableStudents;
	}

	// Check if a student with a specific major name exists in the list
	public boolean getNameMajor(String nameMajor) {
		StudentNode current = Front;
		while (current != null) {
			Student student = (Student) current.getElement();
			if (((String) student.getMajor()).equalsIgnoreCase(nameMajor)) {
				return false;
			}
			current = current.getNext();
		}
		return true;
	}
}
