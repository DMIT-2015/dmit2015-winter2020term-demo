package ca.nait.dmit.domain;

public class ChineseZodiac {

	// Declare data fields to store data
	private String name;
	private int birthYear;
	
	
	// Declare getters/setters to encapsulate access to the data fields
	// There are NO properties in Java, you have to use getters/setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getBirthYear() {
		return birthYear;
	}
	public void setBirthYear(int birthYear) {
		this.birthYear = birthYear;
	}
		
	// Declare constructors to initialize data fields
	public ChineseZodiac(String name, int birthYear) {
		super();
		this.name = name;
		this.birthYear = birthYear;
	}

	// Declare instance-level methods that perform operations using data fields
	public String getAnimal() {
		String animal = "Unknown";
		String[] animals = {
			"Rat",
			"Ox",
			"Tiger",
			"Rabbit",
			"Dragon",
			"Snake",
			"Horse",
			"Goat",
			"Monkey",
			"Rooster",
			"Dog",
			"Pig"
		};
		int offSet = (birthYear - 1900) % 12;
		animal = animals[offSet];		
		
		return animal;
	}
	
}
