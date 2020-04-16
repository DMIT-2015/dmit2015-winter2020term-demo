package dmit2015;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

public class JsonbLocalDateTimeParseTest {

	@Test
	public void shouldParse12HoursLocalTime() {
		// 12-hour clock parsing works for US but not for CANADA locale
		// THe default locale on your machine may be US or CANADA
		// Specify the US locate if you want to parse a 12-hour clock.
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.US);
		LocalTime time = LocalTime.parse("3:08 PM", formatter);
		
		Jsonb jsonb = JsonbBuilder.create();
		// Use Jsonb to serialize the LocalTime object to a JSON string
		String jsonTime = jsonb.toJson(time);
		System.out.println("JSON Time: " + jsonTime);
		// Use Jsonb to deserialize the JSON string to a LocalTime object
		LocalTime javatime = jsonb.fromJson(jsonTime, LocalTime.class);
		System.out.println("Java Time: " + javatime);
	}

	@Test
	public void shouldParse24HoursLocalTime() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
		LocalTime time = LocalTime.parse("15:08", formatter);
		
		Jsonb jsonb = JsonbBuilder.create();
		// Use Jsonb to serialize the LocalTime object to a JSON string
		String jsonTime = jsonb.toJson(time);
		System.out.println("JSON Time: " + jsonTime);
		// Use Jsonb to deserialize the JSON string to a LocalTime object
		LocalTime javatime = jsonb.fromJson(jsonTime, LocalTime.class);
		System.out.println("Java Time: " + javatime);
	}
	
	@Test
	public void shouldParse12HoursLocalDate() {
		// 12-hour clock parsing works for US but not for CANADA locale
		// THe default locale on your machine may be US or CANADA
		// Specify the US locate if you want to parse a 12-hour clock.
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy h:mm a", Locale.US);
		LocalDate date = LocalDate.parse("4/15/2020 9:50 PM", formatter);
		
		Jsonb jsonb = JsonbBuilder.create();
		// Use Jsonb to serialize the LocalDate object to a JSON string
		String jsonDate= jsonb.toJson(date);
		System.out.println("JSON Date: " + jsonDate);
		// Use Jsonb to deserialize the JSON string to a LocalDate object
		LocalDate javaDate = jsonb.fromJson(jsonDate, LocalDate.class);
		System.out.println("Java Date: " + javaDate);
	}

	@Test
	public void shouldParse24HoursLocalDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy h:mm a", Locale.US);
		LocalDate date = LocalDate.parse("4/15/2020 21:50", formatter);
		
		Jsonb jsonb = JsonbBuilder.create();
		// Use Jsonb to serialize the LocalDate object to a JSON string
		String jsonDate= jsonb.toJson(date);
		System.out.println("JSON Date: " + jsonDate);
		// Use Jsonb to deserialize the JSON string to a LocalDate object
		LocalDate javaDate = jsonb.fromJson(jsonDate, LocalDate.class);
		System.out.println("Java Date: " + javaDate);
	}

	@Test
	public void shouldParse12HoursLocalDateTime() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm a", Locale.US);
		LocalDateTime dateTime = LocalDateTime.parse("2020-04-15 3:08 PM", formatter);
		
		Jsonb jsonb = JsonbBuilder.create();
		// Use Jsonb to serialize the LocalDateTime object to a JSON string
		String jsonDateTime = jsonb.toJson(dateTime);
		System.out.println("JSON DateTime: " + jsonDateTime);
		// Use Jsonb to deserialize the JSON string to a LocalDateTime object
		LocalDateTime javaDateTime = jsonb.fromJson(jsonDateTime, LocalDateTime.class);
		System.out.println("Java DateTime: " + javaDateTime);
	}
	
	@Test
	public void shouldParse24HoursLocalDateTime() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse("2020-04-15 15:08", formatter);
		
		Jsonb jsonb = JsonbBuilder.create();
		// Use Jsonb to serialize the LocalDateTime object to a JSON string
		String jsonDateTime = jsonb.toJson(dateTime);
		System.out.println("JSON DateTime: " + jsonDateTime);
		// Use Jsonb to deserialize the JSON string to a LocalDateTime object
		LocalDateTime javaDateTime = jsonb.fromJson(jsonDateTime, LocalDateTime.class);
		System.out.println("Java DateTime: " + javaDateTime);
	}
}