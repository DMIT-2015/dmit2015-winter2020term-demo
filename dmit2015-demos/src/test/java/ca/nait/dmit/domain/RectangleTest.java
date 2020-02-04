package ca.nait.dmit.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class RectangleTest {

	@Test
	public void testCase1() {
		Rectangle rectangle1 = new Rectangle();
		rectangle1.setWidth(4);
		rectangle1.setLength(40);
		assertEquals(160, rectangle1.area(), 0);
		assertEquals(88, rectangle1.perimeter(), 0);
	}

	@Test
	public void testCase2() {
		Rectangle rectangle1 = new Rectangle();
		rectangle1.setWidth(3.5);
		rectangle1.setLength(35.9);
		assertEquals(125.65, rectangle1.area(), 0.005);
		assertEquals(78.8, rectangle1.perimeter(), 0);

	}

}
