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

	public static final int CURRENT_YEAR = LocalDate.now().getYear();

	@Test
	public void testGetAnimalRat() {
		int animalYear = 1900;
		while (animalYear <= CURRENT_YEAR) {
			ChineseZodiac chineseZodiac1 = new ChineseZodiac("Bob Jones", animalYear);
			assertEquals("rat", chineseZodiac1.getAnimal().toLowerCase());
			animalYear += 12;
		}
	}

	@Test
	public void testGetAnimalOx() {
		int animalYear = 1901;
		while (animalYear <= CURRENT_YEAR) {
			ChineseZodiac chineseZodiac1 = new ChineseZodiac("Bob Jones", animalYear);
			assertEquals("ox", chineseZodiac1.getAnimal().toLowerCase());
			animalYear += 12;
		}
	}

	@Test
	public void testGetAnimalTiger() {
		int animalYear = 1902;
		while (animalYear <= CURRENT_YEAR) {
			ChineseZodiac chineseZodiac1 = new ChineseZodiac("Bob Jones", animalYear);
			assertEquals("tiger", chineseZodiac1.getAnimal().toLowerCase());
			animalYear += 12;
		}
	}

	@Test
	public void testGetAnimalRabbit() {
		int animalYear = 1903;
		while (animalYear <= CURRENT_YEAR) {
			ChineseZodiac chineseZodiac1 = new ChineseZodiac("Bob Jones", animalYear);
			assertEquals("rabbit", chineseZodiac1.getAnimal().toLowerCase());
			animalYear += 12;
		}
	}

	@Test
	public void testGetAnimalDragon() {
		int animalYear = 1904;
		while (animalYear <= CURRENT_YEAR) {
			ChineseZodiac chineseZodiac1 = new ChineseZodiac("Bob Jones", animalYear);
			assertEquals("dragon", chineseZodiac1.getAnimal().toLowerCase());
			animalYear += 12;
		}
	}

	@Test
	public void testGetAnimalSnake() {
		int animalYear = 1905;
		while (animalYear <= CURRENT_YEAR) {
			ChineseZodiac chineseZodiac1 = new ChineseZodiac("Bob Jones", animalYear);
			assertEquals("snake", chineseZodiac1.getAnimal().toLowerCase());
			animalYear += 12;
		}
	}

	@Test
	public void testGetAnimalHorse() {
		int animalYear = 1906;
		while (animalYear <= CURRENT_YEAR) {
			ChineseZodiac chineseZodiac1 = new ChineseZodiac("Bob Jones", animalYear);
			assertEquals("horse", chineseZodiac1.getAnimal().toLowerCase());
			animalYear += 12;
		}
	}

	@Test
	public void testGetAnimalGoat() {
		int animalYear = 1907;
		while (animalYear <= CURRENT_YEAR) {
			ChineseZodiac chineseZodiac1 = new ChineseZodiac("Bob Jones", animalYear);
			assertEquals("goat", chineseZodiac1.getAnimal().toLowerCase());
			animalYear += 12;
		}
	}

	@Test
	public void testGetAnimalMonkey() {
		int animalYear = 1908;
		while (animalYear <= CURRENT_YEAR) {
			ChineseZodiac chineseZodiac1 = new ChineseZodiac("Bob Jones", animalYear);
			assertEquals("monkey", chineseZodiac1.getAnimal().toLowerCase());
			animalYear += 12;
		}
	}

	@Test
	public void testGetAnimalRooster() {
		int animalYear = 1909;
		while (animalYear <= CURRENT_YEAR) {
			ChineseZodiac chineseZodiac1 = new ChineseZodiac("Bob Jones", animalYear);
			assertEquals("rooster", chineseZodiac1.getAnimal().toLowerCase());
			animalYear += 12;
		}
	}

	@Test
	public void testGetAnimalDog() {
		int animalYear = 1910;
		while (animalYear <= CURRENT_YEAR) {
			ChineseZodiac chineseZodiac1 = new ChineseZodiac("Bob Jones", animalYear);
			assertEquals("dog", chineseZodiac1.getAnimal().toLowerCase());
			animalYear += 12;
		}
	}

	@Test
	public void testGetAnimalPig() {
		int animalYear = 1911;
		while (animalYear <= CURRENT_YEAR) {
			ChineseZodiac chineseZodiac1 = new ChineseZodiac("Bob Jones", animalYear);
			assertEquals("pig", chineseZodiac1.getAnimal().toLowerCase());
			animalYear += 12;
		}
	}
}
