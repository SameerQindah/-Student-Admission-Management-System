package application;

public class Student implements Comparable<Student> {
	// variables
	private int id;
	private String name;
	private double tawjihiGrade;
	private double placementTestGrade;
	private Object major;
	private double admissionMark;

	// constructor
	public Student(int id, String name, double tawjihiGrade, double placementTestGrade, Object major) {
		this.id = id;
		this.name = name;
		this.tawjihiGrade = tawjihiGrade;
		this.placementTestGrade = placementTestGrade;
		this.major = major;
	}

	// getter and setter
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getTawjihiGrade() {
		return tawjihiGrade;
	}

	public void setTawjihiGrade(double tawjihiGrade) {
		this.tawjihiGrade = tawjihiGrade;
	}

	public double getPlacementTestGrade() {
		return placementTestGrade;
	}

	public void setPlacementTestGrade(double placementTestGrade) {
		this.placementTestGrade = placementTestGrade;
	}

	public Object getMajor() {
		return major;
	}

	public void setMajor(Object major) {
		this.major = major;
	}

	// method to Calculate Admission Mark
	public double getAdmissionMark() {
		if (major instanceof Major) {
			admissionMark = (tawjihiGrade * ((Major) major).getTawjihiWeight())
					+ (placementTestGrade * ((Major) major).getPlacementTestWeight());
		} else {
			admissionMark = 0; // Default value if major is null or not properly set
		}
		return admissionMark;
	}

	public void setAdmissionMark(double admissionMark) {
		this.admissionMark = admissionMark;
	}

	// method compareTo to sort by Admission Mark
	@Override
	public int compareTo(Student o) {
		if (o == null) {
			throw new NullPointerException("Cannot compare to a null Student object.");
		}

		double thisAdmissionMark = this.getAdmissionMark();
		double otherAdmissionMark = o.getAdmissionMark();

		// Reverse the order for descending sort by admissionMark
		return Double.compare(otherAdmissionMark, thisAdmissionMark);
	}

}
