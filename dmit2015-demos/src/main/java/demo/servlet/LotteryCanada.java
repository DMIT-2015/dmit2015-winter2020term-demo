package demo.servlet;

import java.util.*;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.Setter;

public class LotteryCanada {
		
	@Getter @Setter
	private LotteryType lottoType = LotteryType.LOTTO_MAX;
	
	public LotteryCanada(LotteryType lottoType) {
		super();
		this.lottoType = lottoType;
	}

	public Integer[] doOneQuickPick() {
		final int numberOfSelections = (lottoType == LotteryType.LOTTO_MAX) ? 7 : 6;
		final int maxNumber = (lottoType == LotteryType.LOTTO_MAX) ? 50 : 49;
		Set<Integer> numberSet = new TreeSet<>();
		Random rand = new Random();
		while (numberSet.size() < numberOfSelections) {
			numberSet.add(rand.nextInt(maxNumber) + 1);
		}
		return numberSet.toArray(Integer[]::new);
	}
	
	public List<Integer[]> doManyQuickPicks(int totalPicks) {
		List<Integer[]> quickPicksList = new ArrayList<>();
		Stream.iterate(1, num -> num + 1)
			.limit(totalPicks)
			.map(num -> doOneQuickPick())
			.forEach(singleQuickPick -> quickPicksList.add(singleQuickPick));
		return quickPicksList;
	}
}