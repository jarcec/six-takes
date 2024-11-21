package com.jarcec.games.sixtakes.engine;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Log4j2
public class Tournament {
  private final Set<Player> players;
  @Getter final List<RoundHistory> roundHistories;

  public Tournament(Set<Player> players) {
    this.players = players;
    this.roundHistories = new ArrayList<>();
  }

  public Statistics playTournament(int games) {
    Statistics statistics = new Statistics();

    for(int i = 0; i < games; i++) {
      log.info("Starting tournament game {}", i);
      Game game = new Game(players);
      statistics.addScore(game.playGame());
      roundHistories.addAll(game.getRoundHistories());
    }

    return statistics;
  }

}
