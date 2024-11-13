package com.jarcec.games.sixtakes.engine;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Card implements Comparable<Card> {
  private final int id;

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Card card)) return false;
    return getId() == card.getId();
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getId());
  }

  public Card(int id) {
    this.id = id;
  }

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
