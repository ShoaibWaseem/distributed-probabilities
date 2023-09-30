package com.swaseem.probability.distribution.service;

import com.swaseem.probability.distribution.exceptions.RandomGenValidationException;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * This implemetation sorts in order of probability before getting next number. While this adds the benefit of
 * easier understanding for someone passing values in, nothing was gained in terms of output.
 */
public class TreeMapRandomGen {

    private final Random random;

    private final TreeMap<Float, Integer> orderedProbabilities;

    private final float totalProbability;

    public TreeMapRandomGen(final int[] randomNum, final float[] probabilities, final Random random)  {
        this.random = random;
        this.orderedProbabilities = validateAndGetOrderedProbabilities(randomNum, probabilities);
        this.totalProbability = this.orderedProbabilities.keySet().stream()
                .collect(Collectors.summingDouble(Float::doubleValue)).floatValue();
    }

    TreeMap<Float, Integer> validateAndGetOrderedProbabilities(final int[] randomNums, final float[] probabilities) {

        if (randomNums == null || probabilities == null) {
            throw new RandomGenValidationException("Null array provided, unable to process");
        }

        // ensure both arrays are not empty
        if (randomNums.length == 0 || probabilities.length == 0) {
            throw new RandomGenValidationException("Empty arrays provided, unable to process");
        }

        // ensure both arrays are same size
        if (randomNums.length != probabilities.length) {
            throw new RandomGenValidationException("Random numbers and probabilities size do not match");
        }

        final TreeMap<Float, Integer> orderedProbabilities = new TreeMap<>();
        for (int i = 0; i < probabilities.length; i++) {

            if (probabilities[i] <= 0f) {
                throw new RandomGenValidationException("Probability provided is less than or equal to 0");
            }

            if (orderedProbabilities.containsKey(probabilities[i])) {
                continue;
            }
            orderedProbabilities.put(probabilities[i], randomNums[i]);
        }
        return orderedProbabilities;
    }

    /**
     Returns one of the randomNums. When this method is called
     multiple times over a long period, it should return the
     numbers roughly with the initialized probabilities.
     */
    public int nextNum() {
        float cumulativeProbability = 0f;
        float randomFloat = this.random.nextFloat(this.totalProbability);
        for(Map.Entry<Float, Integer> entry: this.orderedProbabilities.entrySet()) {
            cumulativeProbability += entry.getKey();
            if (randomFloat <= cumulativeProbability) {
                return entry.getValue();
            }
        }
        throw new RuntimeException("No probability found. This should not occur");
    }
}