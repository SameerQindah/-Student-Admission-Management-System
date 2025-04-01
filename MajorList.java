package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MajorList {
	private NodeMajor front, back; // Front and back pointers for the list
	private int size; // Size of the list
	private StudentList Slist = new StudentList(); // List of students

	// Constructor that initializes the list with a student list
	public MajorList(StudentList slist) {
		this.Slist = slist;
		front = null;
		back = null;
		size = 0;
	}

	// Getters and setters
	public int getSize() {
		return size;
	}

	public NodeMajor getFront() {
		return front;
	}

	public void setFront(NodeMajor front) {
		this.front = front;
	}

	public NodeMajor getBack() {
		return back;
	}

	public void setBack(NodeMajor back) {
		this.back = back;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public StudentList getSlist() {
		return Slist;
	}

	public void setSlist(StudentList slist) {
		Slist = slist;
	}

	// Add a new node at the beginning of the list
	public void addFirst(Object element) {
		NodeMajor newNode = new NodeMajor(element);
		if (size == 0) {
			front = back = newNode;
		} else {
			newNode.setNext(front);
			front.setPrev(newNode);
			front = newNode;
		}
		size++;
	}

	// Add a new node at the end of the list
	public void addLast(Object element) {
		NodeMajor newNode = new NodeMajor(element);
		if (size == 0) {
			front = back = newNode;
		} else {
			newNode.setPrev(back);
			back.setNext(newNode);
			back = newNode;
		}
		size++;
	}

	// Get the first element in the list
	public Object getFirst() {
		if (size == 0) {
			return null;
		} else {
			return front.getElement();
		}
	}

	// Get the last element in the list
	public Object getLast() {
		if (size == 0) {
			return null;
		}
		return back.getElement();
	}

	// Print the entire list starting from the front node
	private void printList(NodeMajor current) {
		current = front;
		while (current != null) {
			System.out.println(current.getElement() + " ");
			current.setNext(current);
		}
	}

	// Public method to print the list
	public void printList() {
		printList(front);
	}

	// Remove the first element in the list
	public boolean removeFirst() {
		if (size == 0) {
			return false;
		} else if (size == 1) {
			front = back = null;
			return true;
		} else {
			front = front.getNext();
			return true;
		}
	}

	// Remove the last element in the list
	public boolean removeLast() {
		if (size == 0) {
			return false;
		} else if (size == 1) {
			front = back = null;
			return true;
		} else {
			back = back.getPrev();
			return true;
		}
	}

	// Get the element at a specific index
	public Object get(int index) {
		NodeMajor node = front;
		int x = 0;
		while (node != null) {
			if (x == index) {
				return node.getElement();
			}
			x++;
			node.setNext(node);
		}
		return back;
	}

	// Insert an element at a specific index
	public void insert(int index, Object element) {
		if (index == 0)
			addFirst(element);
		else if (index >= getSize())
			addLast(element);
		else {
			NodeMajor newNode = new NodeMajor(element);
			NodeMajor current = front;
			for (int i = 0; i < index - 1; i++)
				current = current.getNext();

			newNode.setNext(current.getNext());
			newNode.setPrev(current);
			current.setNext(newNode);

			size++;
		}
	}

	// Remove an element at a specific index
	public boolean remove(int index) {
		if (size == 0)
			return false;
		else if (index == 0)
			return removeFirst();
		else if (index == size - 1)
			return removeLast();
		else if (index > 0 && index < size - 1) {

			NodeMajor current = front;
			for (int i = 0; i < index - 1; i++)
				current = current.getNext();
			current.setNext(current.getNext());
			current.getNext().setPrev(current);
			size--;
			return true;

		} else
			return false;
	}

	// Remove a major by its name
	public boolean remove(String name) {
		if (size == 0) {
			return false; // List is empty, nothing to remove
		}

		NodeMajor current = front;

		// Traverse the list to find the node with the matching name
		while (current != null) {
			Major major = (Major) current.getElement();

			if (major.getName().equalsIgnoreCase(name)) {
				// Node to be removed is the front node
				if (current == front) {
					front = current.getNext();
					if (front != null) {
						front.setPrev(null);
					} else {
						back = null; // List is now empty
					}
				}
				
				// Node to be removed is the back node
				else if (current == back) {
					back = current.getPrev();
					if (back != null) {
						back.setNext(null);
					}
				}
				// Node to be removed is in the middle of the list
				else {
					current.getPrev().setNext(current.getNext());
					current.getNext().setPrev(current.getPrev());
				}

				size--;
				return true; // Node successfully removed
			}

			current = current.getNext();
		}

		// No matching node was found
		return false;
	}

	// Search for a major by name and return it
	public Major search(String name) {
		NodeMajor current = front;
		while (current != null) {
			Major major = (Major) current.getElement();
			if (major.getName().equalsIgnoreCase(name)) {
				return major;
			}
			current = current.getNext();
		}
		return null;
	}

	// Update the details of a specific major
	public boolean update(String currentName, String newName, double newAcceptanceGrade, double newTawjihiWeight,
			double newPlacementTestWeight) {
		// Search for the major by the current name
		Major major = search(currentName);

		if (major != null) { // If the major is found
			// Validation checks
			if (newAcceptanceGrade < 0 || newAcceptanceGrade > 100) {
				System.out.println("Invalid acceptance grade: Must be between 0 and 100.");
				return false;
			}
			if (newTawjihiWeight < 0 || newTawjihiWeight > 1 || newPlacementTestWeight < 0
					|| newPlacementTestWeight > 1) {
				System.out.println("Invalid weightings: Each must be between 0 and 1.");
				return false;
			}
			if (newTawjihiWeight + newPlacementTestWeight != 1) {
				System.out.println("Invalid total weighting: Tawjihi and Placement Test weights must sum to 1.");
				return false;
			}

			// Update major information
			major.setName(newName); // Update name
			major.setAcceptanceGrade(newAcceptanceGrade);
			major.setTawjihiWeight(newTawjihiWeight);
			major.setPlacementTestWeight(newPlacementTestWeight);

			return true; // Return true to indicate the update was successful
		}

		return false; // Return false if the major was not found
	}

	// Insert a major in sorted order based on name
	public void insertSortMajor(Major element) {
		NodeMajor newNode = new NodeMajor(element);

		// Case 1: If the list is empty, add the element as the first node
		if (size == 0) {
			addFirst(element);
		}
		// Case 2: Insert at the beginning if the new element's name comes before the
		// first element's name
		else if (((Major) getFirst()).compareTo(element) > 0) {
			addFirst(element);
		}
		// Case 3: Insert at the end if the new element's name comes after the last
		// element's name
		else if (((Major) getLast()).compareTo(element) < 0) {
			addLast(element);
		}
		// Case 4: Insert in the correct alphabetical position within the sorted list
		else {
			NodeMajor current = front;
			while (current != null) {
				Major currentMajor = (Major) current.getElement();
				// If currentMajor comes after the new element alphabetically, insert before it
				if (currentMajor.compareTo(element) > 0) {
					newNode.setNext(current);
					newNode.setPrev(current.getPrev());

					if (current.getPrev() != null) {
						current.getPrev().setNext(newNode);
					}
					current.setPrev(newNode);

					// Update front if the new node is now the first node
					if (current == front) {
						front = newNode;
					}

					size++; // Increment size for the new node
					break;
				}
				current = current.getNext();
			}
		}
	}

	// Convert the list of majors to an ObservableList
	public ObservableList<Major> toObservableList() {
		ObservableList<Major> observableMajors = FXCollections.observableArrayList();
		NodeMajor current = front;
		while (current != null) {
			observableMajors.add((Major) current.getElement());
			current = current.getNext();
		}
		return observableMajors;
	}

	// Get the acceptance grade for a specific major
	public double getAcceptanceGrade(String nameMajor) {
		NodeMajor current = front;
		while (current != null) {
			Major major = (Major) current.getElement();
			if (major.getName().equalsIgnoreCase(nameMajor)) {
				return major.getAcceptanceGrade();
			}
			current = current.getNext();
		}
		// Return 101 if the major with the specified name is not found
		return 101;
	}

	// Check if a major name exists in the list
	public boolean getName(String nameMajor) {
		NodeMajor current = front;
		while (current != null) {
			Major major = (Major) current.getElement();
			if (major.getName().equalsIgnoreCase(nameMajor)) {
				return false;
			}
			current = current.getNext();
		}
		return true;
	}

	// Get the Tawjihi weight for a specific major
	public double gettawjihiWeight(String nameMajor) {
		NodeMajor current = front;
		while (current != null) {
			Major major = (Major) current.getElement();
			if (major.getName().equalsIgnoreCase(nameMajor)) {
				return major.getTawjihiWeight();
			}
			current = current.getNext();
		}
		return -1;
	}

	// Get the Placement Test weight for a specific major
	public double getplacementTestWeight(String nameMajor) {
		NodeMajor current = front;
		while (current != null) {
			Major major = (Major) current.getElement();
			if (major.getName().equalsIgnoreCase(nameMajor)) {
				return major.getPlacementTestWeight();
			}
			current = current.getNext();
		}
		return -1;
	}

	// Get an array of majors with acceptance grade less than or equal to a
	// specified grade
	public String[] getArrayMajors(double grade) {
		// First pass to count the number of matching majors
		int count = 0;
		NodeMajor current = front;
		while (current != null) {
			Major major = (Major) current.getElement();
			if (major.getAcceptanceGrade() <= grade) {
				count++;
			}
			current = current.getNext();
		}

		// Create array with the correct size
		String[] majorsList = new String[count];

		// Second pass to add the matching majors to the array
		current = front;
		int index = 0;
		while (current != null) {
			Major major = (Major) current.getElement();
			if (major.getAcceptanceGrade() <= grade) {
				majorsList[index] = major.getName();
				index++;
			}

			current = current.getNext();
		}
		return majorsList;
	}

	// Get an array of all major names
	public String[] getAllArrayMajors() {
		ObservableList<String> majorsList = FXCollections.observableArrayList();
		NodeMajor current = front;
		while (current != null) {
			Major major = (Major) current.getElement();
			majorsList.add(major.getName());
			current = current.getNext();
		}
		return majorsList.toArray(new String[0]);
	}
}
