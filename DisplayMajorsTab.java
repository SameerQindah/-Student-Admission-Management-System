package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DisplayMajorsTab {
	// Navigation buttons for next and previous majors
	private Button nextButton;
	private Button PreviousButton;

	// Fields to display major information
	private TextField nameField;
	private TextField gradeField;
	private TextField tawjihiField;
	private TextField placementField;

	// Track the current major index
	private int current = 0;

	// Lists to hold all students and filtered students for the current major
	private ObservableList<Student> students = FXCollections.observableArrayList();
	private ObservableList<Student> filteredStudents = FXCollections.observableArrayList();

	// Table to display students
	private TableView<Student> studentTable;

	// Constructor
	public DisplayMajorsTab() {
		super();
	}

	// Method to create the main layout for displaying majors
	public HBox displayMajors() {
		VBox majorsVbox = new VBox(10);

		// Labels and fields for major details
		Label nameLabel = new Label("Major Name:");
		nameField = new TextField();
		nameField.setEditable(false); // Make it read-only

		Label acceptanceLabel = new Label("Acceptance Grade:");
		gradeField = new TextField();
		gradeField.setEditable(false);

		Label tawjihiLabel = new Label("Tawjihi Weight:");
		tawjihiField = new TextField();
		tawjihiField.setEditable(false);

		Label placementLabel = new Label("Placement Test Weight:");
		placementField = new TextField();
		placementField.setEditable(false);

		// Next and previous buttons
		nextButton = new Button("Next");
		PreviousButton = new Button("Previous");

		// Layout for major details
		GridPane Gridmajor = new GridPane();
		Gridmajor.add(nameLabel, 0, 0);
		Gridmajor.add(nameField, 1, 0);
		Gridmajor.add(acceptanceLabel, 0, 1);
		Gridmajor.add(gradeField, 1, 1);
		Gridmajor.add(tawjihiLabel, 0, 2);
		Gridmajor.add(tawjihiField, 1, 2);
		Gridmajor.add(placementLabel, 0, 3);
		Gridmajor.add(placementField, 1, 3);
		Gridmajor.setAlignment(Pos.CENTER);
		Gridmajor.setHgap(10);
		Gridmajor.setVgap(10);

		// Layout for navigation buttons
		HBox navigationButtons = new HBox(30, PreviousButton, nextButton);
		navigationButtons.setAlignment(Pos.CENTER);

		majorsVbox.getChildren().addAll(Gridmajor, navigationButtons);

		// Student table setup
		studentTable = new TableView<>(filteredStudents);
		createStudentTable();

		// Main layout that combines major info and student table
		HBox displaymajor = new HBox(30);
		displaymajor.getChildren().addAll(majorsVbox, studentTable);

		return displaymajor;
	}

	// Method to load and display the list of majors
	public void openFile(ObservableList<Major> majors) {
		if (majors.isEmpty()) {
			// If no majors, clear fields and show message
			nameField.setText("No majors available");
			gradeField.clear();
			tawjihiField.clear();
			placementField.clear();
			return;
		}

		// Display the first major and filter students by that major
		displayMajor(majors.get(current));
		filterStudentsByMajor();

		// Event handling for navigating to the next major
		nextButton.setOnAction(e -> {
			if (current < majors.size() - 1) {
				current++;
				displayMajor(majors.get(current));
				filterStudentsByMajor();
			}
		});

		// Event handling for navigating to the previous major
		PreviousButton.setOnAction(e -> {
			if (current > 0) {
				current--;
				displayMajor(majors.get(current));
				filterStudentsByMajor();
			}
		});
	}

	// Method to display details of the given major
	private void displayMajor(Major major) {
		nameField.setText(major.getName());
		gradeField.setText(String.valueOf(major.getAcceptanceGrade()));
		tawjihiField.setText(String.valueOf(major.getTawjihiWeight()));
		placementField.setText(String.valueOf(major.getPlacementTestWeight()));
	}

	// Method to filter students who belong to the current major
	private void filterStudentsByMajor() {
		String currentMajorName = nameField.getText();
		filteredStudents.clear();

		// Only add students whose major matches the current major name
		for (Student student : students) {
			if (student.getMajor().equals(currentMajorName)) {
				filteredStudents.add(student);
			}
		}
	}

	// Method to set up the columns in the student table
	private void createStudentTable() {
		// Column for student ID
		TableColumn<Student, String> StudentId = new TableColumn<>("ID");
		StudentId
				.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));

		// Column for student name
		TableColumn<Student, String> name = new TableColumn<>("Name");
		name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

		// Column for Tawjihi Grade
		TableColumn<Student, String> tawjihiGrade = new TableColumn<>("Tawjihi Grade");
		tawjihiGrade.setCellValueFactory(
				cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getTawjihiGrade())));
		tawjihiGrade.setMinWidth(150);

		// Column for Placement Grade
		TableColumn<Student, String> PlacementGrade = new TableColumn<>("Placement Grade");
		PlacementGrade.setCellValueFactory(
				cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPlacementTestGrade())));
		PlacementGrade.setMinWidth(150);

		studentTable.getColumns().addAll(StudentId, name, tawjihiGrade, PlacementGrade);
	}

	// Method to set the full list of students, used for filtering by major
	public void setStudents(ObservableList<Student> allStudents) {
		this.students = allStudents;
	}
}
