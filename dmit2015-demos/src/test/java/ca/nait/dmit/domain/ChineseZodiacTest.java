package ca.nait.dmit.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class ChineseZodiacTest {

	@Test
	void shouldBeBaseYear() {
		ChineseZodiac chineseZodiac1 = new ChineseZodiac("Bob Jones", 1900);
		assertEquals("Bob Jones", chineseZodiac1.getName());
		assertEquals("Rat", chineseZodiac1.getAnimal());
	}
	
	@Test
	void shouldMidcyleYear() {
		ChineseZodiac chineseZodiac1 = new ChineseZodiac("Michael Smith", 1958);
		assertEquals("Michael Smith", chineseZodiac1.getName());
		assertEquals("Dog", chineseZodiac1.getAnimal());
	}
	
	@Test
	void shouldBeCurrentYear() {
		int currentYear = LocalDate.now().getYear();
		ChineseZodiac chineseZodiac1 = new ChineseZodiac("Billy Bob", currentYear);
		assertEquals("Billy Bob", chineseZodiac1.getName());
		assertEquals("Rat", chineseZodiac1.getAnimal());
	}

}
