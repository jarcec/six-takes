package com.jarcec.games.sixtakes.engine;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class Score {
  private final Map<Player, Integer> points;

  public Score() {
    this.points = new HashMap<>();
  }

  public Score(Set<TablePlayer> players) {
    this();

    for(TablePlayer player : players) {
      addPoints(player.getPlayer(), player.getDiscard().points());
    }
  }

  private void addPoints(Player player, int points) {
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
      .map(e -> e.getKey().getName() + "=" + e.getValue())
      .collect(Collectors.joining(",", "Score(", ")"));
  }
}
