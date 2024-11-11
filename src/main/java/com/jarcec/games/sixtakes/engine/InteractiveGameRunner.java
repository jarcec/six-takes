package com.jarcec.games.sixtakes.engine;

import com.jarcec.games.sixtakes.brain.InteractiveBrain;
import com.jarcec.games.sixtakes.brain.RandomBrain;

import java.util.Set;

public class InteractiveGameRunner {
  public static void main(String[] args) throws Exception {
    System.out.println("Welcome to SixTakes!");

    Game game = new Game(Set.of(
      new Player("Human", new InteractiveBrain()),
      new Player("A", new RandomBrain()),
      new Player("B", new RandomBrain()),
      new Player("C", new RandomBrain()),
      new Player("D", new RandomBrain())
    ));

    game.playGame();
  }
}
