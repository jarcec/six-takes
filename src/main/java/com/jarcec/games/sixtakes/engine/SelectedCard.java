package com.jarcec.games.sixtakes.engine;

public record SelectedCard(Card card, TablePlayer roundPlayer) {
  @Override
  public String toString() {
    return String.valueOf(card.id());
  }
}
