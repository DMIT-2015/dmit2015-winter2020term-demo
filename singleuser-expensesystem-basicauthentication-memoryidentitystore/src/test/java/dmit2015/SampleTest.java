package dmit2015;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public class SampleTest {

	@Test
	public void shouldParse12HoursLocalTimeDefaultLocale() {
		// Default can either be US or en_CA depending on your environment
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
		LocalTime time = LocalTime.parse("3:08 PM", formatter);
		System.out.println(time);
	}
	
	@Test
	public void shouldParse12HoursLocalTimeUSLocale() {
		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
				.appendPattern("h:mm a")
				.toFormatter(Locale.US);
		LocalTime time = LocalTime.parse("3:08 PM", formatter);
		System.out.println(time);
	}


	@Test
	public void shouldParse24HoursLocalTime() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
		LocalTime time = LocalTime.parse("15:08", formatter);
		System.out.println(time.toString());
	}

	@Test
	public void shouldParse12HoursLocaleDateTime() {
		System.out.println(Locale.getDefault().toString());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm a", Locale.US);
		LocalDateTime dateTime = LocalDateTime.parse("2020-04-15 3:08 PM", formatter);
		System.out.println(dateTime);
	}
	
	@Test
	public void shouldParse24HoursLocalDateTime() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse("2020-04-15 15:08", formatter);
		System.out.println(dateTime);
	}
}