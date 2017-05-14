package com.appian.test.cards;

import com.appian.cards.Card;
import com.appian.cards.Deck;
import junit.framework.TestCase;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class DeckTest extends TestCase {

	private enum TestSuit {
		CUPS,
		SWORDS,
		COINS
	}

	private enum TestRank {
		SEVEN,
		EIGHT,
		NINE,
		TEN,
		UNTER,
		OBER,
		KONIG,
		DAUS
	}

	private <T extends Collection<Card>> T dealOutAllCards(T hand, Deck deck) {
		Card dealtCard;
		while ((dealtCard = deck.dealOneCard()) != null) {
			hand.add(dealtCard);
		}
		return hand;
	}

	/**
	 * Tests the cardinality and contents of a deck created with custom suits and ranks.
	 */
	public void testCreate() {
		Deck deck = new Deck(TestSuit.class, TestRank.class);

		int correctSize = TestSuit.values().length * TestRank.values().length;

		// Test that deck size is correct
		assertEquals(correctSize, deck.size());

		// Test that deck contents are correct
		HashSet<Card> hand = dealOutAllCards(new HashSet<>(), deck);

		Map<Enum, Long> suitGrouping = hand.stream().collect(Collectors.groupingBy(Card::getSuit, Collectors.counting()));
		assertEquals(TestSuit.values().length, suitGrouping.size());
		assertTrue(suitGrouping.values().stream().allMatch(count -> count == TestRank.values().length));

		Map<Enum, Long> rankGrouping = hand.stream().collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));
		assertEquals(TestRank.values().length, rankGrouping.size());
		assertTrue(rankGrouping.values().stream().allMatch(count -> count == TestSuit.values().length));
	}

	/**
	 * Tests that the default deck is the 52-card deck used in American card games.
	 */
	public void testStandardDeck() {
		Deck deck = new Deck();
		assertEquals(52, deck.size());
	}

	/**
	 * Test that shuffling works. Note: we don't test the quality of the shuffle, only that the permutation is different from the original.
	 */
	public void testShuffle() throws Exception {

		// Test if the shuffler consumer is invoked; making shufflerCalled a list is a hackish way around Java's lack of variable closures
		List<Boolean> shufflerCalled = new ArrayList<>();
		Consumer<List<Card>> noopShuffler = (List<Card> cards) -> shufflerCalled.add(true);

		Deck deck1 = new Deck(TestSuit.class, TestRank.class);
		Deck deck2 = new Deck(TestSuit.class, TestRank.class);
		Deck deck3 = new Deck(TestSuit.class, TestRank.class);
		Deck deck4 = new Deck(TestSuit.class, TestRank.class, noopShuffler);

		deck3.shuffle();
		deck4.shuffle();

		List<Card> hand2 = dealOutAllCards(new ArrayList<>(), deck2);
		List<Card> hand3 = dealOutAllCards(new ArrayList<>(), deck3);
		List<Card> hand4 = dealOutAllCards(new ArrayList<>(), deck4);
		List<Card> hand1 = dealOutAllCards(new ArrayList<>(), deck1);

		// Deck creation is deterministic
		assertTrue(hand1.equals(hand2));

		// Shuffling works...
		assertFalse(hand1.equals(hand3));

		// ...unless it's no-op shuffling, but that at least gets called
		assertTrue(hand1.equals(hand4));
		assertTrue(shufflerCalled.get(0));

		// Dump for visual sanity check
		for (Card card : hand3) {
			System.out.println(card.toString());
		}
	}

	/**
	 * Tests that dealOneCard() will eventually result in the entire deck being dealt out
	 */
	public void testDealOneCard() throws Exception {
		Deck deck = new Deck(TestSuit.class, TestRank.class);

		int deckSize = deck.size();

		// Test dealing
		HashSet<Card> hand = dealOutAllCards(new HashSet<>(), deck);

		assertNull(deck.dealOneCard());

		// Test that all cards are dealt and that dealt cards are distinct
		assertEquals(deckSize, hand.size());
		assertEquals(0, deck.size());
	}
}
