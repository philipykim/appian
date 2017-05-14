package com.appian.cards;

import com.appian.util.ListUtils;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * A class representing a deck of cards.
 */
public class Deck {
	private final List<Card> _cards;
	private final Consumer<List<Card>> _shuffler;

	/**
	 * This default constructor creates the 52-card deck used in American card games.
	 */
	public Deck() {
		this(FrenchSuit.class, FrenchRank.class);
	}

	/**
	 * Creates a card deck with the given suits and ranks, using the default Fisher-Yates shuffling function.
	 */
	public <TSuit extends Enum<TSuit>, TRank extends Enum<TRank>> Deck(Class<TSuit> suits, Class<TRank> ranks) {
		this(suits, ranks, ListUtils::shuffle);
	}

	/**
	 * Creates a card deck that is the Cartesian product of the given suits and ranks.
	 *
	 * @param suits    The class of an Enum type that will be used to generate the card suits.
	 * @param ranks    The class of an Enum type that will be used to generate the card ranks.
	 * @param shuffler The function that will be used to shuffle the cards.
	 */
	public <TSuit extends Enum<TSuit>, TRank extends Enum<TRank>> Deck(Class<TSuit> suits, Class<TRank> ranks, Consumer<List<Card>> shuffler) {
		_cards = Arrays.stream(suits.getEnumConstants())
				.flatMap(suit -> Arrays.stream(ranks.getEnumConstants()).map(rank -> new Card(suit, rank)))
				.collect(Collectors.toList());
		_shuffler = shuffler;
	}

	public void shuffle() {
		_shuffler.accept(_cards);
	}

	public Card dealOneCard() {
		if (_cards.size() == 0)
			return null;

		Card card = _cards.get(_cards.size() - 1);
		_cards.remove(_cards.size() - 1);
		return card;
	}

	public int size() {
		return _cards.size();
	}
}
