package com.jarcec.games.sixtakes.runner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jarcec.games.sixtakes.brain.HighestCardBrain;
import com.jarcec.games.sixtakes.brain.LowestCardBrain;
import com.jarcec.games.sixtakes.brain.RandomBrain;
import com.jarcec.games.sixtakes.display.Histogram;
import com.jarcec.games.sixtakes.engine.Player;
import com.jarcec.games.sixtakes.engine.RoundHistory;
import com.jarcec.games.sixtakes.engine.Statistics;
import com.jarcec.games.sixtakes.engine.Tournament;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

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
    for (Player player : players) {
      Histogram rankHistogram = new Histogram(1);
      Histogram pointsHistogram = new Histogram(10);
      for (Statistics.Result result : statistics.getResults().get(player)) {
        rankHistogram.add(result.getRank());
        pointsHistogram.add(result.getPoints());
      }

      System.out.println("Player: " + player.getName() + " (" + player.getBrain().getClass().getSimpleName() + ")");
      System.out.println("Rank:");
      rankHistogram.displayStdout();
      System.out.println("Points:");
      pointsHistogram.displayStdout();

      System.out.println();
    }

    // Serialize history (as json)
    Path jsonPath = Paths.get("/Users/jarcec/projects/six-takes/round-histories.json");
    try (BufferedWriter writer = Files.newBufferedWriter(jsonPath, StandardCharsets.UTF_8)) {
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      for (RoundHistory history : tournament.getRoundHistories()) {
        writer.write(gson.toJson(history));
        writer.newLine();
      }
    }

    // Serialize history for AI model training
    Path csvPath = Paths.get("/Users/jarcec/projects/six-takes/round-histories-training.csv");
    try (BufferedWriter writer = Files.newBufferedWriter(csvPath, StandardCharsets.UTF_8)) {
      for (RoundHistory history : tournament.getRoundHistories()) {
        // We will follow path of the winning player
        String winnerId = getWinnerId(history);
        List<Integer> hand = new ArrayList<>(history.getHands().get(winnerId).stream().sorted().toList());

        // History of played cards
        List<Integer> playedcards = serializeTable(history.getTurns().get(0).getTable());

        // And then for each turn
        for(RoundHistory.History.Turn turn : history.getTurns()) {
          RoundHistory.History.Selection selection = turn.getSelections().get(winnerId);

          // Write down the turn
          writeTurn(
            writer,
            serializeHand(hand),
            serializeTable(turn.getTable()),
            playedcards,
            serializeSelectedCard(hand, selection),
            serializeSelectedPile(selection)
          );

          // Update for next turn
          hand.remove(Integer.valueOf(selection.getCard()));
          for(RoundHistory.History.Selection s: turn.getSelections().values()) {
            playedcards.set(s.getCard() - 1, 1);
          }
        }
      }
    }
  }

  private static void writeTurn(
    BufferedWriter writer,
    List<Integer> hand,
    List<Integer> piles,
    List<Integer> playedCards,
    List<Integer> selectedCard,
    List<Integer> selectedPile
  ) throws IOException {
    writer.write(
      hand.stream().map(Object::toString).collect(Collectors.joining(","))
      + ","
      + piles.stream().map(Object::toString).collect(Collectors.joining(","))
      + ","
      + playedCards.stream().map(Object::toString).collect(Collectors.joining(","))
      + ","
      + selectedCard.stream().map(Object::toString).collect(Collectors.joining(","))
      + ","
      + selectedPile.stream().map(Object::toString).collect(Collectors.joining(","))
    );
    writer.newLine();
  }

  private static List<Integer> serializeTable(RoundHistory.History.Table table) {
    List<Integer> piles = new ArrayList<>(Collections.nCopies(104*4, 0));

    for(int i = 0; i < 4; i++) {
      for(int cardId : table.getPiles().get(i)) {
        piles.set((cardId - 1) + (i * 104), 1);
      }
    }

    return piles;
  }

  private static void updatePlayedCards(List<Integer> vector, List<Integer> cards) {
    for(int cardId : cards) {
      vector.set((cardId - 1), 1);
    }
  }

  private static List<Integer> serializeHand(List<Integer> cards) {
    List<Integer> vector = new ArrayList<>(Collections.nCopies(104, 0));
    updatePlayedCards(vector, cards);
    return vector;
  }

  private static String getWinnerId(RoundHistory history) {
    int minPoints = 10_000; // Random high number
    String winnerId = null;

    for(Map.Entry<String, Integer> e : history.getScore().entrySet()) {
      if(e.getValue() < minPoints) {
        minPoints = e.getValue();
        winnerId = e.getKey();
      }
    }

    return winnerId;
  }

  private static List<Integer> serializeSelectedCard(List<Integer> hand, RoundHistory.History.Selection selection) {
    int index = hand.indexOf(selection.getCard());

    List<Integer> vector = new ArrayList<>(Collections.nCopies(10, 0));
    vector.set(index, 1);
    return vector;
  }

  private static List<Integer> serializeSelectedPile(RoundHistory.History.Selection selection) {
    List<Integer> vector = new ArrayList<>(Collections.nCopies(4, 0));
    vector.set(selection.getPile(), 1);
    return vector;
  }
}
