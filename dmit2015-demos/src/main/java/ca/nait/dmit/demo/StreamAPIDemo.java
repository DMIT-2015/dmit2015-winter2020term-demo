package ca.nait.dmit.demo;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Demonstration on how to use the Java 8+ Stream API.
 * 
 * The methods in the Stream interface are divided into three groups:
 * intermediate methods, terminal methods, class-level (static) methods. An
 * intermediate method transforms the stream into another stream. A terminal
 * method returns a result or performs actions. After a terminal method is
 * executed, the stream is closed automatically. A class-level (static) method
 * creates a stream.
 * 
 * Intermediate operation methods: distinct - Return a stream consisting of
 * distinct elements from the stream skip - Return a stream consisting of the
 * remaining elements in this stream after discarding the first n elements limit
 * - Return a stream consisting of the first n elements from this stream filter
 * - Return a stream consisting of the elements pass the test implemented by the
 * function sorted - Return a stream consisting of the elements of this stream
 * sorted by natural order or by a comparator function map - Return a stream
 * consisting of the results of applying the functions to the elements in the
 * stream
 * 
 * Terminal operation methods: count - Return the number of elements in the
 * stream max - Return the maximum element in this stream based on the
 * comparator min - Return the minimum element in this stream based on the
 * comparator findFirst - Return the first element from this stream findAny -
 * Return any element from this stream allMatch - Return true if all the
 * elements in this stream match the predicate anyMatch - Return true if one
 * element in this stream matches the predicate noneMatch - Return true if no
 * element in this stream matchies the predicate forEach - Execute the provided
 * function once for each element in the stream reduce - Reduces the elements in
 * this stream to a value using the identity and an associative accumulation
 * function. Returns an Optional resutl of an accumulation operation using the
 * values in the stream. collect - Perform a mutable reducation operation ont
 * the elements of this stream using a Collector toArray - Return an array
 * consisting of the elements in this stream
 * 
 * Class-level (static) methods: empty - Return an empty sequential stream of -
 * Return a stream consisting of the specified values concat
 *
 *
 * The methods are invokes using a stream pipeline that consist of a
 * source(e.g., a list, a set, or an array), a method that creates a stream,
 * zero or more intermediate methods, and a final terminal method. Example:
 * set.stream().limit(10).distinct().count()
 *
 */

@Data
@AllArgsConstructor
class VideoGame {
	private String title;
	private String platform; // Playstation, Xbox, Nintendo, PC Games
	private double price;
	private long webCode;
}

public class StreamAPIDemo {


	static void demoProcessingObjects() {
		List<VideoGame> games = Arrays.asList(
			new VideoGame("Diablo III Eternal Collection (Switch)", "Nintendo", 34.99, 12919269),
			new VideoGame("NBA 2K20 (PS4)", "Playstation", 49.99, 13720461),
			new VideoGame("NBA 2K20 (Switch)", "Nintendo", 49.99, 13720465),
			new VideoGame("NBA 2K20 (Xbox One)", "XBox", 49.99, 13720462),
			new VideoGame("Forza Horizon 4 (Xbox One)", "XBox", 39.99, 12612447),
			new VideoGame("Final Fantasy X/X-2 HD Remaster (Switch)", "Nintendo", 34.99, 13208397),
			new VideoGame("The Outer Worlds (PS4)", "Playstation", 49.99, 13642197),
			new VideoGame("Kingdom Hearts 3 (PS4)", "Playstation", 19.99, 10255421),
			new VideoGame("Overwatch Legendary Edition (Switch)", "Nintendo", 34.99, 13899355),
			new VideoGame("WWE 2K20 (PS4)", "Playstation", 39.99, 13836134),
			new VideoGame("Kingdom Hearts 3 (Xbox One)", "XBox", 19.99, 10255666),
			new VideoGame("Dragon Quest Builders 2 (PS4)", "Playstation", 29.99, 13414143)
		);
		
		// Display all Nintendo games
		System.out.println("All Nintendo games on sale");
		games.stream()
			.filter(item -> item.getPlatform().equalsIgnoreCase("Nintendo"))
			.forEach(System.out::println);
		
		// Display the title of each game
		System.out.println("Game titles on sale");
		games.stream()
			.map(VideoGame::getTitle)
			.forEach(System.out::println);
		
		// Display the unique game platform
		System.out.println("Game Platform");
		games.stream()
			.map(VideoGame::getPlatform)
			.distinct()
			.forEach(System.out::println);
		
		// Find any game on sale
		Optional<VideoGame> anyGame = games.stream().findAny();
		if (!anyGame.isEmpty()) {
			System.out.println("Any game: " + anyGame.get());
		} else {
			System.out.println("No games on sale.");
		}
		
		// Find the first XBox game sale
		Optional<VideoGame> firstGame = games.stream()
			.filter(item -> item.getPlatform().equalsIgnoreCase("Xbox"))
			.findFirst();
		if (!firstGame.isEmpty()) {
			System.out.println("First Xbox game: " + firstGame.get());
		} else {
			System.out.println("No XBox games on sale.");
		}
		
		// Any games less than $20?
		// All games less than $50?
		// No PC Games on sale?
		
		// sum, max, min in a Stream
		
		// Collecting the result of a stream
		
		// Summarization collectors
		
		// Grouping
		
	}

