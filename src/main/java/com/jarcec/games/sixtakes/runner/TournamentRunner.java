package com.jarcec.games.sixtakes.runner;

import com.jarcec.games.sixtakes.brain.RandomBrain;
import com.jarcec.games.sixtakes.engine.Player;
import com.jarcec.games.sixtakes.engine.Statistics;
import com.jarcec.games.sixtakes.engine.Tournament;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TournamentRunner {

  private final static class Histogram {
    private final List<String> labels;
    private final List<Integer> counts;

    Histogram(List<String> labels) {
      this.labels = labels;
      this.counts = new ArrayList<>(labels.size());
      for(String ignored : labels) {
        this.counts.add(0);
      }
    }

    public void add(int group) {
      this.counts.set(group, this.counts.get(group) + 1);
    }

    public void display() {
      for(int i = 0; i < labels.size(); i++) {
        System.out.println(labels.get(i) + ": " + "X".repeat(counts.get(i)) );
      }
    }
  }

  public static void main(String[] args) throws Exception {
    System.out.println("Welcome to SixTakes Tournament!");

    // Players
    Set<Player> players = Set.of(
      new Player("A", new RandomBrain()),
      new Player("B", new RandomBrain()),
      new Player("C", new RandomBrain()),
      new Player("D", new RandomBrain()),
      new Player("E", new RandomBrain()),
      new Player("F", new RandomBrain())
    );

    Tournament tournament = new Tournament(players);
    Statistics statistics = tournament.playTournament(500);

    for(Player player : players) {

      Histogram rankHistogram = new Histogram(List.of("1", "2", "3", "4", "5", "6"));
      Histogram pointsHistogram = new Histogram(List.of("00-10", "11-20", "21-30", "31-40", "41-50", "51-60", "61-70", "71-80", "81-90", "91-100", "101-110"));
      for(Statistics.Result result: statistics.getResults().get(player)) {
        rankHistogram.add(result.getRank());
        pointsHistogram.add(result.getPoints() / 10);
      }

      System.out.println("Player: " + player.getName() + " (" + player.getBrain().getClass().getSimpleName() + ")");
      System.out.println("Rank:");
      rankHistogram.display();
      System.out.println("Points:");
      pointsHistogram.display();

      System.out.println();
    }
  }
}
