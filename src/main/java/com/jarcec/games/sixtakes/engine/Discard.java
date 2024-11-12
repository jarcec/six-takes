package com.jarcec.games.sixtakes.engine;

import java.util.ArrayList;
import java.util.List;

public class Discard {
  private final List<Card> cards;

  public Discard() {
    this.cards = new ArrayList<>();
  }

  public void add(List<Card> cards) {
    this.cards.addAll(cards);
  }

  public int points() {
    return this.cards.stream().mapToInt(Card::getPoints).sum();
  }
}
