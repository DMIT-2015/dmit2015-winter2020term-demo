package ca.nait.dmit.domain;

/**
 * This class is use to calculate a person's body mass index (BMI) and their BMI
 * Category.
 * 
 * @author yourFirstName yourLastName
 * @version 2015.01.16
 */
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
	/**
	 * Calculate the body mass index (BMI) using the weight and height of the
	 * person. The BMI of a person is calculated using the formula: BMI = 700 *
	 * weight / (height * height) where weight is in pounds and height is in inches.
	 * 
	 * @return the body mass index (BMI) value of the person
	 */
	public double getBMI() {
		return 703 * weight / Math.pow(height, 2);
	}

	/**
	 * Determines the BMI Category of the person using their BMI value and comparing
	 * it against the following table.
	 * <table>
	 * <thead>
	 * <tr>
	 * <th>BMI Range</th>
	 * <th>BMI Category</th>
	 * </tr>
	 * </thead> <tbody>
	 * <tr>
	 * <td>< 18.5</td>
	 * <td>underweight</td>
	 * </tr>
	 * <tr>
	 * <td>>= 18.5 and < 25</td>
	 * <td>normal</td>
	 * </tr>
	 * <tr>
	 * <td>>= 25 and < 30</td>
	 * <td>overweight</td> *
	 * </tr>
	 * <tr>
	 * <td>>= 30</td>
	 * <td>obese</td>
	 * </tr>
	 * </tbody>
	 * </table>
	 *
	 * @return one of following: underweight, normal, overweight, obese.
	 */

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
