package com.jarcec.games.sixtakes.brain.dl4j;

import org.nd4j.linalg.api.ndarray.INDArray;

public class Dl4jUtils {
  public static int findLargestIndex(INDArray vector) {
    return findLargestIndex(vector, Integer.MAX_VALUE);
  }

  public static int findLargestIndex(INDArray vector, int cutOffIndex) {
    double maxValue = 0;
    int maxIndex = -1;

    for (int index = 0; index < vector.size(1) && index < cutOffIndex; index++) {
      if(vector.getDouble(index) > maxValue) {
        maxValue = vector.getDouble(index);
        maxIndex = index;
      }
    }

    return maxIndex;
  }
}
