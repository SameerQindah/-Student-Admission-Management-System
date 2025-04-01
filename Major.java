package application;

public class Major implements Comparable<Major> {
	// variables
	private String name;
	private double acceptanceGrade;
	private double TawjihiWeight;
	private double PlacementTestWeight;

	// constructor
	public Major(String name, double acceptanceGrade, double tawjihiWeight, double placementTestWeight) {
		super();
		this.name = name;
		this.acceptanceGrade = acceptanceGrade;
		TawjihiWeight = tawjihiWeight;
		PlacementTestWeight = placementTestWeight;
	}

	// Getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getAcceptanceGrade() {
		return acceptanceGrade;
	}

	public void setAcceptanceGrade(double acceptanceGrade) {
		this.acceptanceGrade = acceptanceGrade;
	}

	public double getTawjihiWeight() {
		return TawjihiWeight;
	}

	public void setTawjihiWeight(double tawjihiWeight) {
		TawjihiWeight = tawjihiWeight;
	}

	public double getPlacementTestWeight() {
		return PlacementTestWeight;
	}

	public void setPlacementTestWeight(double placementTestWeight) {
		PlacementTestWeight = placementTestWeight;
	}

	// compareTo method to sort
	@Override
	public int compareTo(Major o) {
		return Character.compare(this.name.toLowerCase().charAt(0), o.getName().toLowerCase().charAt(0));
	}

}
