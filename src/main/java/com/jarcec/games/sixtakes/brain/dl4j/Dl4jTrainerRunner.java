package com.jarcec.games.sixtakes.brain.dl4j;

import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderMultiDataSetIterator;
import org.deeplearning4j.nn.conf.ComputationGraphConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.iterator.MultiDataSetIterator;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Sgd;

import java.io.File;
import java.util.Arrays;

public class Dl4jTrainerRunner {
  public static void main(String[] args) throws Exception{
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
      .addLayer("selectedcard", new DenseLayer.Builder().nOut(10).build(), "hand", "piles", "playedcards")
      .addLayer("selectedpile", new DenseLayer.Builder().nOut(4).build(), "hand", "piles", "playedcards")
      .addLayer("selectedcardout", new OutputLayer.Builder().nOut(10).build(), "selectedcard")
      .addLayer("selectedpileout", new OutputLayer.Builder().nOut(4).build(), "selectedpile")
      .setOutputs("selectedcardout", "selectedpileout")
      .build();

    // Instantiate NN
    ComputationGraph net = new ComputationGraph(conf);
    net.init();
    net.setListeners(new ScoreIterationListener(10));

    // Load testing data
    RecordReader rr = new CSVRecordReader(0, ",");
    rr.initialize(new FileSplit(new File("/Users/jarcec/projects/six-takes/round-histories-training.csv")));
    MultiDataSetIterator iterator = new RecordReaderMultiDataSetIterator.Builder(1_000)
      .addReader("main",rr)
      .addInput("main", 0, 1*104-1)
      .addInput("main", 104, 2*104-1)
      .addInput("main", 2*104, 3*104-1)
      .addInput("main", 3*104, 4*104-1)
      .addInput("main", 4*104, 5*104-1)
      .addInput("main", 5*104, 6*104-1)
      .addOutput("main",6*104,6*104 + 10 - 1)
      .addOutput("main",6*104 + 10, 6*104 + 10 + 4 - 1)
      .build();

    // Train NN
    System.out.println("Starting training...");
    net.fit(iterator);
    System.out.println("Training finished; saving model");
    net.save(new File("/Users/jarcec/projects/six-takes/mercury.model"));

    // Some test to see if it will work properly (drop me)
    try(
      INDArray handVector = Nd4j.zeros(1, 104);
      INDArray pile1Vector = Nd4j.zeros(1, 104);
      INDArray pile2Vector = Nd4j.zeros(1, 104);
      INDArray pile3Vector = Nd4j.zeros(1, 104);
      INDArray pile4Vector = Nd4j.zeros(1, 104);
      INDArray playedCardsVector = Nd4j.zeros(1, 104);
    ) {
      INDArray[] output = net.output(handVector, pile1Vector, pile2Vector, pile3Vector, pile4Vector, playedCardsVector);
      System.out.println("Output from all zero input: " + Arrays.toString(output));
      System.out.println("Selected card: " + Dl4jUtils.findLargestIndex(output[0]));
      System.out.println("Selected pile: " + Dl4jUtils.findLargestIndex(output[1]));
    }
  }
}
