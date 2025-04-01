package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class MajorButtons {

	private VBox dynamicArea = new VBox(10); // Area for dynamic forms (like add, delete, update)

	// Method to set up buttons for managing majors
	public void setupButtons(BorderPane root, ObservableList<Major> majors, MajorList majorList) {

		// Buttons for adding, deleting, updating, searching, and saving majors
		Button add = new Button("Add Major");
		Button delete = new Button("Delete Major");
		Button update = new Button("Update Major");
		Button search = new Button("Search Major");
		Button Save = new Button("Save Major");

		// Set actions for each button
		add.setOnAction(e -> showAddMajorForm(majors, majorList));
		delete.setOnAction(e -> showDeleteMajorForm(majors, majorList));
		update.setOnAction(e -> showUpdateMajorForm(majors, majorList));
		search.setOnAction(e -> showSearchMajorForm(majorList));
		Save.setOnAction(e -> showSaveMajor(majors));

		// Arrange buttons in a horizontal box
		HBox buttonBox = new HBox(10, add, delete, update, search, Save);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.setPadding(new Insets(10));

		root.setBottom(buttonBox); // Add buttons to the bottom of the layout
		root.setCenter(dynamicArea); // Set the dynamic area in the center
	}

	// Method to show a file chooser and save major data to a file
	private void showSaveMajor(ObservableList<Major> majors) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Major Data");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

		// Show save dialog to choose file location
		File saveFile = fileChooser.showSaveDialog(dynamicArea.getScene().getWindow());

		if (saveFile != null) {
			try (PrintWriter writer = new PrintWriter(new FileWriter(saveFile))) {
				// Write header for file
				writer.println("Major Name:Acceptance Grade:Tawjihi Weight:Placement Test Weight");

				// Write each major's data
				for (int i = 0; i < majors.size(); i++) {
					Major major = majors.get(i);
					String majorRecord = String.format("%s:%.2f:%.2f:%.2f", major.getName(), major.getAcceptanceGrade(),
							major.getTawjihiWeight(), major.getPlacementTestWeight());
					writer.println(majorRecord);
				}

				// Show success message
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Save Successful");
				alert.setHeaderText(null);
				alert.setContentText("Major data successfully saved to " + saveFile.getName());
				alert.showAndWait();

			} catch (IOException e) {
				e.printStackTrace();
				// Show error message if saving fails
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Save Failed");
				alert.setHeaderText(null);
				alert.setContentText("Failed to save major data");
				alert.showAndWait();
			}
		}
	}

	// Method to display the form to add a new major
	private void showAddMajorForm(ObservableList<Major> majors, MajorList majorList) {
		dynamicArea.getChildren().clear();

		// Fields to enter major details
		Label addLabel = new Label("Add Major:");
		TextField majorNameField = new TextField();
		majorNameField.setPromptText("Major Name");
		TextField acceptanceGradeField = new TextField();
		acceptanceGradeField.setPromptText("Acceptance Grade");
		TextField tawjihiWeightField = new TextField();
		tawjihiWeightField.setPromptText("Tawjihi Weight");
		TextField placementTestWeightField = new TextField();
		placementTestWeightField.setPromptText("Placement Test Weight");

		// Button to save the new major
		Button saveButton = new Button("Save");
		saveButton.setOnAction(event -> addMajor(majorNameField, acceptanceGradeField, tawjihiWeightField,
				placementTestWeightField, majors, majorList));

		// Layout for the form fields and button
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.add(addLabel, 0, 0);
		gridPane.add(majorNameField, 1, 0);
		gridPane.add(acceptanceGradeField, 2, 0);
		gridPane.add(tawjihiWeightField, 3, 0);
		gridPane.add(placementTestWeightField, 4, 0);
		gridPane.add(saveButton, 5, 0);

		dynamicArea.getChildren().add(gridPane);
	}

	// Method to display the form to delete a major
	private void showDeleteMajorForm(ObservableList<Major> majors, MajorList majorList) {
		dynamicArea.getChildren().clear();

		// Field to enter the name of the major to delete
		Label deleteLabel = new Label("Delete Major:");
		TextField majorNameField = new TextField();
		majorNameField.setPromptText("Major Name");

		// Button to delete the major
		Button deleteButton = new Button("Delete");
		deleteButton.setOnAction(event -> deleteMajor(majorNameField, majors, majorList));

		// Layout for the form field and button
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.add(deleteLabel, 0, 0);
		gridPane.add(majorNameField, 1, 0);
		gridPane.add(deleteButton, 2, 0);

		dynamicArea.getChildren().add(gridPane);
	}

	// Method to display the form to update a major
	private void showUpdateMajorForm(ObservableList<Major> majors, MajorList majorList) {
		dynamicArea.getChildren().clear();

		// Fields to enter current and new major details
		Label updateLabel = new Label("Update Major:");
		TextField currentNameField = new TextField();
		currentNameField.setPromptText("Current Major Name");
		TextField newNameField = new TextField();
		newNameField.setPromptText("New Major Name");
		TextField acceptanceGradeField = new TextField();
		acceptanceGradeField.setPromptText("New Acceptance Grade");
		TextField tawjihiWeightField = new TextField();
		tawjihiWeightField.setPromptText("New Tawjihi Weight");
		TextField placementTestWeightField = new TextField();
		placementTestWeightField.setPromptText("New Placement Test Weight");

		// Button to update the major
		Button updateButton = new Button("Update");
		updateButton.setOnAction(event -> updateMajor(currentNameField, newNameField, acceptanceGradeField,
				tawjihiWeightField, placementTestWeightField, majors, majorList));

		// Layout for the form fields and button
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.add(updateLabel, 0, 0);
		gridPane.add(currentNameField, 1, 0);
		gridPane.add(newNameField, 2, 0);
		gridPane.add(acceptanceGradeField, 3, 0);
		gridPane.add(tawjihiWeightField, 4, 0);
		gridPane.add(placementTestWeightField, 5, 0);
		gridPane.add(updateButton, 6, 0);

		dynamicArea.getChildren().add(gridPane);
	}

	// Method to display the form to search for a major
	private void showSearchMajorForm(MajorList majorList) {
		dynamicArea.getChildren().clear();

		// Field to enter the name of the major to search for
		Label searchLabel = new Label("Search Major:");
		TextField majorNameField = new TextField();
		majorNameField.setPromptText("Major Name");

		// Button to search for the major
		Button searchButton = new Button("Search");
		searchButton.setOnAction(event -> searchMajor(majorNameField, majorList));

		// Layout for the form field and button
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.add(searchLabel, 0, 0);
		gridPane.add(majorNameField, 1, 0);
		gridPane.add(searchButton, 2, 0);

		dynamicArea.getChildren().add(gridPane);
	}

	// Method to add a new major
	private void addMajor(TextField majorNameField, TextField acceptanceGradeField, TextField tawjihiWeightField,
			TextField placementTestWeightField, ObservableList<Major> majors, MajorList majorList) {
		try {
			// Get input values
			String majorName = majorNameField.getText().trim();
			double acceptanceGrade = Double.parseDouble(acceptanceGradeField.getText().trim());
			double tawjihiWeight = Double.parseDouble(tawjihiWeightField.getText().trim());
			double placementTestWeight = Double.parseDouble(placementTestWeightField.getText().trim());

			// Validate values
			if (acceptanceGrade < 50 || acceptanceGrade > 100) {
				showAlert("Error", "Acceptance Grade must be between 50 and 100.");
				return;
			}
			if (tawjihiWeight + placementTestWeight != 1.0) {
				showAlert("Error", "The Tawjihi Weight and Placement Test Weight must sum to 1.");
				return;
			}
			if (majorName.isEmpty() || majorName.matches(".*\\d.*")) {
				showAlert("Error", "Major Name cannot contain numbers and must not be empty.");
				return;
			}
			if (majorList.search(majorName) != null) {
				showAlert("Error", "A major with this name already exists.");
				return;
			}

			// Create and add new major
			Major newMajor = new Major(majorName, acceptanceGrade, tawjihiWeight, placementTestWeight);
			majorList.insertSortMajor(newMajor);
			majors.setAll(majorList.toObservableList());

			// Clear fields and show success message
			majorNameField.clear();
			acceptanceGradeField.clear();
			tawjihiWeightField.clear();
			placementTestWeightField.clear();
			showAlert("Success", "Major added successfully!");

		} catch (NumberFormatException e) {
			showAlert("Error", "Please enter valid numbers for the acceptance grade and weight fields.");
		}
	}

	// Method to delete a major
	private void deleteMajor(TextField majorNameField, ObservableList<Major> majors, MajorList majorList) {
		String majorName = majorNameField.getText().trim();
		if (majorName.isEmpty()) {
			showAlert("Error", "Please enter the name of the major to delete.");
			return;
		}

		boolean removed = majorList.remove(majorName);
		if (removed) {
			majors.setAll(majorList.toObservableList());
			showAlert("Success", "Major deleted successfully!");
		} else {
			showAlert("Error", "Major not found.");
		}

		majorNameField.clear(); // Clear the field
	}

	// Method to update a major
	private void updateMajor(TextField currentNameField, TextField newNameField, TextField acceptanceGradeField,
			TextField tawjihiWeightField, TextField placementTestWeightField, ObservableList<Major> majors,
			MajorList majorList) {
		try {
			String currentName = currentNameField.getText().trim();
			String newName = newNameField.getText().trim();
			double acceptanceGrade = Double.parseDouble(acceptanceGradeField.getText().trim());
			double tawjihiWeight = Double.parseDouble(tawjihiWeightField.getText().trim());
			double placementTestWeight = Double.parseDouble(placementTestWeightField.getText().trim());

			// Validate values
			if (acceptanceGrade < 50 || acceptanceGrade > 100) {
				showAlert("Error", "Acceptance Grade must be between 50 and 100.");
				return;
			}
			if (tawjihiWeight + placementTestWeight != 1.0) {
				showAlert("Error", "The Tawjihi Weight and Placement Test Weight must sum to 1.");
				return;
			}
			if (newName.isEmpty() || newName.matches(".*\\d.*")) {
				showAlert("Error", "New Major Name cannot contain numbers and must not be empty.");
				return;
			}
			if (!currentName.equals(newName) && majorList.search(newName) != null) {
				showAlert("Error", "A major with the new name already exists.");
				return;
			}

			// Update major and refresh list
			boolean updated = majorList.update(currentName, newName, acceptanceGrade, tawjihiWeight,
					placementTestWeight);
			if (updated) {
				majors.setAll(majorList.toObservableList());
				showAlert("Success", "Major updated successfully!");
			} else {
				showAlert("Error", "Update failed. Major not found.");
			}

			// Clear fields
			currentNameField.clear();
			newNameField.clear();
			acceptanceGradeField.clear();
			tawjihiWeightField.clear();
			placementTestWeightField.clear();

		} catch (NumberFormatException e) {
			showAlert("Error", "Please enter valid numbers for the acceptance grade and weight fields.");
		}
	}

	// Method to search for a major
	private void searchMajor(TextField majorNameField, MajorList majorList) {
		String majorName = majorNameField.getText().trim();
		if (majorName.isEmpty()) {
			showAlert("Error", "Please enter the name of the major to search for.");
			return;
		}

		Major foundMajor = majorList.search(majorName);
		if (foundMajor != null) {
			showAlert("Major Found",
					"Name: " + foundMajor.getName() + "\nAcceptance Grade: " + foundMajor.getAcceptanceGrade()
							+ "\nTawjihi Weight: " + foundMajor.getTawjihiWeight() + "\nPlacement Test Weight: "
							+ foundMajor.getPlacementTestWeight());
		} else {
			showAlert("Error", "Major not found.");
		}

		majorNameField.clear(); // Clear the field
	}

	// Method to show alert messages
	private void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
