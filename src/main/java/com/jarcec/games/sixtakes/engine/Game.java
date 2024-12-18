package com.jarcec.games.sixtakes.engine;

import com.jarcec.games.sixtakes.brain.Brain;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2
public class Game {
  private final Set<Player> players;
  @Getter private final List<RoundHistory> roundHistories;

  public Game(Set<Player> players) {
    this.players = players;
    this.roundHistories = new ArrayList<>();
  }

  public Score playGame() {
    log.info("Starting a new game");

    // Announce start of the game to all players
    for(Player player : players) {
      player.getBrain().startGame();
    }

    // Play as many rounds as needed
    int roundNumber = 0;
    Score score = new Score();
    while(!score.isGameOver()) {
      roundNumber++;
      score.merge(playRound(roundNumber));
      log.info("Score at the end of round {}", roundNumber);
      for(Map.Entry<Player, Integer> entry : score.getPoints().entrySet()) {
        log.info("Player {}: {} points", entry.getKey().getName(), entry.getValue());
      }
    }

    for(Player player : players) {
      player.getBrain().finishGame(score);
    }
    log.info("Game over; winner {}", score.getWinner().get().getName());
    return score;
  }

  private Score playRound(int roundNumber) {
    // New table for this round
    Table table = new Table(players);

    // Calculation of round history for learning algorithms
    RoundHistory history = new RoundHistory(table);

    // Play all 10 cards in the round
    for(int turnNumber = 1; turnNumber <= 10; turnNumber++) {
      // 1) Announce this round and turn
      log.info("Starting round {} and turn {}", roundNumber, turnNumber);
      for(Player player : players) {
        player.getBrain().startRoundAndTurn(table, roundNumber, turnNumber);
      }
      for(Pile pile : table.getPiles()) {
        log.info("Pile: {}", String.join(" > ", pile.getCards().stream().map(Card::toString).toList()));
      }

      // 2) Players need to chose cards
      List<Selection> selections = new ArrayList<>();
      for(TablePlayer roundPlayer : table.getPlayers()) {
        Brain.Selection selection = roundPlayer.getPlayer().getBrain().makeSelection(roundPlayer.getHand(), table);
        selections.add(new Selection(selection.getCard(), selection.getPile(), roundPlayer));
      }

      // Intermezzo: Validate that all selections are actually valid
      for(Selection selection : selections) {
        assert selection != null;
        assert selection.getPile() != null;
        assert selection.getCard() != null;
        assert selection.getRoundPlayer().getHand().getCards().contains(selection.getCard());
      }

      // 3) Sort order of the cards
      Collections.sort(selections);
      log.info("Selected cards for this turn: {}", selections.stream()
        .map(s -> s.getRoundPlayer().getPlayer().getName() + " (" + s.getCard().getId() + "; " + s.getPile().getPileIndex() + ")")
        .collect(Collectors.joining(", "))
      );
      for(Player player : players) {
        player.getBrain().selectedCards(selections);
      }

      // Intermezzo: Persist history of the game
      history.createAndAddTurn(table, selections);

      // 4) Add cards to the table
      for(Selection selection : selections) {
        selection.addCardToTable(table);
      }

      // Intermezzo: Validate that all hands are as expected
      for(TablePlayer roundPlayer : table.getPlayers()) {
        assert roundPlayer.getHand().getCards().size() == 10 - turnNumber;
      }
    }

    // Generate and persist score
    Score score = new Score(table.getPlayers());
    history.setFinalScore(score);

    // Ensure that this round history is persisted
    roundHistories.add(history);

    // And we're done, return score
    return score;
  }
}
