package com.jarcec.games.sixtakes.engine;

import lombok.Getter;

import java.util.*;

@Getter
public class Table {
  private final List<Pile> piles;
  private final Set<TablePlayer> players;

  public Table(Set<Player> players) {
    // 1) Create a new deck
    Deck deck = new Deck();

    // 2) Create the four main piles
    piles = new ArrayList<>(4);
    piles.add(new Pile(deck));
    piles.add(new Pile(deck));
    piles.add(new Pile(deck));
    piles.add(new Pile(deck));

    // 3) Ready player hands and point piles
    this.players = new HashSet<>();
    for(Player player : players) {
      this.players.add(new TablePlayer(player, deck));
    }
  }

  public void playCard(SelectedCard selectedCard) {
    // Firstly select pile for this card
    Optional<Pile> activePile = Optional.empty();
    int minDifference = 1000; // Randomly high number

    for(Pile pile : piles) {
      Optional<Integer> currentDifference = pile.difference(selectedCard.getCard());
      if(currentDifference.isPresent()) {
        if(currentDifference.get() < minDifference) {
          minDifference = currentDifference.get();
          activePile = Optional.of(pile);
        }
      }
    }

    if(activePile.isPresent()) {
      selectedCard.getRoundPlayer().getDiscard().add(activePile.get().addCard(selectedCard.getCard()));
    } else {
      Pile pile = selectedCard.getRoundPlayer().getPlayer().getBrain().selectPile(this);
      selectedCard.getRoundPlayer().getDiscard().add(pile.replace(selectedCard.getCard()));
    }

    selectedCard.finishTurn();
  }
}
