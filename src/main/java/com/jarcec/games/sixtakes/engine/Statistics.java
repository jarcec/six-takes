package com.jarcec.games.sixtakes.engine;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

@Getter
public class Statistics {
  @Getter @AllArgsConstructor
  public static final class Result {
    private final int rank;
    private final int points;
  }

  private final Map<Player, List<Result>> results = new HashMap<>();

  public void addScore(Score score) {
    // Make sure that all players indeed exists in internal structures
    for(Player player : score.getPoints().keySet()) {
      if(!results.containsKey(player)) {
        results.put(player, new ArrayList<>());
      }
    }

    // Rank the players by score
    List<Map.Entry<Player, Integer>> rankedOrder = score.getPoints().entrySet().stream()
      .sorted(Map.Entry.comparingByValue())
      .toList();

    int rank = 0;
    for(Map.Entry<Player, Integer> entry : rankedOrder) {
      results.get(entry.getKey()).add(new Result(rank, entry.getValue()));
      rank++;
    }
  }
}
