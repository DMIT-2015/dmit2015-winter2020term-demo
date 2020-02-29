package demo.servlet;

import java.util.*;
import java.util.stream.Stream;

public class LotteryCanada {
	
//	public enum LotteryType {LOTTO_MAX, LOTTO_649};
	private LotteryType lottoType = LotteryType.LOTTO_MAX;
	
	public static final int LOTTO_MAX_HIGHEST_NUMBER = 50;
	public static final int LOTTO_649_HIGHEST_NUMBER = 49;
	public static final int LOTTO_MAX_SELECTIONS = 7;
	public static final int LOTTO_649_SELECTIONS= 6;
	
	public LotteryCanada(LotteryType lottoType) {
		super();
		this.lottoType = lottoType;
	}

	public Integer[] doOneQuickPick() {
		final int numberOfSelections = (lottoType == LotteryType.LOTTO_MAX) ? LOTTO_MAX_SELECTIONS : LOTTO_649_SELECTIONS;
		final int maxNumber = (lottoType == LotteryType.LOTTO_MAX) ? LOTTO_MAX_HIGHEST_NUMBER : LOTTO_649_HIGHEST_NUMBER;
		Set<Integer> numberSet = new TreeSet<>();
		Random rand = new Random();
		while (numberSet.size() < numberOfSelections) {
			numberSet.add(rand.nextInt(maxNumber) + 1);
		}
		return numberSet.toArray(Integer[]::new);
	}
	
	public List<Integer[]> doManyQuickPicks(int totalPicks) {
		List<Integer[]> quickPicksList = new ArrayList<>();
		Stream.iterate(1, num -> num + 1)	// infinite sequential ordered stream
			.limit(totalPicks)
			.map(num -> doOneQuickPick())
			.forEach(singleQuickPick -> quickPicksList.add(singleQuickPick));
		return quickPicksList;
	}
}
