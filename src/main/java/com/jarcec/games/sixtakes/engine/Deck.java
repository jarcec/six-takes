package com.jarcec.games.sixtakes.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
  private List<Card> cards;

  public Deck() {
    this.cards = new ArrayList<>(104);
    for(int i = 1; i <= 104; i++) {
      cards.add(new Card(i));
    }
    Collections.shuffle(this.cards);
  }

  public int getCardCount() {
    return this.cards.size();
  }

  public Card drawCard() {
    return this.cards.remove(0);
  }

  public List<Card> drawCards(int count) {
    List<Card> result = this.cards.subList(0, count);
    this.cards = this.cards.subList(count, this.cards.size());
    return result;
  }
}
