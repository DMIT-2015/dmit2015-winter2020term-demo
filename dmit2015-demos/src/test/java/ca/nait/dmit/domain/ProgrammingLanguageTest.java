package ca.nait.dmit.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProgrammingLanguageTest {
	@Test
	public void testPowerOperator() { // 2^3 should be 8
//		assertEquals(8, 2 ^ 3);
		assertEquals(8, Math.pow(2, 3));
	}

	@Test
	public void testDivision() { // 4/5 should be 0.80
//		assertEquals(0.80, 4 / 5, 0);
		assertEquals(0.80, 4.0 / 5, 0);
	}

	@Test
	public void testStringCompare() { // do a case-insensitive string compare
		assertTrue("dmit2015" == "dmit2015");
	}

	@Test//(java.lang.ArithmeticException.class)
	public void testForException() { // this test method will succeed if a exception is ArithmeticException is thrown
//		assertEquals(0, 3 / 0);
		Assertions.assertThrows(ArithmeticException.class, () -> {
			assertEquals(0, 3 / 0);
		});
	}
}