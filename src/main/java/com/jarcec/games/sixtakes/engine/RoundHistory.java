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
      private List<List<Integer>> piles;
    }

    @Getter @Setter @AllArgsConstructor
    public static final class Selection {
      private int card;
      private int pile;
    }

    @Getter @Setter @AllArgsConstructor
    public static final class Turn {
      private Table table;
      private Map<String, Selection> selections;
    }
  }

  private final Map<String, List<Integer>> hands;
  private final List<History.Turn> turns = new ArrayList<>();
  private Map<String, Integer> score = new HashMap<>();

  public RoundHistory(Table table) {
    this.hands = table.getPlayers().stream().collect(Collectors.toMap(
      p -> p.getPlayer().getId().toString(),
      p -> p.getHand().getCards().stream().map(Card::getId).sorted().toList()
    ));
  }

  public void createAndAddTurn(Table table, List<Selection> selections) {
    turns.add(new History.Turn(
      new History.Table(List.of(
        table.getPiles().get(0).getCards().stream().map(Card::getId).toList(),
        table.getPiles().get(1).getCards().stream().map(Card::getId).toList(),
        table.getPiles().get(2).getCards().stream().map(Card::getId).toList(),
        table.getPiles().get(3).getCards().stream().map(Card::getId).toList()
      )),
      selections.stream().collect(Collectors.toMap(
        s -> s.getRoundPlayer().getPlayer().getId().toString(),
        s -> new History.Selection(s.getCard().getId(), s.getPile().getPileIndex())
      ))
    ));
  }

  public void setFinalScore(Score score) {
    this.score = score.getPoints().entrySet().stream().collect(Collectors.toMap(
      e -> e.getKey().getId().toString(),
      Map.Entry::getValue
    ));
  }
}
