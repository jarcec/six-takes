package com.jarcec.games.sixtakes.engine;

import lombok.Getter;

import java.util.Optional;

@Getter
public class Selection implements Comparable<Selection> {
  private final Card card;
  private final Pile pile;
  private final TablePlayer roundPlayer;

  public Selection(Card card, Pile pile, TablePlayer roundPlayer) {
    this.card = card;
    this.pile = pile;
    this.roundPlayer = roundPlayer;
  }

  public void addCardToTable(Table table) {
    // 1) Select the right pile to add this card to
    Optional<Pile> activePile = Optional.empty();
    int minDifference = 1000; // Randomly high number

    for(Pile pile : table.getPiles()) {
      Optional<Integer> currentDifference = pile.difference(card);
      if(currentDifference.isPresent()) {
        if(currentDifference.get() < minDifference) {
          minDifference = currentDifference.get();
          activePile = Optional.of(pile);
        }
      }
    }

    // 2) Add the card to the right pile (or replace if needed be
    if(activePile.isPresent()) {
      roundPlayer.getDiscard().add(activePile.get().addCard(card));
    } else {
      roundPlayer.getDiscard().add(pile.replace(card));
    }

    // 3) Finish turn by removing the card from player's hand
    roundPlayer.getHand().removeCard(card);
  }

  @Override
  public String toString() {
    return String.valueOf(card.getId());
  }

  @Override
  public int compareTo(Selection o) {
    return card.compareTo(o.card);
  }
}
