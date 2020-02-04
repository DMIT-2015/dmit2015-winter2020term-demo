package ca.nait.dmit.domain;

/**
 * Models a Rectangle shape.
 * 
 * @author Sam Wu
 * @version 2020.01.09
 */
public class Rectangle {

	// Declare data fields to store data
	/** The length of the rectangle */
	private double length;
	/** The width of the rectangle */
	private double width;
	
	// Declare getters/setters to encapsulate access to the data fields
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	
	// Declare constructors
	public Rectangle() {
		super();
		length = 1;
		width = 1;
	}
	public Rectangle(double length, double width) {
		super();
		this.length = length;
		this.width = width;
	}
	
	// Declare instance-level methods that performs operations using data fields
	/**
	 * Calculate and return the area of the rectangle
	 * @return The area of the rectangle
	 */
	public double area() {
		return length * width;
	}
	
	/**
	 * Calculate and return the perimeter of the rectangle.
	 * @return The perimeter of the rectangle
	 */
	public double perimeter() {
		return 2 * (length + width);
	}
	
	// Declare class-level methods that does not requires usage of data fields
	
}
