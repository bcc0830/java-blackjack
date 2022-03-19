package blackjack.domain;

import static org.assertj.core.api.Assertions.assertThat;

import blackjack.domain.betting.BettingToken;
import blackjack.domain.betting.Profit;
import blackjack.domain.card.Card;
import blackjack.domain.card.CardDenomination;
import blackjack.domain.card.CardSuit;
import blackjack.domain.card.Cards;
import blackjack.domain.gamer.Dealer;
import blackjack.domain.gamer.Name;
import blackjack.domain.gamer.Player;
import blackjack.domain.gamer.Players;
import blackjack.domain.process.BettingResult;
import java.util.List;

import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BettingResultTest {
	Players players;
	Player player1;
	Player player2;
	Player player3;

	@BeforeEach
	void gamer_init() {
		player1 = getBustedPlayer();
		player2 = getBlackJackedPlayer();
		player3 = getNormalPlayer();
		players = new Players(List.of(player1, player2, player3));
	}

	@Test
	void dealer_bust() {
		Dealer dealer = new Dealer(new Cards(
			List.of(new Card(CardDenomination.QUEEN, CardSuit.CLOVER), new Card(CardDenomination.KING, CardSuit.SPADE),
				new Card(CardDenomination.TWO, CardSuit.SPADE))));
		BettingResult bettingResult = new BettingResult(players, dealer);
		List<Integer> totalResult = bettingResult.calculatePlayersBettingResult().values().stream()
			.map(Profit::calculateResult)
			.collect(Collectors.toList());
		assertThat(totalResult).containsSequence(-500, -3000, 1500, 2000);
	}

	@Test
	void dealer_blackjack() {
		Dealer dealer = new Dealer(new Cards(List.of(new Card(CardDenomination.QUEEN, CardSuit.CLOVER),
			new Card(CardDenomination.ACE, CardSuit.SPADE))));
		BettingResult bettingResult = new BettingResult(players, dealer);
		List<Integer> totalResult = bettingResult.calculatePlayersBettingResult().values().stream()
			.map(Profit::calculateResult)
			.collect(Collectors.toList());
		assertThat(totalResult).containsSequence(5000, -3000, 0, -2000);
	}

	@Test
	void dealer_normal() {
		Dealer dealer = new Dealer(new Cards(List.of(new Card(CardDenomination.QUEEN, CardSuit.CLOVER),
			new Card(CardDenomination.NINE, CardSuit.SPADE))));
		BettingResult bettingResult = new BettingResult(players, dealer);
		List<Integer> totalResult = bettingResult.calculatePlayersBettingResult().values().stream()
			.map(Profit::calculateResult)
			.collect(Collectors.toList());
		assertThat(totalResult).containsSequence(3500, -3000, 1500, -2000);
	}

	private Player getBustedPlayer() {
		return new Player(new Cards(
			List.of(new Card(CardDenomination.TEN, CardSuit.SPADE), new Card(CardDenomination.TEN, CardSuit.DIAMOND),
				new Card(CardDenomination.TWO, CardSuit.HEART))), new BettingToken(3000), new Name("pobi"));
	}

	private Player getBlackJackedPlayer() {
		return new Player(new Cards(
			List.of(new Card(CardDenomination.TEN, CardSuit.HEART), new Card(CardDenomination.ACE, CardSuit.DIAMOND))),
			new BettingToken(1000), new Name("jason"));
	}

	private Player getNormalPlayer() {
		return new Player(new Cards(
			List.of(new Card(CardDenomination.SIX, CardSuit.HEART), new Card(CardDenomination.FIVE, CardSuit.DIAMOND))),
			new BettingToken(2000), new Name("alpha"));
	}
}