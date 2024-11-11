package com.jarcec.games.sixtakes.brain;

import com.jarcec.games.sixtakes.engine.Card;
import com.jarcec.games.sixtakes.engine.Hand;
import com.jarcec.games.sixtakes.engine.Pile;
import com.jarcec.games.sixtakes.engine.Table;

public interface Brain {
  Card selectCard(Hand hand, Table table);
  Pile selectPile(Table table);
}
