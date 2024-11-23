package com.jarcec.games.sixtakes.runner;

import com.jarcec.games.sixtakes.brain.InteractiveBrain;
import com.jarcec.games.sixtakes.brain.RandomBrain;
import com.jarcec.games.sixtakes.brain.dl4j.Dl4jBrain;
import com.jarcec.games.sixtakes.engine.Game;
import com.jarcec.games.sixtakes.engine.Player;

import java.util.Set;

public class GameRunner {
  public static void main(String[] args) throws Exception {
    System.out.println("Welcome to SixTakes!");

    Game game = new Game(Set.of(
//      new Player("Human", new InteractiveBrain()),
      new Player("Mercury", new Dl4jBrain("/Users/jarcec/projects/six-takes/mercury.model")),
      new Player("A", new RandomBrain()),
      new Player("B", new RandomBrain()),
      new Player("C", new RandomBrain()),
      new Player("D", new RandomBrain())
    ));

    game.playGame();
  }
}
