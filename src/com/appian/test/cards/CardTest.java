package com.appian.test.cards;

import com.appian.cards.Card;
import com.appian.cards.FrenchRank;
import com.appian.cards.FrenchSuit;
import junit.framework.TestCase;

import java.util.HashSet;

public class CardTest extends TestCase {
	/**
	 * Tests construction and getters.
	 */
	public void testCreate() {
		Card card = new Card(FrenchSuit.SPADES, FrenchRank.ACE);
		assertEquals(FrenchSuit.SPADES, card.getSuit());
		assertEquals(FrenchRank.ACE, card.getRank());
	}

	/**
	 * Tests that the equals() and getHashCode() functions are correct.
	 */
	public void testEquals() {
		Card card1 = new Card(FrenchSuit.SPADES, FrenchRank.ACE);
		Card card2 = new Card(FrenchSuit.SPADES, FrenchRank.ACE);
		assertFalse(card1 == card2);
		assertEquals(card1, card2);

		HashSet<Card> cards = new HashSet<>();
		cards.add(card1);
		cards.add(card2);
		assertEquals(1, cards.size());
	}
}
