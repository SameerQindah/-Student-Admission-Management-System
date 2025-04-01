 package application;

import java.io.File;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Main extends Application {

	// Declare buttons for student and major management
	private StudentButtons btstudent = new StudentButtons();
	private MajorButtons btMajor = new MajorButtons();
	private Raed read = new Raed(); // Declare file reading
	// Declare tabs
	private AdmissionTab AdmissionTab = new AdmissionTab(read);
	private DisplayMajorsTab displayM = new DisplayMajorsTab();

	// Lists to store students and majors
	private StudentList stusentlist = new StudentList();
	private MajorList majorlist = new MajorList(stusentlist);

	// Observable lists for JavaFX UI components to dynamically show students and
	// majors
	private ObservableList<Student> students = FXCollections.observableArrayList();
	private ObservableList<Major> majors = FXCollections.observableArrayList();
	private BorderPane root;

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Student Admission Management System");

		// Create menu bar with "File" menu for loading data files
		MenuBar bar = new MenuBar();
		Menu menuFile = new Menu("File");
		bar.getMenus().add(menuFile);

		// "Open File" option to load student and major data from a file
		MenuItem itemOpenFile = new MenuItem("Open File");
		menuFile.getItems().add(itemOpenFile);
		itemOpenFile.setOnAction(e -> {
			FileChooser chooser = new FileChooser();
			File file = chooser.showOpenDialog(primaryStage);
			if (file != null) {
				// Read data and populate tables and combo boxes
				read.readStudent(file, students, stusentlist, majorlist); // Populate students
				read.readMajor(file, majors, majorlist); // Populate majors
				displayM.openFile(majors); // Display majors
				displayM.setStudents(students); // Set students in display tab
				AdmissionTab.getMajor1ComboBox(majorlist); // Refresh major combo box
			}
		});

		// Create tabbed layout for different sections
		TabPane tabPane = new TabPane();
		Tab studentTab = new Tab("Student Management", createStudentPane());
		Tab majorTab = new Tab("Major Management", createMajorPane());
		Tab admissionTab = new Tab("Admission Criteria And Statistics",
				AdmissionTab.createAdmissionPane(majorlist, stusentlist, students));
		Tab displayMajors = new Tab("Display Majors", displayM.displayMajors());

		// Add tabs to the tab pane
		tabPane.getTabs().addAll(studentTab, majorTab, admissionTab, displayMajors);

		// Event listener to handle tab changes and update layout as necessary
		tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
			if (newTab == studentTab) {
				btstudent.setupButtons(root, students, stusentlist, majorlist); // Show student buttons
			} else {
				root.setBottom(null);
				root.setCenter(null); // Clear any additional UI components when switching tabs
			}
		});

		// Set up the main layout
		VBox vbox = new VBox();
		vbox.getChildren().addAll(bar, tabPane);

		root = new BorderPane();
		root.setTop(vbox);

		// Scene setup with specified width and height
		Scene scene = new Scene(root, 1000, 600);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	// Creates the Student Management Pane
	private VBox createStudentPane() {
		VBox studentPane = new VBox(10);
		studentPane.setPadding(new Insets(10));

		// Button to manage student data
		Button allButton = new Button("Student Data Management");
		allButton.setOnAction(e -> {
			btstudent.setupButtons(root, students, stusentlist, majorlist); // Set up buttons for student actions
		});

		// Table for displaying student information
		TableView<Student> studentTable = new TableView<>();
		studentTable.setItems(students);

		// Student ID column (converted to String for display)
		TableColumn<Student, String> StudentId = new TableColumn<>("ID");
		StudentId
				.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));

		// Student Name column
		TableColumn<Student, String> name = new TableColumn<>("Name");
		name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

		// Tawjihi Grade column
		TableColumn<Student, String> tawjihiGrade = new TableColumn<>("tawjihi Grade");
		tawjihiGrade.setCellValueFactory(
				cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getTawjihiGrade())));
		tawjihiGrade.setMinWidth(150);

		// Placement Grade column
		TableColumn<Student, String> PlacementGrade = new TableColumn<>("Placement Grade");
		PlacementGrade.setCellValueFactory(
				cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPlacementTestGrade())));
		PlacementGrade.setMinWidth(150);

		// Chosen Major column
		TableColumn<Student, String> ChosenMajor = new TableColumn<>("Chosen Major");
		ChosenMajor.setCellValueFactory(cellData -> new SimpleStringProperty((String) cellData.getValue().getMajor()));
		ChosenMajor.setMinWidth(150);

		// Add columns to the student table
		studentTable.getColumns().addAll(StudentId, name, tawjihiGrade, PlacementGrade, ChosenMajor);

		studentPane.getChildren().addAll(studentTable, allButton);
		return studentPane;
	}

	// Creates the Major Management Pane
	private VBox createMajorPane() {
		VBox majorPane = new VBox();
		majorPane.setPadding(new Insets(10));
		majorPane.setSpacing(10);

		// Button to manage major data
		Button addMajorButton = new Button("Major Data Management");
		addMajorButton.setOnAction(e -> {
			btMajor.setupButtons(root, majors, majorlist); // Set up buttons for major actions
		});

		// Table for displaying major information
		TableView<Major> majorTable = new TableView<>(majors);

		// Major Name column
		TableColumn<Major, String> majorName = new TableColumn<>("Major Name");
		majorName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		majorName.setMinWidth(150);

		// Acceptance Grade column
		TableColumn<Major, String> acceptanceGrade = new TableColumn<>("Acceptance Grade");
		acceptanceGrade.setCellValueFactory(
				cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getAcceptanceGrade())));
		acceptanceGrade.setMinWidth(150);

		// Tawjihi Weight column
		TableColumn<Major, String> TawjihiWeight = new TableColumn<>("Tawjihi Weight");
		TawjihiWeight.setCellValueFactory(
				cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getTawjihiWeight())));
		TawjihiWeight.setMinWidth(150);

		// Placement Test Weight column
		TableColumn<Major, String> PlacementTestWeight = new TableColumn<>("Placement Test Weight");
		PlacementTestWeight.setCellValueFactory(
				cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPlacementTestWeight())));
		PlacementTestWeight.setMinWidth(150);

		// Add columns to the major table
		majorTable.getColumns().addAll(majorName, acceptanceGrade, TawjihiWeight, PlacementTestWeight);

		majorPane.getChildren().addAll(majorTable, addMajorButton);
		return majorPane;
	}

	public static void main(String[] args) {
		launch(args); // Launches the application
	}
}
