package com.jarcec.games.sixtakes.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Pile {
  private List<Card> cards;

  public Pile(Deck deck) {
    initPile(deck.drawCard());
  }

  private void initPile(Card card) {
    cards = new ArrayList<>(5);
    cards.add(card);
  }

  public List<Card> getCards() {
    return Collections.unmodifiableList(cards);
  }

  public Card currentCard() {
    return cards.get(cards.size() - 1);
  }

  public List<Card> replace(Card card) {
    List<Card> pointCards = cards;
    initPile(card);
    return pointCards;
  }

  public int cardCount() {
    return cards.size();
  }

  public Optional<Integer> difference(Card card) {
    Card currentCard = currentCard();

    if(currentCard.id() < card.id()) {
      return Optional.of(card.id() - currentCard.id());
    }

    return Optional.empty();
  }

  public List<Card> addCard(Card card) {
    if(cards.size() == 5) {
      return replace(card);
    }

    cards.add(card);
    return Collections.emptyList();
  }

  public int getPoints() {
    return cards.stream().map(Card::getPoints).reduce(0, Integer::sum);
  }

  @Override
  public String toString() {
    return cards.toString();
  }
}
