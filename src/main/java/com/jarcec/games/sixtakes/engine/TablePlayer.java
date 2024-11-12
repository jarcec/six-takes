package com.jarcec.games.sixtakes.engine;

import lombok.Getter;

public class TablePlayer {
  @Getter private final Player player;
  @Getter private final Hand hand;
  @Getter private final Discard discard;

  public TablePlayer(Player player, Deck deck) {
    this.player = player;
    this.hand = new Hand(deck);
    this.discard = new Discard();
  }
}
