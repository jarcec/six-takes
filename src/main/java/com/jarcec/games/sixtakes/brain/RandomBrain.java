package com.jarcec.games.sixtakes.brain;

import com.jarcec.games.sixtakes.engine.Hand;
import com.jarcec.games.sixtakes.engine.Table;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class RandomBrain implements Brain {
  @Override
  public Selection makeSelection(Hand hand, Table table) {
    return new Brain.Selection(
      new ArrayList<>(hand.getCards()).get(ThreadLocalRandom.current().nextInt(hand.getCards().size())),
      table.getPiles().get(ThreadLocalRandom.current().nextInt(4))
    );
  }
}
