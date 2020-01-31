package ca.nait.dmit.demo;

import java.util.Arrays;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Demonstration on how to use the Java 8+ Stream API.
 * 
 * The methods in the Stream interface are divided into three groups: 
 * 		intermediate methods, 
 * 		terminal methods, 
 * 		class-level (static) methods.
 * An intermediate method transforms the stream into another stream.
 * A terminal method returns a result or performs actions. After a terminal method is executed, the stream is closed automatically.
 * A class-level (static) method creates a stream.
 * 
 * Intermediate operation methods:
 *		distinct 	- Return a stream consisting of distinct elements from the stream
 * 		skip 		- Return a stream consisting of the remaining elements in this stream after discarding the first n elements
 *		limit 		- Return a stream consisting of the first n elements from this stream
 *		filter 		- Return a stream consisting of the elements pass the test implemented by the function
 * 		sorted 		- Return a stream consisting of the elements of this stream sorted by natural order or by a comparator function
 * 		map 		- Return a stream consisting of the results of applying the functions to the elements in the stream
 * 
 * Terminal operation methods:
 * 		count 		- Return the number of elements in the stream
 * 		max 		- Return the maximum element in this stream based on the comparator
 * 		min 		- Return the minimum element in this stream based on the comparator
 * 		findFirst 	- Return the first element from this stream
 * 		findAny 	- Return any element from this stream
 * 		allMatch 	- Return true if all the elements in this stream match the predicate
 * 		anyMatch 	- Return true if one element in this stream matches the predicate 
 * 		noneMatch 	- Return true if no element in this stream matches the predicate
 * 		forEach 	- Execute the provided function once for each element in the stream
 * 		reduce 		- Reduces the elements in this stream to a value using the identity and an associative accumulation function. 
 * 						Returns an Optional result of an accumulation operation using the values in the stream.
 *		collect 	- Perform a mutable reduction operation on the elements of this stream using a Collector
 *		toArray 	- Return an array consisting of the elements in this stream
 * 
 * Class-level (static) methods:
 * 		empty		- Return an empty sequential stream
 * 		of			- Return a stream consisting of the specified values
 * 		concat		- Returns a lazily concatenated stream 
 *
 *
 * 	The methods are invokes using a stream pipeline that consist of a source(e.g., a list, a set, or an array),
 * 	a method that creates a stream, zero or more intermediate methods, and a final terminal method.
 *	Example: 
 *		set.stream().limit(10).distinct().count()
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
		boolean isAnyGameLessThan20 = games.stream().anyMatch(item -> item.getPrice() < 20);
		System.out.println("Any games less than $20 " + isAnyGameLessThan20);		
		
		// All games less than $50?
		boolean isAllGamesLessThan50 = games.stream().allMatch(item -> item.getPrice() < 50);
		System.out.println("All games less than $50 " + isAllGamesLessThan50);		
		
		// No PC Games on sale?
		boolean isNoPCGamesOnSale = games.stream().noneMatch(item -> item.getPlatform().equalsIgnoreCase("PC Games"));
		System.out.println("No PC Games for sale " + isNoPCGamesOnSale);
		
		// sum, max, min in a Stream
		double sumNintendoGamePrice = games.stream()
				.filter(item -> item.getPlatform().equalsIgnoreCase("Nintendo"))
				.mapToDouble(VideoGame::getPrice)
				.sum();
		System.out.println("The sum of all Nintendo games is: " + sumNintendoGamePrice);
		double maxNintendoGamePrice = games.stream()
				.filter(item -> item.getPlatform().equalsIgnoreCase("Nintendo"))
				.mapToDouble(VideoGame::getPrice)
				.max()
				.orElse(99.99);
		System.out.println("The maximum price for a Nintendo game on sale is: " + maxNintendoGamePrice);
		double minNintendoGamePrice = games.stream()
				.filter(item -> item.getPlatform().equalsIgnoreCase("Nintendo"))
				.mapToDouble(VideoGame::getPrice)
				.min()
				.orElse(0.00);
		System.out.println("The minimum price for a Nintendo game on sale is: " + minNintendoGamePrice);
		
		double sumPlaystationGamePrice = games.stream()
				.filter(item -> item.getPlatform().equalsIgnoreCase("Playstation"))
				.map(VideoGame::getPrice)
				.reduce(0.0, (item1, item2) -> item1 + item2);
		System.out.println("The sum of all Playstation games is: " + sumPlaystationGamePrice);
		double maxPlaystationGamePrice = games.stream()
				.filter(item -> item.getPlatform().equalsIgnoreCase("Playstation"))
				.map(VideoGame::getPrice)
				.reduce(Double::max)
				.orElse(99.99);
		System.out.println("The maximum price for a Playstation game on sale is: " + maxPlaystationGamePrice);
		double minPlaystationGamePrice = games.stream()
				.filter(item -> item.getPlatform().equalsIgnoreCase("Playstation"))
				.map(VideoGame::getPrice)
				.reduce(Double::min)
				.orElse(0.00);
		System.out.println("The minimum price for a Playstation game on sale is: " + minPlaystationGamePrice);
		
		// Collecting the result of a stream
		// Double the price for each game and collect the result in a List
