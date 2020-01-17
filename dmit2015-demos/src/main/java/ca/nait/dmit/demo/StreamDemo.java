package ca.nait.dmit.demo;

import java.util.Arrays;
import java.util.stream.Stream;

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
 *		distinct
 *		filter
 *		limit 	 
 * 		skip
 * 		sorted
 * 		map
 * 
 * Terminal operation methods:
 * 		count
 * 		max
 * 		min
 * 		findFirst
 * 		findAny
 * 		allMatch
 * 		anyMatch
 * 		noneMatch
 * 		forEach
 * 		reduce
 *		collect
 *		toArray
 * 
 * Class-level (static) methods:
 * 		empty
 * 		of
 * 		concat
 *
 *
 * 	The methods are invokes using a stream pipeline that consist of a source(e.g., a list, a set, or an array),
 * 	a method that creates a stream, zero or more intermediate methods, and a final terminal method.
 *	Example: 
 *		set.stream().limit(10).distinct().count()
 *
 */
public class StreamDemo {

	public static void main(String[] args) {
		// Declare and initial an array of game titles
		String[] games = {
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
			"Mario Kart 8 Deluxe",
		};
		
		// forEach demo
		System.out.println("The first five games are: ");
		Stream.of(games)
			.limit(5)
			.sorted()
			.forEach(instance -> System.out.println(instance + " "));
		
		// skip and sorted demo
		System.out.println("\nThe games after the first five games sorted are: ");
		Stream.of(games)
			.skip(5)
			.sorted((lhs, rhs) -> lhs.compareToIgnoreCase(rhs))
			.forEach(instance -> System.out.println(instance + " "));
		
		// method reference demo
		System.out.println("\nThe games after the first five games sorted using method reference are: ");
		Stream.of(games)
			.skip(5)
			.sorted(String::compareToIgnoreCase)
			.forEach(item -> System.out.println(item + " "));
		
		// filter demo
		System.out.print("\nThe game title with Super keyword: \n");
		 Stream.of(games)
			.filter(instance -> instance.contains("Super"))
			.forEach(instance -> System.out.println(instance + " "));						

		// get demo
		System.out.print("\nThe shortest game title: "
			+ Stream.of(games)
				.max(String::compareTo)
				.get());
		System.out.print("\nThe longest game title: "
			+ Stream.of(games)
				.min(String::compareTo)
				.get());
		
		// anyMatch demo
		System.out.println("\nGames with contain the Mario title? "
			+ Stream.of(games)
				.anyMatch(instance -> instance.contains("Mario")));

		// allMatch demo
		System.out.println("\nAll games contains Mario? "
			+ Stream.of(games)
				.allMatch(instance -> instance.contains("Mario")));

		// noneMatch demo
		System.out.println("\nNo games start with Mario? "
			+ Stream.of(games)
				.noneMatch(instance -> instance.startsWith("Mario")));
		
		// distinct demo
		System.out.println("\nNumber of distinct game titles: "
			+ Stream.of(games)
				.distinct()
				.count());
		
		// findFirst demo
		System.out.println("\nFirst game title that starts with Super Mario in lower case: "
			+ Stream.of(games)
				.filter(instance -> instance.startsWith("Super Mario"))
				.map(String::toLowerCase)
				.findFirst()
				.get());
		
		// findAny demogames
		System.out.println("\nAny game title with Bros: "
			+ Stream.of(games)
				.filter(instance -> instance.contains("Bros"))
				.findAny()
				.get());
		
		// toArray demo
		Object[] superMarioTitles = Stream.of(games)
				.filter(instance -> instance.contains("Super Mario"))
				.toArray();
		System.out.println(Arrays.toString(superMarioTitles));

	}
}
