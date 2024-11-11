package com.jarcec.games.sixtakes.engine;

public record Card(int id) {

  public int getPoints() {
    if(id == 55) { return 7; }
    if(id % 11 == 0) { return 5; }
    if(id % 10 == 0) { return 3; }
    if(id % 5 == 0) { return 2; }

    return 1;
  }

}
