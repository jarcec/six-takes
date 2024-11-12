package com.jarcec.games.sixtakes.engine;

import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Log4j2
public class Game {
  private final Set<Player> players;

  public Game(Set<Player> players) {
    this.players = players;
  }

  public void playGame() {
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
      log.info("Starting a round {}", roundNumber);
      score.merge(playRound(roundNumber));
    }

    for(Player player : players) {
      player.getBrain().finishGame(score);
    }
    log.info("Game over; winner {}", score.getWinner().get().getName());
  }

  private Score playRound(int roundNumber) {
    // New table for this round
    Table table = new Table(players);

    // Play all 10 cards in the round
    for(int turnNumber = 1; turnNumber <= 10; turnNumber++) {
      // 1) Announce this round and turn
      log.info("Starting round {} and turn {}", roundNumber, turnNumber);
      for(Player player : players) {
        player.getBrain().startRoundAndTurn(table, roundNumber, turnNumber);
      }

      // 2) Players need to chose cards
      List<SelectedCard> selectedCards = new ArrayList<>();
      for(TablePlayer roundPlayer : table.getPlayers()) {
        selectedCards.add(
          new SelectedCard(
            roundPlayer.getPlayer().getBrain().selectCard(
            roundPlayer.getHand(),
            table
          ),
          roundPlayer));
      }
      for(Player player : players) {
        player.getBrain().selectedCards(selectedCards);
      }

      // 3) Sort order of the cards
      selectedCards.sort((a, b) -> a.getCard().id() < b.getCard().id() ? 1 : -1);
      log.info("Selected cards: {}", selectedCards);

      // 4) Add cards to the table
      for(SelectedCard selectedCard : selectedCards) {
        selectedCard.addCardToTable(table);
      }
      log.info("Table at the end of the round: {}", table);
    }

    return new Score(table.getPlayers());
  }
}
