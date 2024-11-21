package com.jarcec.games.sixtakes.engine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public static final class SelectedCard {
      private String playerId;
      private int card;
    }

    @Getter @Setter @AllArgsConstructor
    public static final class Turn {
      private Table table;
      private List<SelectedCard> selectedCards;
    }

    @Getter @Setter @AllArgsConstructor
    public static final class Score {
      private String playerId;
      private int score;
    }
  }


  private final List<History.Player> players = new ArrayList<>();
  private final List<History.Turn> turns = new ArrayList<>();
  private final List<History.Score> score = new ArrayList<>();

  public RoundHistory(Table table) {
    for(TablePlayer player: table.getPlayers()) {
      this.players.add(new History.Player(
        player.getPlayer().getId().toString(),
        player.getHand().getCards().stream().map(Card::getId).sorted().toList()
      ));
    }
  }

  public void createAndAddTurn(Table table, List<SelectedCard> selectedCards) {
    turns.add(new History.Turn(
      new History.Table(
        table.getPiles().get(0).getCards().stream().map(Card::getId).toList(),
        table.getPiles().get(1).getCards().stream().map(Card::getId).toList(),
        table.getPiles().get(2).getCards().stream().map(Card::getId).toList(),
        table.getPiles().get(3).getCards().stream().map(Card::getId).toList()
      ),
      selectedCards.stream().map(c -> new History.SelectedCard(c.getRoundPlayer().getPlayer().getId().toString(), c.getCard().getId())).toList()
    ));
  }

  public void setFinalScore(Score score) {
    for(Map.Entry<Player, Integer> entry : score.getPoints().entrySet()) {
      this.score.add(new History.Score(
        entry.getKey().getId().toString(),
        entry.getValue()
      ));
    }
  }
}
