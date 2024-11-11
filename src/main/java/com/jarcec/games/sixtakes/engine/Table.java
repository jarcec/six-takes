package com.jarcec.games.sixtakes.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Table {
  private List<Pile> piles;

  public Table(Deck deck) {
    piles = new ArrayList<>(4);
    piles.add(new Pile(deck));
    piles.add(new Pile(deck));
    piles.add(new Pile(deck));
    piles.add(new Pile(deck));
  }

  public Pile getPile(int index) {
    return piles.get(index);
  }

  public Optional<List<Card>> addCard(Card card) {
    Optional<Pile> activePile = Optional.empty();
    int minDifference = 1000; // Randomly high number

    for(Pile pile : piles) {
      Optional<Integer> currentDifference = pile.difference(card);
      if(currentDifference.isPresent()) {
        if(minDifference < currentDifference.get()) {
          minDifference = currentDifference.get();
          activePile = Optional.of(pile);
        }
      }
    }

    return activePile.map(pile -> pile.addCard(card));
  }
}