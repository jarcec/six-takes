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
    Score score = new Score();

    while(!score.isGameOver()) {
      log.info("Starting a round");
      score.merge(playRound());
      log.info("Current score: {}", score);
    }

    log.info("Game over; winner {}", score.getWinner().get().getName());
  }

  private Score playRound() {
    Table table = new Table(players);

    // Play all 10 cards in the round
    for(int roundNumber = 0; roundNumber < 10; roundNumber++) {
      // 1) Initialization
      log.info("Starting round {}", roundNumber);
      List<SelectedCard> selectedCards = new ArrayList<>();

      // 2) Players need to chose cards
      for(TablePlayer roundPlayer : table.getPlayers()) {
        selectedCards.add(
          new SelectedCard(
            roundPlayer.getPlayer().getBrain().selectCard(
            roundPlayer.getHand(),
            table
          ),
          roundPlayer));
      }

      // 3) Sort order of the cards
      selectedCards.sort((a, b) -> a.getCard().id() < b.getCard().id() ? 1 : -1);
      log.info("Selected cards: {}", selectedCards);

      // 4) Add cards to the table
      for(SelectedCard selectedCard : selectedCards) {
        table.playCard(selectedCard);
      }
      log.info("Table at the end of the round: {}", table);
    }

    Score score = new Score();
    for(TablePlayer roundPlayer : table.getPlayers()) {
      score.addPoints(
        roundPlayer.getPlayer(),
        roundPlayer.getPoints().stream().map(Card::getPoints).reduce(0, Integer::sum)
      );
    }
    return score;
  }
}
