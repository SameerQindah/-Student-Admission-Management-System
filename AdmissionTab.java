package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.beans.property.SimpleStringProperty;

public class AdmissionTab {
	private ComboBox<String> major1ComboBox = new ComboBox<>();
	private ComboBox<String> major2ComboBox = new ComboBox<>();
	private double admissionMark;
	private int totalAccepted ;
	private int totalRejected ;
	private int studentId;
	private String name;
	private double tawjihiGrade;
	private double placementTestGrade;
	
	
	
	private TableView<RejectedStudent> rejectionTable = new TableView<>();
	private ObservableList<RejectedStudent> rejectedStudentsList = FXCollections.observableArrayList();

	public AdmissionTab(Raed read ) {
		super();
		totalAccepted = read.getTotalAccepted();
        totalRejected = read.getTotalRejected();
		
		setupRejectionTable();
	}

	// Method to populate alternativeMajorComboBox with all majors from MajorList
	public void getMajor1ComboBox(MajorList majorlist) {
		String[] alternativeMajors = majorlist.getAllArrayMajors();
		ObservableList<String> alternativeMajorList = FXCollections.observableArrayList(alternativeMajors);
		major1ComboBox.setItems(alternativeMajorList);
	}

	public void getmajor2ComboBox(MajorList majorlist, double admissionMark) {
		String[] alternativeMajors = majorlist.getArrayMajors(admissionMark);
		ObservableList<String> alternativeMajorList = FXCollections.observableArrayList(alternativeMajors);
		major2ComboBox.setItems(alternativeMajorList);
	}

