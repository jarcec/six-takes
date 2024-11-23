package com.jarcec.games.sixtakes.brain;

import com.jarcec.games.sixtakes.engine.Card;
import com.jarcec.games.sixtakes.engine.Hand;
import com.jarcec.games.sixtakes.engine.Table;

public class LowestCardBrain implements Brain {
  @Override
  public Selection makeSelection(Hand hand, Table table) {
    return new Brain.Selection(
      hand.getCards().stream().reduce(new Card(104), (a, b) -> a.getId() < b.getId() ? a : b),
      table.getLowestPointPile()
    );
  }
}
