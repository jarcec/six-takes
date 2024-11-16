package com.jarcec.games.sixtakes.brain;

import com.jarcec.games.sixtakes.engine.Card;
import com.jarcec.games.sixtakes.engine.Hand;
import com.jarcec.games.sixtakes.engine.Pile;
import com.jarcec.games.sixtakes.engine.Table;

import java.util.concurrent.ThreadLocalRandom;

public class LowestCardBrain implements Brain {
  @Override
  public Card selectCard(Hand hand, Table table) {
    return hand.getCards().stream()
      .reduce(new Card(104), (a, b) -> a.getId() < b.getId() ? a : b);
  }

  @Override
  public Pile selectPile(Table table, Card card) {
    return table.getPiles().get(ThreadLocalRandom.current().nextInt(4));
  }
}