	public VBox createAdmissionPane(MajorList majorlist, StudentList studentlist, ObservableList<Student> students) {
		VBox admissionPane = new VBox(10);
		admissionPane.setPadding(new Insets(10));

		TextField IdField = new TextField();
		IdField.setPromptText("ID Student");

		TextField NameField = new TextField();
		NameField.setPromptText("Name Student");

		TextField tawjihiGradeField = new TextField();
		tawjihiGradeField.setPromptText("Tawjihi Grade");

		TextField placementTestGradeField = new TextField();
		placementTestGradeField.setPromptText("Placement Test Grade");

		Label resultLabel = new Label();

		// Populate the alternative major combo box initially
		getMajor1ComboBox(majorlist);

		Button calculateAdmissionButton = new Button("Calculate Admission Mark");
		calculateAdmissionButton.setOnAction(e -> {
			try {
				studentId = Integer.parseInt(IdField.getText().trim());
				name = NameField.getText().trim();
				tawjihiGrade = Double.parseDouble(tawjihiGradeField.getText());
				placementTestGrade = Double.parseDouble(placementTestGradeField.getText());
				String chosenMajor = major1ComboBox.getValue();

				if (placementTestGrade < 50 || placementTestGrade > 100) {
					showAlert("Error", "placement Test Grade must be between 50 and 100.");
					return;
				}
				if (tawjihiGrade < 50 || tawjihiGrade > 100) {
					showAlert("Error", "tawjihi Grade must be between 50 and 100.");
					return;
				}
				if (chosenMajor == null) {
					showAlert("Selection Error", "Please select a major.");
					return;
				}
				if (studentlist.search(studentId) != null) {
					showAlert("Error", "The ID must be unique.");
					return;
				}
				if (name.matches(".*\\d.*")) {
					showAlert("Error", "Name cannot contain numbers.");
					return;
				}
				if (name.isEmpty()) {
					showAlert("Error", "Name field cannot be empty.");
					return;
				}

				// Calculate the Admission Mark
				admissionMark = (tawjihiGrade * majorlist.gettawjihiWeight(chosenMajor))
						+ (placementTestGrade * majorlist.getplacementTestWeight(chosenMajor));

				// Check if the student meets the acceptance grade
				if (admissionMark >= majorlist.getAcceptanceGrade(chosenMajor)) {
					resultLabel.setText("Accepted with Admission Mark: " + String.format("%.2f", admissionMark));
					Student student = new Student(studentId, name, tawjihiGrade, placementTestGrade, chosenMajor);
					studentlist.insertSortStudent(student);
					students.setAll(studentlist.toObservableList());
					totalAccepted++;
				} else {
					resultLabel.setText("Not Accepted (Admission Mark: " + String.format("%.2f", admissionMark) + ")");
					totalRejected++;
					getmajor2ComboBox(majorlist, admissionMark);

					// Add to rejected students list
					rejectedStudentsList
							.add(new RejectedStudent(name, "Admission mark below required grade for " + chosenMajor));
				}

			} catch (NumberFormatException ex) {
				showAlert("Error", "Please enter valid numbers for all fields.");
			}

			tawjihiGradeField.clear();
			placementTestGradeField.clear();
			IdField.clear();
			NameField.clear();
			major1ComboBox.setValue(null);

		});

		Label majorSuggestionLabel = new Label("Select a major based on admission marks:");
		Button confirmButton = new Button("Confirm Major");
		confirmButton.setOnAction(e -> {
			String selectedMajor = major2ComboBox.getValue();
			if (selectedMajor != null) {
				resultLabel.setText("Confirmed Major: " + selectedMajor);
				Student student = new Student(studentId, name, tawjihiGrade, placementTestGrade, selectedMajor);
				studentlist.insertSortStudent(student);
				students.setAll(studentlist.toObservableList());
				totalAccepted++;
			} else {
				showAlert("Selection Error", "Please select a major.");
			}
		});

		// Statistics and Reporting UI
		Label acceptanceRateLabel = new Label("Acceptance Rate: ");
		Label totalAcceptedLabel = new Label("Total Accepted: ");
		Label totalEvaluatedLabel = new Label("Total Evaluated: ");
		Button updateStatsButton = new Button("Update Statistics");

		updateStatsButton.setOnAction(e -> {
		
			int totalEvaluated = totalAccepted + totalRejected;
			double acceptanceRate = (totalAccepted / (double) totalEvaluated) * 100;
			acceptanceRateLabel.setText("Acceptance Rate: " + String.format("%.2f", acceptanceRate) + "%");
			totalAcceptedLabel.setText("Total Accepted: " + totalAccepted);
			totalEvaluatedLabel.setText("Total Evaluated: " + totalEvaluated);
		});

		VBox vbox = new VBox(15, majorSuggestionLabel, major2ComboBox, confirmButton, acceptanceRateLabel,
				totalAcceptedLabel, totalEvaluatedLabel, updateStatsButton, rejectionTable);

		HBox admissionForm = new HBox(10, IdField, NameField, tawjihiGradeField, placementTestGradeField,
				major1ComboBox, calculateAdmissionButton);
		admissionPane.getChildren().addAll(admissionForm, resultLabel, vbox);

		return admissionPane;
	}

	public void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private void setupRejectionTable() {
		// Student Name column
		TableColumn<RejectedStudent, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

		// Rejection Messages column
		TableColumn<RejectedStudent, String> rejectionMessagesColumn = new TableColumn<>("Rejection Messages");
		rejectionMessagesColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRejectionMessage()));
		rejectionMessagesColumn.setPrefWidth(400); // Set preferred width for rejection messages column

		rejectionTable.getColumns().addAll(nameColumn, rejectionMessagesColumn);
		rejectionTable.setItems(rejectedStudentsList);
		rejectionTable.setPrefHeight(150);
	}

	// Inner class to represent rejected students
	public static class RejectedStudent {
		private String name;
		private String rejectionMessage;

		public RejectedStudent(String name, String rejectionMessage) {
			this.name = name;
			this.rejectionMessage = rejectionMessage;
		}

		public String getName() {
			return name;
		}

		public String getRejectionMessage() {
			return rejectionMessage;
		}
	}
}
