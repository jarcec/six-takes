package com.jarcec.games.sixtakes.engine;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class TablePlayer {
  @Getter private final Player player;
  @Getter private final Hand hand;
  @Getter private final List<Card> points;

  public TablePlayer(Player player, Deck deck) {
    this.player = player;
    this.hand = new Hand(deck);
    this.points = new ArrayList<>();
  }
}
