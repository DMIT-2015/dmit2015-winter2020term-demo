package ca.nait.dmit.domain;

public class BMI {

	// Declare data fields to store data
	private String name;
	private int weight;
	private int height;
	
	// Encapsulate access to the data fields using getters/setters

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	

	// Declare constructor to initialize the object
	public BMI(String name, int weight, int height) {
		super();
		this.name = name;
		this.weight = weight;
		this.height = height;
	}
	
	
	// Declare instance-level methods that performs operations using the data fields
	public double getBMI() {
		return 703 * weight / Math.pow(height, 2);
	}
	
	public String getCategory() {
		String category = "obese";
		final double bmiValue = getBMI();
		if (bmiValue < 18.5) {
			category = "underweight";
		} else if (bmiValue < 25) {
			category = "normal";
		} else if (bmiValue < 30) {
			category = "overweight";
		} else {
			category = "obese";
		}
		return category;
	}
	
}
