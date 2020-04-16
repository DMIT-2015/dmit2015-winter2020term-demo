package dmit2015;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SampleTest {

	@Test
	public void shouldParse12Hours() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm a");
		LocalDateTime dateTime = LocalDateTime.parse("2020-04-15 3:08 PM", formatter);
		System.out.println(dateTime);
	}
	
	@Test
	public void shouldParse24Hours() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse("2020-04-15 15:08", formatter);
		System.out.println(dateTime);
	}
}