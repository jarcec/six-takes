package com.jarcec.games.sixtakes.engine;

public record Card(int id) implements Comparable<Card> {

  public int getPoints() {
    if(id == 55) { return 7; }
    if(id % 11 == 0) { return 5; }
    if(id % 10 == 0) { return 3; }
    if(id % 5 == 0) { return 2; }

    return 1;
  }

  @Override
  public String toString() {
    return String.valueOf(id);
  }

  @Override
  public int compareTo(Card other) {
    return Integer.compare(this.id, other.id);
  }
}
