package com.jarcec.games.sixtakes.engine;

import lombok.Getter;

import java.util.*;

public class Table {
  @Getter private final List<Pile> piles;
  @Getter private final Set<TablePlayer> players;

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
    Optional<List<Card>> pointCards = selectPile(selectedCard);

    if(pointCards.isPresent()) {
      selectedCard.roundPlayer().points().addAll(pointCards.get());
    } else {
      Pile pile = selectedCard.roundPlayer().player().brain().selectPile(this);
      selectedCard.roundPlayer().points().addAll(pile.replace(selectedCard.card()));
    }

    selectedCard.roundPlayer().hand().removeCard(selectedCard.card());
  }

  private Optional<List<Card>> selectPile(SelectedCard selectedCard) {
    Optional<Pile> activePile = Optional.empty();
    int minDifference = 1000; // Randomly high number

    for(Pile pile : piles) {
      Optional<Integer> currentDifference = pile.difference(selectedCard.card());
      if(currentDifference.isPresent()) {
        if(currentDifference.get() < minDifference) {
          minDifference = currentDifference.get();
          activePile = Optional.of(pile);
        }
      }
    }

    return activePile.map(pile -> pile.addCard(selectedCard.card()));
  }
}
