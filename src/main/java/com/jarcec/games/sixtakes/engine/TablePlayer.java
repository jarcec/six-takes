package com.jarcec.games.sixtakes.engine;

import java.util.ArrayList;
import java.util.List;

public record TablePlayer(Player player, Hand hand, List<Card> points) {
  public TablePlayer(Player player, Deck deck) {
    this(player, new Hand(deck), new ArrayList<>());
  }
}
