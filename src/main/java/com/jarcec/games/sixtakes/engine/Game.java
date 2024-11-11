package com.jarcec.games.sixtakes.engine;

import lombok.extern.log4j.Log4j2;

import java.util.Set;

@Log4j2
public class Game {
  private final Set<Player> players;

  public Game(Set<Player> players) {
    this.players = players;
  }

  public void playGame() {
    log.info("Starting a new game");
    Score score = new Score();

    while(!score.isGameOver()) {
      log.info("Starting a round");
      Round round = new Round(players);
      score.merge(round.playRound());
      log.info("Current score: {}", score);
    }

    log.info("Game over; winner {}", score.getWinner().get().name());
  }
}
