package com.jarcec.games.sixtakes.brain.dl4j;

import org.deeplearning4j.nn.conf.ComputationGraphConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.nd4j.linalg.learning.config.Sgd;

public class Dl4jTrainerRunner {
  public static void main(String[] args) {
    // Build our Neural Network
    ComputationGraphConfiguration conf = new NeuralNetConfiguration.Builder()
      .updater(new Sgd(0.01))
      .graphBuilder()
      .addInputs("hand", "pile1", "pile2", "pile3", "pile4", "playedcards")
      .setInputTypes(
        InputType.feedForward(104), // Hand
        InputType.feedForward(104), // Pile1
        InputType.feedForward(104), // Pile2
        InputType.feedForward(104), // Pile3
        InputType.feedForward(104), // Pile4
        InputType.feedForward(104) // playedcards
      )
      .addLayer("piles", new DenseLayer.Builder().nOut(104).build(), "pile1", "pile2", "pile3", "pile4")
      .addLayer("selectedcard", new DenseLayer.Builder().nOut(104).build(), "hand", "piles", "playedcards")
      .addLayer("selectedpile", new DenseLayer.Builder().nOut(4).build(), "hand", "piles", "playedcards")
      .setOutputs("selectedcard", "selectedpile")
      .build();

    ComputationGraph net = new ComputationGraph(conf);
    net.init();
  }
}
