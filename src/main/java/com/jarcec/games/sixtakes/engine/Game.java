package com.jarcec.games.sixtakes.engine;

import java.util.Set;

public class Game {
  private final Set<Player> players;

  public Game(Set<Player> players) {
    this.players = players;
  }

  public void playGame() {
    Score score = new Score();

    while(!score.isGameOver()) {
      Round round = new Round(players);
      round.playRound();
    }
  }
}
