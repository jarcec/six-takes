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
    piles.add(new Pile(0, deck));
    piles.add(new Pile(1, deck));
    piles.add(new Pile(2, deck));
    piles.add(new Pile(3, deck));

    // 3) Ready player hands and point piles
    this.players = new HashSet<>();
    for(Player player : players) {
      this.players.add(new TablePlayer(player, deck));
    }
  }

  public Pile getLowestPointPile() {
    Pile lowestPile = piles.get(0);

    for(Pile pile : piles) {
      if(pile.getPoints() < lowestPile.getPoints()) {
        lowestPile = pile;
      }
    }

    return lowestPile;
  }
}
