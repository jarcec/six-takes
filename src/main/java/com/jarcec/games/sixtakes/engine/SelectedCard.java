package com.jarcec.games.sixtakes.engine;

import lombok.Getter;

@Getter
public class SelectedCard {
  private final Card card;
  private final TablePlayer roundPlayer;

  public SelectedCard(Card card, TablePlayer roundPlayer) {
    this.card = card;
    this.roundPlayer = roundPlayer;
  }

  public void finishTurn() {
    roundPlayer.getHand().removeCard(card);
  }

  @Override
  public String toString() {
    return String.valueOf(card.id());
  }
}
