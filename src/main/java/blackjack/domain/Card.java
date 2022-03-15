package blackjack.domain;

public class Card {
	private final CardDenomination cardDenomination;
	private final CardSuit cardSuit;

	public Card(CardDenomination cardDenomination, CardSuit cardSuit) {
		this.cardDenomination = cardDenomination;
		this.cardSuit = cardSuit;
	}

	public int getScore() {
		return cardDenomination.getLetterScore();
	}

	public String getCardName() {
		return cardDenomination.getCardName();
	}

	public CardSuit getType() {
		return cardSuit;
	}

	public boolean isAce() {
		return cardDenomination == CardDenomination.ACE;
	}
}
