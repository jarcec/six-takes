package com.jarcec.games.sixtakes.display;

import java.util.ArrayList;
import java.util.List;

public class Histogram {
  private final List<String> labels;
  private final List<Integer> counts;

  public Histogram(List<String> labels) {
    this.labels = labels;
    this.counts = new ArrayList<>(labels.size());
    for(String ignored : labels) {
      this.counts.add(0);
    }
  }

  public void add(int group) {
    this.counts.set(group, this.counts.get(group) + 1);
  }

  public void display() {
    for(int i = 0; i < labels.size(); i++) {
      System.out.println(labels.get(i) + ": " + "X".repeat(counts.get(i)) );
    }
  }
}
