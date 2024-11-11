package com.jarcec.games.sixtakes.engine;

import com.jarcec.games.sixtakes.brain.RandomBrain;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class GameTest {
  @Test
  public void runTestGame() {
    Player a = new Player("A", new RandomBrain());
    Player b = new Player("B", new RandomBrain());
    Player c = new Player("C", new RandomBrain());
    Player d = new Player("D", new RandomBrain());

    Game game = new Game(Set.of(a, b, c, d));
    game.playGame();
  }
}
