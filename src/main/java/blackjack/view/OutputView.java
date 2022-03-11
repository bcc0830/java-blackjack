package blackjack.view;

import blackjack.domain.Cards;
import blackjack.domain.Dealer;
import blackjack.domain.Gamer;
import blackjack.domain.Players;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import blackjack.domain.Card;
import blackjack.domain.Player;
import blackjack.domain.ResultType;

public class OutputView {
	public void displayDealerOneCard(Dealer dealer) {
		Card card = dealer.getRandomOneCard();
		String cardInfo = convertCardToString(card);
		System.out.println(dealer.getName() + ": " + cardInfo);
	}

	public void displayFirstDistribution(Players players, Dealer dealer) {
		String joinedNamesInfo = players.getPlayers().stream()
			.map(Player::getName)
			.collect(Collectors.joining(", "));
		System.out.println(dealer.getName() + "와 " + joinedNamesInfo + "에게 2장의 카드를 나누었습니다.");
	}

	public void displayAllCard(Gamer gamer) {
		List<String> strings = generateAllCardStrings(gamer);
		System.out.println(gamer.getName() + "카드: " + String.join(", ", strings));
	}

	public void displayAllCardAndScore(Gamer gamer) {
		String joinedCardsInfo = String.join(", ", generateAllCardStrings(gamer));
		String scoreString = String.valueOf(gamer.getScore());
		if ("-1".equals(scoreString)) {
			scoreString = "버스트";
		}
		System.out.println(gamer.getName() + "카드: " + joinedCardsInfo + " - 결과: " + scoreString);
	}

	private List<String> generateAllCardStrings(Gamer gamer) {
		return gamer.getCards().stream()
			.map(card -> convertCardToString(card) + card.getType())
			.collect(Collectors.toList());
	}

	private String convertCardToString(Card card) {
		String cardNumber = String.valueOf(card.getNumber());
		if (card.isAce()) {
			cardNumber = "A";
		}
		return cardNumber + card.getType();
	}

	public void displayDealerUnderSevenTeen() {
		System.out.println("딜러는 16이하라 한장의 카드를 더 받았습니다.");
	}

	public void displayResult(Map<Player, ResultType> result) {
		final int winCount = (int) result.values().stream()
			.filter(resultType -> resultType == ResultType.LOSE)
			.count();
		final int loseCount = (int)result.values().stream()
			.filter(resultType -> resultType == ResultType.WIN)
			.count();
		final int drawCount = result.size() - (winCount + loseCount);
		System.out.println("## 최종 승패");
		System.out.println("딜러: " + winCount + "승 " + loseCount + "패 " + drawCount + "무");
		for (Player player : result.keySet()) {
			System.out.println(player.getName() + ": " + result.get(player).getValue());
		}
	}
}
