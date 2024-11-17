package com.jarcec.games.sixtakes.display;

import java.util.ArrayList;
import java.util.List;

public class Histogram {
  private final int groupSize;
  private final List<Integer> counts;

  public Histogram(int groupSize) {
    this.groupSize = groupSize;
    this.counts = new ArrayList<>();
  }

  public void add(int points) {
    // Identify proper group
    int group = points / this.groupSize;

    // Ensure that we have all the groups we need
    for(int ignored = counts.size(); ignored <= group; ignored++) {
      counts.add(0);
    }

    // And finally persist the result
    this.counts.set(group, this.counts.get(group) + 1);
  }

  public void displayStdout() {
    // Generate labels for the groups
    List<String> labels = new ArrayList<>();
    for(int i = 0; i < this.counts.size(); i++) {
      if(groupSize == 1) {
        labels.add(Integer.toString(i));
      } else {
        labels.add((i * groupSize) + "-" + ((i + 1) * this.groupSize - 1));
      }
    }

    for(int i = 0; i < this.counts.size(); i++) {
      System.out.println(labels.get(i) + ": " + "█".repeat(this.counts.get(i)));
    }

  }
}