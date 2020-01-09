package ca.nait.dmit.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CircleTest {

	@Test
	void testGetArea() {
		// Create a Circle object with a radius 5
		Circle circle1 = new Circle(5);
		// Verify the area is 78.54
		assertEquals(78.54, circle1.getArea(), 0.005);
	}

	@Test
	void testGetDiameter() {
		// Create a Circle object with a radius 5
		Circle circle1 = new Circle(5);
		// Verify the diameter is 10
		assertEquals(10, circle1.getDiameter());
	}

	@Test
	void testGetCircumference() {
		// Create a Circle object with a radius 5
		Circle circle1 = new Circle(5);
		// Verify the circumference is 31.42
		assertEquals(31.42, circle1.getCircumference(), 0.005);
	}

	@Test
	void testAllThreeMethods() {
		// Create a Circle object with a radius 5
		Circle circle1 = new Circle(5);
		assertAll("all methods", 
			() -> assertEquals(78.54, circle1.getArea(), 0.01),
			() -> assertEquals(10, circle1.getDiameter()),
			() -> assertEquals(31.42, circle1.getCircumference(), 0.005)
		);
	}

}
