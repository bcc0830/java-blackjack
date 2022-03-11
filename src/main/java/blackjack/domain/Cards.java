package blackjack.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cards {
	private final List<Card> cards = new ArrayList<>();
	private static final int BUST_THRESHOLD = 21;
	private static final int BUST_STATUS = -1;
	private static final int ANOTHER_ACE_SCORE = 11;
	private int score = 0;

	public void addCard(Card card) {
		cards.add(card);
		calculateScore();
	}

	private void calculateScore() {
		int aceCnt = countAceCard();
		int notAceScore = calculateNotAceCardScore();
		List<Integer> possible = getPossibleAceScores(aceCnt);
		this.score = possible.stream()
			.filter(each -> each + notAceScore <= BUST_THRESHOLD)
			.map(each -> each + notAceScore)
			.max(Integer::compare).orElse(BUST_STATUS);
	}

	private List<Integer> getPossibleAceScores(int aceCnt) {
		List<Integer> possible = new ArrayList<>();
		for (int i = 0; i <= aceCnt; i++) {
			possible.add(i + ANOTHER_ACE_SCORE * (aceCnt - i));
		}
		return possible;
	}

	private int countAceCard() {
		return (int) cards.stream()
			.filter(Card::isAce)
			.count();
	}

	private int calculateNotAceCardScore() {
		return cards.stream()
			.filter(card -> !card.isAce())
			.mapToInt(Card::getNumber)
			.sum();
	}

	public Card getRandomCard() {
		return cards.get(new Random().nextInt(cards.size()));
	}

	public List<Card> getCards() {
		return this.cards;
	}

	public int getScore() {
		return score;
	}
}