//		List<VideoGame> doublePriceGames = games.stream()
//			.map(item -> {
//				item.setPrice( item.getPrice() * 2);
//				return item;
//			})
//			.collect(Collectors.toList());
//		doublePriceGames.stream().forEach(System.out::println);
		
		// Collect the result in a Set		
		Set<Double> uniquePrices = games.stream()
				.map(VideoGame::getPrice)
				.collect(Collectors.toSet());
		uniquePrices.stream().forEach(System.out::println);
		System.out.println("\n\n");
		
		// Collect the result in a sorted Set (TreeSet)
		uniquePrices = games.stream()
				.map(VideoGame::getPrice)
				.collect(Collectors.toCollection(TreeSet::new));
		uniquePrices.stream().forEach(System.out::println);
		System.out.println("\n\n");
		
		// Collect the result in a Map with title for key and price for value
		Map<String, Double> titleMap = games.stream()
				.collect(Collectors.toMap(VideoGame::getTitle, VideoGame::getPrice));
		titleMap.entrySet().stream().forEach(item -> System.out.println(item.getKey() + ":" + item.getValue()));
		System.out.println("\n\n");
		
		// Collect the result in a sorted Map with title for key and price for value
		Map<String, Double> sortedTitleMap = games.stream()
				.sorted(Comparator.comparingDouble(VideoGame::getPrice).reversed())
				.collect(Collectors.toMap(VideoGame::getTitle, VideoGame::getPrice, 
						(oldValue, newValue) -> oldValue, // Merge function used to resolve collisions between values associated with the same key
						LinkedHashMap::new));
		sortedTitleMap.entrySet().stream().forEach(item -> System.out.println(item.getKey() + ":" + item.getValue()));
		System.out.println("\n\n");
		
		// Concatenate the elements of a stream into a String
		String allWebCodeCsv = games.stream()
				.map(item -> String.valueOf(item.getWebCode()))	// value must be a String
				.sorted()
				.collect(Collectors.joining(","));
		System.out.println(allWebCodeCsv);
		
		// Return a new List of VideoGame for every even location in the collection
		List<VideoGame> evenGameList = IntStream.range(0, games.size())
				.filter(index -> index % 2 == 0)
				.mapToObj(index -> games.get(index))
				.collect(Collectors.toList());
		// Convert a List to an Array
		VideoGame[] evenGameArray = evenGameList.toArray(VideoGame[]::new); 
		System.out.println("Even index games");
		Arrays.stream(evenGameArray)
			.forEach(System.out::println);;
		
		// Summarization Collectors: 
		//	summingInt(), summingDouble()
		double sumPrices = games.stream()
				.collect(Collectors.summingDouble(VideoGame::getPrice));
		System.out.println("The total price of all games are: " + sumPrices);
		//	reducing()
		double sumGST = games.stream()
				.collect(Collectors.reducing(0.0, item -> item.getPrice() * 0.05, (item1, item2) -> item1 + item2));
		System.out.println("The total gst of all games are: " + sumGST);
		
		//	averagingInt(), averagingLong(), averagingDouble()
		double averagePrice = games.stream()
				.collect(Collectors.averagingDouble(VideoGame::getPrice));
		System.out.println("The average price of a game is: " + averagePrice);
		
		//	counting()
		double countGames = games.stream()
				.collect(Collectors.counting());
		System.out.println("The number of games on sale: " + countGames);
		
		//	maxBy(), minBy()
		double maxPrice = games.stream()
				.collect(Collectors.maxBy(Comparator.comparing(VideoGame::getPrice)))
				.orElseThrow()
				.getPrice();
		System.out.println("The max price for a game: " + maxPrice);
		
		//	summarizingInt(), summarizingLong(), summarizingDouble()
		DoubleSummaryStatistics gameStatistics = games.stream()
				.collect(Collectors.summarizingDouble(VideoGame::getPrice));
		System.out.println("Price statistics: " + gameStatistics);
		
		// Grouping: groupingBy()
		Map<String, List<VideoGame>> groupGamesByPlattform = games.stream()
				.collect(Collectors.groupingBy(VideoGame::getPlatform));
		System.out.println(groupGamesByPlattform);
		
		// Partitioning: partitioningBy()
		
		// Filtering, flattening, and mapping collections: filtering(), mapping(), flatMapping(), 
		
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


