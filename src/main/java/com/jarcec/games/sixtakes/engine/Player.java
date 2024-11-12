package com.jarcec.games.sixtakes.engine;

import com.jarcec.games.sixtakes.brain.Brain;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

public class Player {
  @Getter private final UUID id;
  @Getter private final String name;
  @Getter private final Brain brain;

  public Player(String name, Brain brain) {
    this.id = UUID.randomUUID();
    this.name = name;
    this.brain = brain;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Player player)) return false;
    return Objects.equals(getId(), player.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getId());
  }
}
