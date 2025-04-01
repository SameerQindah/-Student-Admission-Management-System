package application;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

public class Raed {
	private int totalAccepted = 0;
	private int totalRejected = 0;

	public Raed() {
		super();
	}

	public int getTotalAccepted() {
		return totalAccepted;
	}

	public void setTotalAccepted(int totalAccepted) {
		this.totalAccepted = totalAccepted;
	}

	public int getTotalRejected() {
		return totalRejected;
	}

	public void setTotalRejected(int totalRejected) {
		this.totalRejected = totalRejected;
	}

	// this method for import data from my file and save in my data TableView
	public void readMajor(File file, ObservableList<Major> majors, MajorList Listmajors) {
		try (Scanner in = new Scanner(file)) {
			while (in.hasNextLine()) {
				String line = in.nextLine();

				if (line.isEmpty())
					continue; // Skip empty lines

				String[] splitLine = line.split(":");

				if (splitLine.length < 4)
					continue;

				try {
					String name = splitLine[0];
					double acceptanceGrade = Double.parseDouble(splitLine[1]);
					double tawjihiWeight = Double.parseDouble(splitLine[2]);
					double placementTestWeight = Double.parseDouble(splitLine[3]);
					if (acceptanceGrade < 50 && acceptanceGrade > 100) {
						// showAlert("Error", "The Acceptance Grade is less then 50");
						continue;
					}
					if ((placementTestWeight + tawjihiWeight) != 1) {
						// showAlert("Error", "The placement Test Weight Grade + tawjihi Weight dont
						// equal 1");
						continue;
					}

					if (name.matches(".*\\d.*")) {
						// showAlert("Error", "Please enter valid String for Name (cannot be Nmbers).");
						continue;
					}
					if (Listmajors.search(name) != null) {
						// showAlert("Error", "The Name can not be frequent ");
						continue;
					}

					// Create a new Major object
					Major major = new Major(name, acceptanceGrade, tawjihiWeight, placementTestWeight);

					// Insert the new Major into Listmajors in sorted order
					Listmajors.insertSortMajor(major);

				} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
					continue; // Handle parsing errors
				}
			}

			// Update the observable list once after all entries have been added

			majors.setAll(Listmajors.toObservableList());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// this method for import data from my file and save in my data TableView
	public void readStudent(File file, ObservableList<Student> students, StudentList stusentlist,
			MajorList Listmajors) {
		try (Scanner in = new Scanner(file)) {
			while (in.hasNextLine()) {
				String line = in.nextLine(); // Read the next line

				if (line.isEmpty())
					continue; // Skip empty lines
				String[] splitLine = line.split(":");

				if (splitLine.length < 5)
					continue; // Ensure there are enough columns

				try {
					int StudentId = Integer.parseInt(splitLine[0]);
					String name = splitLine[1];
					double tawjihiGrade = Double.parseDouble(splitLine[2]);
					double PlacementGrade = Double.parseDouble(splitLine[3]);
					Object ChosenMajor = splitLine[4];

					double admissionMark = (tawjihiGrade * Listmajors.gettawjihiWeight((String) ChosenMajor))
							+ (PlacementGrade * Listmajors.getplacementTestWeight((String) ChosenMajor));

					if (tawjihiGrade < 50 && tawjihiGrade > 100) {
						// showAlert("Error", "The tawjihi Grade is less then 50");
						setTotalRejected(getTotalRejected() + 1);
						continue;
					}
					if (PlacementGrade < 50 && PlacementGrade > 100) {
						// showAlert("Error", "The placement Test Grade is less then 50");
						setTotalRejected(getTotalRejected() + 1);
						continue;
					}

					if (Listmajors.getName(((String) ChosenMajor))) {
						// showAlert("Error", "This major not found in the system ");
						setTotalRejected(getTotalRejected() + 1);
						continue;
					}
					if (stusentlist.search(StudentId) != null) {
						// showAlert("Error", "The ID can not be frequent ");
						setTotalRejected(getTotalRejected() + 1);
						continue;
					}

					if (name.matches(".*\\d.*") || ((String) ChosenMajor).matches(".*\\d.*")) {
						// showAlert("Error", "Please enter valid String for Name and Major (cannot be
						// Nmbers).");
						setTotalRejected(getTotalRejected() + 1);
						continue;
					}
					if (Listmajors.getAcceptanceGrade((String) ChosenMajor) >= admissionMark) {
						// showAlert("Error", "The Admission Mark is less than AcceptanceGrade.");
						setTotalRejected(getTotalRejected() + 1);
						continue;
					}

					// Create a new Node Student object
					Student student = new Student(StudentId, name, tawjihiGrade, PlacementGrade, ChosenMajor);

					// Add the Student to the list for the TableView
					stusentlist.insertSortStudent(student);
					setTotalAccepted(getTotalAccepted() + 1);

				} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
					continue; // Handle errors
				}
			}

			students.setAll(stusentlist.toObservableList());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

}
