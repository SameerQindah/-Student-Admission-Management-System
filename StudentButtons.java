package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class StudentButtons {
	// VBox layout for student-related forms
	private VBox VboxStudent = new VBox(10);

	// Sets up the student buttons and defines their actions
	public void setupButtons(BorderPane root, ObservableList<Student> students, StudentList studentList,
			MajorList majorList) {

		// Create buttons for various actions
		Button add = new Button("Add Student");
		Button delete = new Button("Delete Student");
		Button update = new Button("Update Student");
		Button search = new Button("Search Student");
		Button Save = new Button("Save Student");

		// Set up actions for each button
		add.setOnAction(e -> {
			showAddStudentForm(students, studentList, majorList);
		});
		delete.setOnAction(e -> {
			showDeleteStudentForm(students, studentList);
		});
		update.setOnAction(e -> {
			showUpdateStudentForm(students, studentList);
		});
		search.setOnAction(e -> {
			showSearchStudentForm(studentList);
		});
		Save.setOnAction(e -> {
			showSaveStudent(students);
		});

		// Arrange buttons horizontally and set them at the bottom of the main layout
		HBox buttonBox = new HBox(10, add, delete, update, search, Save);
		buttonBox.setPadding(new Insets(10));

		root.setBottom(buttonBox);
		root.setCenter(VboxStudent);
	}

	// Shows a dialog to save student data to a file
	private void showSaveStudent(ObservableList<Student> students) {
		// Use FileChooser to let the user select where to save the file
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Student Data");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

		// Show the save dialog
		File saveFile = fileChooser.showSaveDialog(VboxStudent.getScene().getWindow());

		// If the user chose a file, write student data to it
		if (saveFile != null) {
			try (PrintWriter writer = new PrintWriter(new FileWriter(saveFile))) {
				// Write header for the file
				writer.println("Student ID: Name: Tawjihi Grade: Placement Test Grade: Major");

				// Write each student's details to the file
				for (int i = 0; i < students.size(); i++) {
					Student student = students.get(i);
					String studentRecord = String.format("%d: %s: %.2f: %.2f: %s", student.getId(), student.getName(),
							student.getTawjihiGrade(), student.getPlacementTestGrade(), student.getMajor());
					writer.println(studentRecord);
				}

				// Show a message confirming the save was successful
				showAlert("Save Successful", "Student data successfully saved to " + saveFile.getName());

			} catch (IOException e) {
				e.printStackTrace();
				// Show a message if saving fails
				showAlert("Save Failed", "Failed to save student data");
			}
		}
	}

	// Displays the form to add a new student
	private void showAddStudentForm(ObservableList<Student> students, StudentList studentList, MajorList majorList) {
		VboxStudent.getChildren().clear();

		Label addLabel = new Label("Add Student:");
		TextField idField = new TextField();
		idField.setPromptText("Student ID");
		TextField nameField = new TextField();
		nameField.setPromptText("Name");
		TextField tawjihiGradeField = new TextField();
		tawjihiGradeField.setPromptText("Tawjihi Grade");
		TextField placementTestField = new TextField();
		placementTestField.setPromptText("Placement Test Grade");
		TextField majorField = new TextField();
		majorField.setPromptText("Chosen Major");

		Button saveButton = new Button("Save");
		saveButton.setOnAction(event -> addStudent(idField, nameField, tawjihiGradeField, placementTestField,
				majorField, students, studentList, majorList));

		// Arrange fields in a grid layout
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.add(addLabel, 0, 0);
		gridPane.add(idField, 1, 0);
		gridPane.add(nameField, 2, 0);
		gridPane.add(tawjihiGradeField, 3, 0);
		gridPane.add(placementTestField, 4, 0);
		gridPane.add(majorField, 5, 0);
		gridPane.add(saveButton, 6, 0);

		VboxStudent.getChildren().add(gridPane);
	}

	// Displays the form to delete a student
	private void showDeleteStudentForm(ObservableList<Student> students, StudentList studentList) {
		VboxStudent.getChildren().clear();

		Label deleteLabel = new Label("Delete Student:");
		TextField idField = new TextField();
		idField.setPromptText("Student ID");

		Button deleteButton = new Button("Delete");
		deleteButton.setOnAction(event -> deleteStudent(idField, students, studentList));

		// Arrange fields in a grid layout
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.add(deleteLabel, 0, 0);
		gridPane.add(idField, 1, 0);
		gridPane.add(deleteButton, 2, 0);

		VboxStudent.getChildren().add(gridPane);
	}

	// Displays the form to update a student's details
	private void showUpdateStudentForm(ObservableList<Student> students, StudentList studentList) {
		VboxStudent.getChildren().clear();

		Label updateLabel = new Label("Update Student:");
		TextField idField = new TextField();
		idField.setPromptText("Student ID");
		TextField nameField = new TextField();
		nameField.setPromptText("New Name");
		TextField tawjihiGradeField = new TextField();
		tawjihiGradeField.setPromptText("New Tawjihi Grade");
		TextField placementTestField = new TextField();
		placementTestField.setPromptText("New Placement Test Grade");

		Button updateButton = new Button("Update");
		updateButton.setOnAction(event -> updateStudent(idField, nameField, tawjihiGradeField, placementTestField,
				students, studentList));

		// Arrange fields in a grid layout
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.add(updateLabel, 0, 0);
		gridPane.add(idField, 1, 0);
		gridPane.add(nameField, 2, 0);
		gridPane.add(tawjihiGradeField, 3, 0);
		gridPane.add(placementTestField, 4, 0);
		gridPane.add(updateButton, 5, 0);

		VboxStudent.getChildren().add(gridPane);
	}

	// Displays the form to search for a student
	private void showSearchStudentForm(StudentList studentList) {
		VboxStudent.getChildren().clear();

		Label searchLabel = new Label("Search Student:");
		TextField idField = new TextField();
		idField.setPromptText("Student ID");

		Button searchButton = new Button("Search");
		searchButton.setOnAction(event -> searchStudent(idField, studentList));

		// Arrange fields in a grid layout
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.add(searchLabel, 0, 0);
		gridPane.add(idField, 1, 0);
		gridPane.add(searchButton, 2, 0);

		VboxStudent.getChildren().add(gridPane);
	}

	// Adds a new student with details from the form
	private void addStudent(TextField idField, TextField nameField, TextField tawjihiField, TextField placementField,
			TextField majorField, ObservableList<Student> students, StudentList studentList, MajorList majorList) {
		try {
			int studentId = Integer.parseInt(idField.getText().trim());
			String name = nameField.getText().trim();
			double tawjihiGrade = Double.parseDouble(tawjihiField.getText().trim());
			double placementTestGrade = Double.parseDouble(placementField.getText().trim());
			String major = majorField.getText().trim();

			double admissionMark = (tawjihiGrade * majorList.gettawjihiWeight(major))
					+ (placementTestGrade * majorList.getplacementTestWeight(major));

			// Validate inputs
			if (tawjihiGrade < 50 || tawjihiGrade > 100) {
				showAlert("Error", "The Tawjihi Grade must be between 50 and 100.");
				return;
			}
			if (placementTestGrade < 50 || placementTestGrade > 100) {
				showAlert("Error", "The Placement Test Grade must be between 50 and 100.");
				return;
			}

			if (studentList.search(studentId) != null) {
				showAlert("Error", "The ID must be unique.");
				return;
			}
			if (name.matches(".*\\d.*") || major.matches(".*\\d.*")) {
				showAlert("Error", "Name and Major cannot contain numbers.");
				return;
			}
			if (name.isEmpty() || major.isEmpty()) {
				showAlert("Error", "Name and Major fields cannot be empty.");
				return;
			}
			if (majorList.getName(((String) major))) {
				showAlert("Error", "This major not found in the system ");
				return;
			}
			if (majorList.getAcceptanceGrade(major) >= admissionMark) {
				showAlert("Error", "The Admission Mark is less than AcceptanceGrade.");
			}

			// Create and add a new student
			Student student = new Student(studentId, name, tawjihiGrade, placementTestGrade, major);
			studentList.insertSortStudent(student);
			students.setAll(studentList.toObservableList());

			// Clear input fields after successful addition
			idField.clear();
			nameField.clear();
			tawjihiField.clear();
			placementField.clear();
			majorField.clear();

			showAlert("Success", "Student added successfully.");
		} catch (NumberFormatException e) {
			showAlert("Error", "Please enter valid numbers for ID, Tawjihi Grade, and Placement Test Grade.");
		}
	}

	// Deletes a student by ID
	private void deleteStudent(TextField idField, ObservableList<Student> students, StudentList studentList) {
		try {
			int studentId = Integer.parseInt(idField.getText().trim());
			Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION,
					"Are you sure you want to delete this student?", ButtonType.YES, ButtonType.NO);
			confirmAlert.setTitle("Confirm Deletion");
			confirmAlert.showAndWait().ifPresent(response -> {
				if (response == ButtonType.YES && studentList.remove(studentId)) {
					students.setAll(studentList.toObservableList());
					showAlert("Success", "Student with ID " + studentId + " has been deleted.");
				}
			});
		} catch (NumberFormatException e) {
			showAlert("Error", "Please enter a valid Student ID.");
		}
	}

	// Updates a student's details
	private void updateStudent(TextField idField, TextField nameField, TextField tawjihiField, TextField placementField,
			ObservableList<Student> students, StudentList studentList) {
		try {
			int studentId = Integer.parseInt(idField.getText().trim());
			String name = nameField.getText().trim();
			double tawjihiGrade = Double.parseDouble(tawjihiField.getText().trim());
			double placementTestGrade = Double.parseDouble(placementField.getText().trim());

			// Validate inputs
			if (name.matches(".*\\d.*")) {
				showAlert("Error", "Name cannot contain numbers.");
				return;
			}
			if (tawjihiGrade < 50 || tawjihiGrade > 100) {
				showAlert("Error", "The Tawjihi Grade must be between 50 and 100.");
				return;
			}
			if (placementTestGrade < 50 || placementTestGrade > 100) {
				showAlert("Error", "The Placement Test Grade must be between 50 and 100.");
				return;
			}
			if (name.isEmpty()) {
				showAlert("Error", "Name field cannot be empty.");
				return;
			}
			if (studentList.update(studentId, name, tawjihiGrade, placementTestGrade)) {
				students.setAll(studentList.toObservableList());
				showAlert("Success", "Student with ID " + studentId + " has been updated.");
			} else {
				showAlert("Error", "Update failed. Please check the inputs and try again.");
			}
		} catch (NumberFormatException e) {
			showAlert("Error", "Please enter valid numbers for ID, Tawjihi Grade, and Placement Test.");
		}
	}

	// Searches for a student by ID
	private void searchStudent(TextField idField, StudentList studentList) {
		try {
			int studentId = Integer.parseInt(idField.getText().trim());
			Student student = studentList.search(studentId);

			// Show student's details if found, else show an error message
			if (student != null) {
				showAlert("Student Found",
						"ID: " + student.getId() + "\nName: " + student.getName() + "\nTawjihi Grade: "
								+ student.getTawjihiGrade() + "\nPlacement Test: " + student.getPlacementTestGrade()
								+ "\nMajor: " + student.getMajor());
			} else {
				showAlert("Error", "Student with ID " + studentId + " not found.");
			}
		} catch (NumberFormatException e) {
			showAlert("Error", "Please enter a valid Student ID.");
		}
	}

	// Displays an alert box with a message
	private void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
