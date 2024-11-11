package com.jarcec.games.sixtakes.brain;

import com.jarcec.games.sixtakes.engine.Card;
import com.jarcec.games.sixtakes.engine.Hand;
import com.jarcec.games.sixtakes.engine.Pile;
import com.jarcec.games.sixtakes.engine.Table;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class RandomBrain implements Brain {
  @Override
  public Card selectCard(Hand hand, Table table) {
    return new ArrayList<>(hand.getCards()).get(ThreadLocalRandom.current().nextInt(hand.getCards().size()));
  }

  @Override
  public Pile selectPile(Table table) {
    return table.getPile(ThreadLocalRandom.current().nextInt(4));
  }
}
