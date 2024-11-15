package com.jarcec.games.sixtakes.brain;

import com.jarcec.games.sixtakes.engine.*;

import java.util.List;

public interface Brain {

  /**
   * Called upon game start to let Brain initialize anything that needs to be initialized.
   */
  default void startGame() {
  }

  /**
   * Called on every turn for brain to prepare if needed be.
   * @param table State of the table at the begging of the turn
   * @param round 1-N, depending on number of rounds the game needs
   * @param turn 1-10, turn number within the round
   */
  default void startRoundAndTurn(Table table, int round, int turn) {
  }

  /**
   * Select a card from hand that should be played this turn
   * @param hand Hand of the current player
   * @param table Table of the game
   * @return Card to play this turn
   */
  Card selectCard(Hand hand, Table table);

  /**
   * List of all selected cards for this turn across all players.
   * @param selectedCards All selected cards this turn
   */
  default void selectedCards(List<SelectedCard> selectedCards) {
  }

  /**
   * Select a pile to replace with your card
   * @param table Table of the game
   * @param card Card that was selected for this turn
   * @return Pile from the table that should be replaced.
   */
  Pile selectPile(Table table, Card card);

  /**
   * Called after a game is over with final score.
   * @param score Final score of the game
   */
  default void finishGame(Score score) {
  }
}
