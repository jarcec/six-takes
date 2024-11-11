package com.jarcec.games.sixtakes.engine;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Score {
  private final Map<Player, Integer> points = new HashMap<>();

  public void addPoints(Player player, int points) {
    if(!this.points.containsKey(player)) {
      this.points.put(player, 0);
    }

    this.points.put(player, this.points.get(player) + points);
  }

  public void merge(Score score) {
    for(Map.Entry<Player, Integer> entry : this.points.entrySet()) {
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

    return points.entrySet().stream()
      .max(Comparator.comparingInt(Map.Entry::getValue))
      .map(Map.Entry::getKey)
      ;
  }
}
