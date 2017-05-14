package com.appian.cards;

/**
 * An immutable class representing a playing card with a Suit and a Rank.
 * Two cards with the same suit and rank are equals().
 */
public class Card {
	private Enum _suit;
	private Enum _rank;

	public Card(Enum suit, Enum rank) {
		_suit = suit;
		_rank = rank;
	}

	public Enum getSuit() {
		return _suit;
	}

	public Enum getRank() {
		return _rank;
	}

	@Override
	public String toString() {
		return getRank() + " of " + getSuit();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Card card = (Card) o;
		return _suit.equals(card._suit) && _rank.equals(card._rank);
	}

	@Override
	public int hashCode() {
		int result = _suit.hashCode();
		result = 31 * result + _rank.hashCode();
		return result;
	}
}
