package com.jarcec.games.sixtakes.brain;

import com.jarcec.games.sixtakes.engine.Card;
import com.jarcec.games.sixtakes.engine.Hand;
import com.jarcec.games.sixtakes.engine.Pile;
import com.jarcec.games.sixtakes.engine.Table;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class InteractiveBrain implements Brain {
  private final BufferedReader reader;

  public InteractiveBrain() {
    this.reader = new BufferedReader(new InputStreamReader(System.in));
  }

  @Override
  public Card selectCard(Hand hand, Table table) {
    displayTable(table);

    // Display user's hand
    List<String> cards = hand.getCards().stream()
      .sorted()
      .map(c -> c.id() + "(" + c.getPoints() + ")")
      .toList();
    System.out.println("Your hand: " + String.join(", ", cards));

    // Select a card
    return readCard(hand);
  }

  @Override
  public Pile selectPile(Table table) {
    displayTable(table);
    return readPile(table);
  }

  public Card readCard(Hand hand) {
    while(true) {
      try {
        System.out.print("Choose card: ");
        String input = reader.readLine();
        Card card = new Card(Integer.parseInt(input));

        if(hand.getCards().contains(card)) {
          return card;
        }

      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public Pile readPile(Table table) {
    while(true) {
      try {
        System.out.print("Choose pile: ");
        String input = reader.readLine();
        int pileId = Integer.parseInt(input);

        if(1 <= pileId && pileId <= 4) {
          return table.getPile(pileId);
        }

      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public void displayTable(Table table) {
    System.out.println("Current state of the table: " );
    for(Pile pile : table.getPiles()) {
      System.out.print("Pile");
      for(Card card: pile.getCards()) {
        System.out.print(" > ");
        System.out.print(card);
      }
      System.out.println();
    }
  }
}
