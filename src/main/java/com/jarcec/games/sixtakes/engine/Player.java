package com.jarcec.games.sixtakes.engine;

import com.jarcec.games.sixtakes.brain.Brain;

import java.util.Objects;
import java.util.UUID;

public record Player(UUID id, String name, Brain brain) {

  public Player(String name, Brain brain) {
    this(UUID.randomUUID(), name, brain);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Player player)) return false;
    return Objects.equals(id(), player.id());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id());
  }
}
