package com.jarcec.games.sixtakes.brain.dl4j;

import com.jarcec.games.sixtakes.brain.Brain;
import com.jarcec.games.sixtakes.engine.Card;
import com.jarcec.games.sixtakes.engine.Hand;
import com.jarcec.games.sixtakes.engine.Pile;
import com.jarcec.games.sixtakes.engine.Table;

public class MercuryBrain implements Brain {
  @Override
  public Card selectCard(Hand hand, Table table) {
    return null;
  }

  @Override
  public Pile selectPile(Table table, Card card) {
    return null;
  }
}
