package com.jarcec.games.sixtakes.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PileTest {
  private Deck deck;

  @BeforeEach
  public void createDeck() {
    this.deck = new Deck();
  }

  @Test
  public void testNew() {
    Pile pile = new Pile(0, deck);
    assertEquals(1, pile.cardCount());
  }

  @Test
  public void testAddAndCurrent() {
    Pile pile = new Pile(0, deck);
    List<Card> points = pile.addCard(new Card(1));
    assertEquals(2, pile.cardCount());
    assertEquals(new Card(1), pile.currentCard());
    assertEquals(0, points.size());
  }

  @Test
  public void testAddToReplace() {
    List<Card> points;
    Pile pile = new Pile(0, deck);

    points = pile.addCard(new Card(1));
    assertEquals(2, pile.cardCount());
    assertEquals(0, points.size());

    points = pile.addCard(new Card(2));
    assertEquals(3, pile.cardCount());
    assertEquals(0, points.size());

    points = pile.addCard(new Card(3));
    assertEquals(4, pile.cardCount());
    assertEquals(0, points.size());

    points = pile.addCard(new Card(4));
    assertEquals(5, pile.cardCount());
    assertEquals(0, points.size());

    points = pile.addCard(new Card(5));
    assertEquals(1, pile.cardCount());
    assertEquals(5, points.size());
  }

  @Test
  public void testReplace() {
    Pile pile = new Pile(0, deck);
    pile.addCard(new Card(1));

    List<Card> points = pile.replace(new Card(104));
    assertEquals(1, pile.cardCount());
    assertEquals(new Card(104), pile.currentCard());
    assertEquals(2, points.size());
    assertEquals(new Card(1), points.get(1));
  }

  @Test
  public void testDifference() {
    Pile pile = new Pile(0, deck);
    pile.addCard(new Card(10));

    assertEquals(Optional.empty(), pile.difference(new Card(5)));
    assertEquals(Optional.of(1), pile.difference(new Card(11)));
    assertEquals(Optional.of(10), pile.difference(new Card(20)));
  }
}
