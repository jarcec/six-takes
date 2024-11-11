package com.jarcec.games.sixtakes.engine;

import java.util.*;

public class Round {

  private record RoundPlayer(Player player, Hand hand, List<Card> points) {
    public RoundPlayer(Player player, Deck deck) {
      this(player, new Hand(deck), new ArrayList<>());
    }

    public SelectedCard selectCard(Table table) {
      return new SelectedCard(
        player.brain().selectCard(hand, table),
        this
      );
    }
  }

  private record SelectedCard(Card card, RoundPlayer roundPlayer) {
    public void addToTable(Table table) {
      Optional<List<Card>> pointCards = table.addCard(card);

      if(pointCards.isPresent()) {
        roundPlayer.points().addAll(pointCards.get());
      } else {
        Pile pile = roundPlayer.player().brain().selectPile(table);
        roundPlayer.points().addAll(pile.replace(card));
      }

      roundPlayer.hand().removeCard(card);
    }
  }

  private Table table;
  private Set<RoundPlayer> players;

  public Round(Set<Player> players) {
    // 1) Create a new deck
    Deck deck = new Deck();

    // 2) Create a new table
    this.table = new Table(deck);

    // 3) Create Round Players (players with hands)
    this.players = new HashSet<>();
    for(Player player : players) {
      this.players.add(new RoundPlayer(player, deck));
    }
  }

  public Score playRound() {
    // Play all 10 cards in the round
    for(int roundNumber = 0; roundNumber < 10; roundNumber++) {
      // 1) Initialization
      List<SelectedCard> selectedCards = new ArrayList<>();

      // 2) Players need to chose cards
      for(RoundPlayer roundPlayer : this.players) {
        selectedCards.add(roundPlayer.selectCard(table));
      }

      // 3) Sort order of the cards
      selectedCards.sort((a, b) -> a.card.id() < b.card.id() ? 1 : -1);

      // 4) Add cards to the table
      for(SelectedCard selectedCard : selectedCards) {
        selectedCard.addToTable(table);
      }
    }

    Score score = new Score();
    for(RoundPlayer roundPlayer : this.players) {
      score.addPoints(
        roundPlayer.player,
        roundPlayer.points.stream().map(Card::getPoints).reduce(0, Integer::sum)
      );
    }
    return score;
  }
}
