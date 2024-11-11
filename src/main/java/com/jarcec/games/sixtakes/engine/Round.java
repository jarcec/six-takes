package com.jarcec.games.sixtakes.engine;

import lombok.extern.log4j.Log4j2;

import java.util.*;

@Log4j2
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

    @Override
    public String toString() {
      return String.valueOf(card.id());
    }
  }

  private final Table table;
  private final Set<RoundPlayer> players;

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
      log.info("Starting round {}", roundNumber);
      List<SelectedCard> selectedCards = new ArrayList<>();

      // 2) Players need to chose cards
      for(RoundPlayer roundPlayer : this.players) {
        selectedCards.add(roundPlayer.selectCard(table));
      }

      // 3) Sort order of the cards
      selectedCards.sort((a, b) -> a.card.id() < b.card.id() ? 1 : -1);
      log.info("Selected cards: {}", selectedCards);

      // 4) Add cards to the table
      for(SelectedCard selectedCard : selectedCards) {
        selectedCard.addToTable(table);
      }
      log.info("Table at the end of the round: {}", table);
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
