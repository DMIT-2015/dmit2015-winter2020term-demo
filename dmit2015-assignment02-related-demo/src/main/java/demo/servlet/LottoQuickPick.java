package demo.servlet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.Data;

@Data
public class LottoQuickPick {

	private LotteryType game = LotteryType.LOTTO_MAX;
	
	@Min(value = 1, message = "Quantity must be between 1 and 10")
	@Max(value = 10, message = "Quantity must be between 1 and 10")
	private int quantity;
		
	public String getSinglePick() {
		LotteryCanada lotto = new LotteryCanada(game);
		Integer[] quickPick = lotto.doOneQuickPick();
		return Arrays.stream(quickPick)
				.map(num -> num.toString())			// convert each object in the stream from an Integer to an String 
				.collect(Collectors.joining(","));	// combine all the String values in the stream and separate each value by a delimiter
	}
	
	public List<String> getQuickPicks() {
		List<String> quickPicksStringList = new ArrayList<>();
		
		LotteryCanada lotto = new LotteryCanada(game);
		List<Integer[]> quickPicks = lotto.doManyQuickPicks(quantity);
		quickPicks.forEach(singleQuick -> {
			// Convert array Integer to a String value where each number is separated by a comma
			String singleQuickPickString = Arrays.stream(singleQuick)
					.map(num -> num.toString())
					.collect(Collectors.joining(","));
			quickPicksStringList.add(singleQuickPickString);
		});
		
		return quickPicksStringList;
	}
	
}