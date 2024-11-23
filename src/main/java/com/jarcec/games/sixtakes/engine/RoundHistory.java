package com.jarcec.games.sixtakes.engine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Generally history of the game for purpose of training future brains.
 */
@Getter
public class RoundHistory {
  public static final class History {
    @Getter @Setter @AllArgsConstructor
    public static final class Table {
      private List<Integer> pile1;
      private List<Integer> pile2;
      private List<Integer> pile3;
      private List<Integer> pile4;
    }

    @Getter @Setter @AllArgsConstructor
    public static final class Player {
      private String id;
      private List<Integer> startingHand;
    }

    @Getter @Setter @AllArgsConstructor
    public static final class Turn {
      private Table table;
      private Map<String, Integer> selectedCards;
    }
  }

  private final List<History.Player> players = new ArrayList<>();
  private final List<History.Turn> turns = new ArrayList<>();
  private Map<String, Integer> score = new HashMap<>();

  public RoundHistory(Table table) {
    for(TablePlayer player: table.getPlayers()) {
      this.players.add(new History.Player(
        player.getPlayer().getId().toString(),
        player.getHand().getCards().stream().map(Card::getId).sorted().toList()
      ));
    }
  }

  public void createAndAddTurn(Table table, List<Selection> selections) {
    turns.add(new History.Turn(
      new History.Table(
        table.getPiles().get(0).getCards().stream().map(Card::getId).toList(),
        table.getPiles().get(1).getCards().stream().map(Card::getId).toList(),
        table.getPiles().get(2).getCards().stream().map(Card::getId).toList(),
        table.getPiles().get(3).getCards().stream().map(Card::getId).toList()
      ),
      selections.stream().collect(Collectors.toMap(c -> c.getRoundPlayer().getPlayer().getId().toString(), c -> c.getCard().getId()))
    ));
  }

  public void setFinalScore(Score score) {
    this.score = score.getPoints().entrySet().stream().collect(Collectors.toMap(
      e -> e.getKey().getId().toString(),
      Map.Entry::getValue
    ));
  }
}