	static void demoProcessingValues() {
		// Generate exactly 7 values in the range of [1..50].
		IntStream lottoMaxStream = new Random().ints(7, 1, 50);
		lottoMaxStream.forEach(System.out::println);
		
		// Declare and initial an array of game titles
		List<String> games = Arrays.asList(
			"Mario Kart 8 Deluxe", 
			"Super Mario Party", 
			"Super Mario Odyssey",
			"New Super Mario Bros. U Deluxe", 
			"Mario & Sonic at the Olympic Games: Tokyo 2020",
			"Super Mario Maker 2", 
			"Mario + Rabbids Kingdom Battle", 
			"Luigi's Mansion 3", 
			"Mario Tennis Aces",
			"Super Smash Bros Ultimate", 
			"Mario Kart 8 Deluxe"
		);

		// forEach demo
		System.out.println("The first five games are: ");
		games.stream()
			.limit(5)
			.sorted()
			.forEach(instance -> System.out.println(instance + " "));

		// skip and sorted demo
		System.out.println("\nThe games after the first five games sorted are: ");
		games.stream()
			.skip(5)
			.sorted((lhs, rhs) -> lhs.compareToIgnoreCase(rhs))
			.forEach(System.out::println);

		// method reference demo
		System.out.println("\nThe games after the first five games sorted using method reference are: ");
		games.stream()
			.skip(5)
			.sorted(String::compareToIgnoreCase)
			.forEach(item -> System.out.println(item + " "));

		// filter demo
		System.out.print("\nThe game title with Super keyword: \n");
		games.stream()
			.filter(instance -> instance.contains("Super"))
			.forEach(instance -> System.out.println(instance + " "));

		// get demo
		System.out.print("\nThe shortest game title: " + games.stream().max(String::compareTo).get());
		System.out.print("\nThe longest game title: " + games.stream().min(String::compareTo).get());

		// anyMatch demo
		System.out.println("\nGames with contain the Mario title? "
				+ Stream.of(games).anyMatch(instance -> instance.contains("Mario")));

		// allMatch demo
		System.out.println(
				"\nAll games contains Mario? " + games.stream().allMatch(instance -> instance.contains("Mario")));

		// noneMatch demo
		System.out.println(
				"\nNo games start with Mario? " + games.stream().noneMatch(instance -> instance.startsWith("Mario")));

		// distinct demo
		System.out.println("\nNumber of distinct game titles: " + games.stream().distinct().count());

		// findFirst demo
		System.out.println("\nFirst game title that starts with Super Mario in lower case: " + games.stream()
				.filter(instance -> instance.startsWith("Super Mario")).map(String::toLowerCase).findFirst().get());

		// findAny demo
		System.out.println("\nAny game title with Bros: "
				+ games.stream().filter(instance -> instance.contains("Bros")).findAny().get());

		// toArray demo
		Object[] superMarioTitles = games.stream().filter(instance -> instance.contains("Super Mario")).toArray();
		System.out.println(Arrays.toString(superMarioTitles));

	}

	public static void main(String[] args) {
//		demoProcessingValues();
		
		demoProcessingObjects();
	}
}