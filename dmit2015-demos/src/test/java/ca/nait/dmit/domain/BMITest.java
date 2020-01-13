package ca.nait.dmit.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BMITest {

	@Test
	void shouldBeUnderweight() {
		BMI bmi1 = new BMI("Shampoo Voo", 100, 66);
		assertEquals(16.1, bmi1.getBMI(), 0.05);
		assertEquals("underweight", bmi1.getCategory());
		assertEquals("Shampoo Voo", bmi1.getName());
	}
	
	@Test
	void shouldBeNormal() {
		BMI bmi1 = new BMI("Shampoo Voo", 140, 66);
		assertEquals(22.6, bmi1.getBMI(), 0.05);
		assertEquals("normal", bmi1.getCategory());
		assertEquals("Shampoo Voo", bmi1.getName());
	}
	
	@Test
	void shouldBeOverweight() {
		BMI bmi1 = new BMI("Shampoo Voo", 175, 66);
		assertEquals(28.2, bmi1.getBMI(), 0.05);
		assertEquals("overweight", bmi1.getCategory());
		assertEquals("Shampoo Voo", bmi1.getName());
	}
	
	@Test
	void shouldBeObese() {
		BMI bmi1 = new BMI("Shampoo Voo", 200, 66);
		assertEquals(32.3, bmi1.getBMI(), 0.05);
		assertEquals("obese", bmi1.getCategory());
		assertEquals("Shampoo Voo", bmi1.getName());
	}

}
