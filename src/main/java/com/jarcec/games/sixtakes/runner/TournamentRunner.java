package com.jarcec.games.sixtakes.runner;

import com.jarcec.games.sixtakes.brain.HighestCardBrain;
import com.jarcec.games.sixtakes.brain.LowestCardBrain;
import com.jarcec.games.sixtakes.brain.RandomBrain;
import com.jarcec.games.sixtakes.display.Histogram;
import com.jarcec.games.sixtakes.engine.Player;
import com.jarcec.games.sixtakes.engine.Statistics;
import com.jarcec.games.sixtakes.engine.Tournament;

import java.util.HashSet;
import java.util.List;

public class TournamentRunner {
  public static void main(String[] args) throws Exception {
    System.out.println("Welcome to SixTakes Tournament!");

    // Players
    List<Player> players = List.of(
      new Player("A", new LowestCardBrain()),
      new Player("B", new HighestCardBrain()),
      new Player("C", new RandomBrain()),
      new Player("D", new RandomBrain()),
      new Player("E", new RandomBrain()),
      new Player("F", new RandomBrain())
    );

    // Tournament
    Tournament tournament = new Tournament(new HashSet<>(players));
    Statistics statistics = tournament.playTournament(500);

    // Print tournament results
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
