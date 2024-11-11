package com.jarcec.games.sixtakes.engine;

import java.util.*;
import java.util.stream.Collectors;

public class Score {
  private final Map<Player, Integer> points = new HashMap<>();

  public void addPoints(Player player, int points) {
    if(!this.points.containsKey(player)) {
      this.points.put(player, 0);
    }

    this.points.put(player, this.points.get(player) + points);
  }

  public void merge(Score score) {
    for(Map.Entry<Player, Integer> entry : score.points.entrySet()) {
      addPoints(entry.getKey(), entry.getValue());
    }
  }

  public boolean isGameOver() {
    return points.values().stream().max(Integer::compareTo).orElse(0) >= 66;
  }

  public Optional<Player> getWinner() {
    if(!isGameOver()) {
      return Optional.empty();
    }

    return Optional.of(points.entrySet().stream()
      .sorted(Map.Entry.comparingByValue())
      .findFirst()
      .get()
      .getKey()
    );
  }

  @Override
  public String toString() {
    return points.entrySet().stream()
      .map(e -> e.getKey().name() + "=" + e.getValue())
      .collect(Collectors.joining(",", "Score(", ")"));
  }
}
