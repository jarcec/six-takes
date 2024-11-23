package com.jarcec.games.sixtakes.brain.dl4j;

import com.jarcec.games.sixtakes.brain.Brain;
import com.jarcec.games.sixtakes.engine.Card;
import com.jarcec.games.sixtakes.engine.Hand;
import com.jarcec.games.sixtakes.engine.Table;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Dl4jBrain implements Brain {
  private final ComputationGraph net;
  private List<Card> playedCards;

  public Dl4jBrain(String path) throws IOException {
    this.net = ComputationGraph.load(new File(path), true);
    this.net.init();
  }

  @Override
  public void startRoundAndTurn(Table table, int round, int turn) {
    if(turn == 1) {
      playedCards = new ArrayList<>();
      playedCards.addAll(table.getPiles().get(0).getCards());
      playedCards.addAll(table.getPiles().get(1).getCards());
      playedCards.addAll(table.getPiles().get(2).getCards());
      playedCards.addAll(table.getPiles().get(3).getCards());
    }
  }

  @Override
  public void selectedCards(List<com.jarcec.games.sixtakes.engine.Selection> selections) {
    for(com.jarcec.games.sixtakes.engine.Selection selection : selections) {
      playedCards.add(selection.getCard());
    }
  }

  @Override
  public Selection makeSelection(Hand hand, Table table) {
    List<Card> handCards = hand.getCards().stream().sorted().toList();

    try(
      INDArray handVector = Nd4j.zeros(1,104);
      INDArray pile1Vector = Nd4j.zeros(1, 104);
      INDArray pile2Vector = Nd4j.zeros(1, 104);
      INDArray pile3Vector = Nd4j.zeros(1, 104);
      INDArray pile4Vector = Nd4j.zeros(1, 104);
      INDArray playedCardsVector = Nd4j.zeros(1, 104);
    ) {
      cardsToVector(handCards, handVector);
      cardsToVector(table.getPiles().get(0).getCards(), pile1Vector);
      cardsToVector(table.getPiles().get(1).getCards(), pile2Vector);
      cardsToVector(table.getPiles().get(2).getCards(), pile3Vector);
      cardsToVector(table.getPiles().get(3).getCards(), pile4Vector);
      cardsToVector(playedCards, playedCardsVector);

      INDArray[] output = net.output(handVector, pile1Vector, pile2Vector, pile3Vector, pile4Vector, playedCardsVector);

      return new Brain.Selection(
        handCards.get(Dl4jUtils.findLargestIndex(output[1], handCards.size())),
        table.getPiles().get(Dl4jUtils.findLargestIndex(output[1]))
      );
    }
  }

  private void cardsToVector(Collection<Card> cards, INDArray vector) {
    for(Card card : cards) {
      vector.putScalar(card.getId() - 1, 1);
    }
  }
}
