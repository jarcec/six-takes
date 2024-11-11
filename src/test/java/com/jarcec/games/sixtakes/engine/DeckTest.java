package com.jarcec.games.sixtakes.engine;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DeckTest {

  @Test
  public void testDeck() {
    Deck deck = new Deck();
    assertEquals(104, deck.getCardCount());

    Card card = deck.drawCard();
    assertEquals(103, deck.getCardCount());
    assertNotNull(card);

    List<Card> cards = deck.drawCards(10);
    assertEquals(93, deck.getCardCount());
    assertNotNull(cards);
    assertEquals(10, cards.size());
  }
}
