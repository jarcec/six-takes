package com.jarcec.games.sixtakes.engine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardTest {

  @Test
  public void testPoints() {
    // 7 points
    assertEquals(7, new Card(55).getPoints());
    // 5 points
    assertEquals(5, new Card(11).getPoints());
    assertEquals(5, new Card(22).getPoints());
    assertEquals(5, new Card(33).getPoints());
    assertEquals(5, new Card(44).getPoints());
    assertEquals(5, new Card(66).getPoints());
    assertEquals(5, new Card(77).getPoints());
    assertEquals(5, new Card(88).getPoints());
    assertEquals(5, new Card(99).getPoints());
    // 3 points
    assertEquals(3, new Card(10).getPoints());
    assertEquals(3, new Card(20).getPoints());
    assertEquals(3, new Card(30).getPoints());
    assertEquals(3, new Card(40).getPoints());
    assertEquals(3, new Card(50).getPoints());
    assertEquals(3, new Card(60).getPoints());
    assertEquals(3, new Card(70).getPoints());
    assertEquals(3, new Card(80).getPoints());
    assertEquals(3, new Card(90).getPoints());
    assertEquals(3, new Card(100).getPoints());
    // 2 points
    assertEquals(2, new Card(5).getPoints());
    assertEquals(2, new Card(15).getPoints());
    assertEquals(2, new Card(25).getPoints());
    assertEquals(2, new Card(35).getPoints());
    assertEquals(2, new Card(45).getPoints());
    assertEquals(2, new Card(65).getPoints());
    assertEquals(2, new Card(75).getPoints());
    assertEquals(2, new Card(85).getPoints());
    assertEquals(2, new Card(95).getPoints());
    // 1 points
    assertEquals(1, new Card(1).getPoints());
    assertEquals(1, new Card(104).getPoints());
  }
}
