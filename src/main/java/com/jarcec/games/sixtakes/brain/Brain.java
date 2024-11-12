package com.jarcec.games.sixtakes.brain;

import com.jarcec.games.sixtakes.engine.*;

public interface Brain {
  Card selectCard(Hand hand, Table table);
  Pile selectPile(Table table);
}
