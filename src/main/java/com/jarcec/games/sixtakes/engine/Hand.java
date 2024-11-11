package com.jarcec.games.sixtakes.engine;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Hand {
  private final Set<Card> cards;

  public Hand(Deck deck) {
    cards = new HashSet<>(deck.drawCards(10));
  }

  public Set<Card> getCards() {
    return Collections.unmodifiableSet(cards);
  }

  public void removeCard(Card card) {
    cards.remove(card);
  }
}
