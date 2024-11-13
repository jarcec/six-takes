package com.jarcec.games.sixtakes.brain;

import com.jarcec.games.sixtakes.engine.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InteractiveBrain implements Brain {
  private final BufferedReader reader;

  public InteractiveBrain() {
    this.reader = new BufferedReader(new InputStreamReader(System.in));
  }

  @Override
  public void startGame() {
    System.out.println("Welcome to a new game of SixTakes!");
  }

  @Override
  public void startRoundAndTurn(Table table, int round, int turn) {
    System.out.println("Round " + round + ", turn " + turn + " out of 10");
    int pileId = 0;
    for(Pile pile : table.getPiles()) {
      pileId++;
      System.out.print("Pile #" + pileId);
      for(Card card: pile.getCards()) {
        System.out.print(" > ");
        System.out.print(card);
      }
      System.out.println();
    }
  }

  @Override
  public Card selectCard(Hand hand, Table table) {
    // Display player's hand
    String cards = hand.getCards().stream()
      .sorted()
      .map(c -> c.getId() + "(" + c.getPoints() + ")")
      .collect(Collectors.joining(", "));
    System.out.println("Your hand (points): " + cards);

    // And let the user select which card he/she is interested in
    while(true) {
      try {
        System.out.print("Choose card: ");
        String input = reader.readLine();
        Card card = new Card(Integer.parseInt(input));

        if(hand.getCards().contains(card)) {
          return card;
        }

        System.out.println("Selected invalid card that is not in hand.");
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Override
  public void selectedCards(List<SelectedCard> selectedCards) {
    String cards = selectedCards.stream()
      .map(c -> c.getCard().getId() + "(" + c.getCard().getPoints() + ")")
      .collect(Collectors.joining(", "));
    System.out.println("Cards selected in this turn (points): " + cards);
  }

  @Override
  public Pile selectPile(Table table, Card card) {
    System.out.println("Your card " + card.getId() + " is lowest then any pile. Select a pile to take to your discard pile.");
    // Available piles
    int pileId = 0;
    for(Pile pile : table.getPiles()) {
      pileId++;
      System.out.print("Pile #" + pileId);
      for(Card pileCard: pile.getCards()) {
        System.out.print(" > ");
        System.out.print(pileCard);
      }
      System.out.println(" (Total points " + pile.getPoints()+ ")");
    }

    // And let the user select which pile (s)he is interested in
    while(true) {
      try {
        System.out.print("Choose pile: ");
        String input = reader.readLine();
        pileId = Integer.parseInt(input);

        if(1 <= pileId && pileId <= 4) {
          return table.getPiles().get(pileId - 1);
        }

        System.out.println("Selected invalid pile number.");
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Override
  public void finishGame(Score score) {
    System.out.println("Game over! Thank you for playing. Final score:");
    for(Map.Entry<Player, Integer> entry : score.getPoints().entrySet()) {
      System.out.println(entry.getKey().getName() + " : " + entry.getValue() + " points");
    }

  }
}
