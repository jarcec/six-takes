package com.jarcec.games.sixtakes.engine;

import lombok.Getter;

public class SelectedCard {
  @Getter private final Card card;
  @Getter private final TablePlayer roundPlayer;

  public SelectedCard(Card card, TablePlayer roundPlayer) {
    this.card = card;
    this.roundPlayer = roundPlayer;
  }

  @Override
  public String toString() {
    return String.valueOf(card.id());
  }
}
