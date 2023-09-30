package com.swaseem.probability.distribution;

import com.swaseem.probability.distribution.service.RandomGen;
import com.swaseem.probability.distribution.service.TreeMapRandomGen;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Application {
    public static void main(String[] args) {

        Application.arraySolution();

    }

    /**
     * This entrypoint to the application uses a solution with Arrays. With the provided arguments of numbers, its
     * probabilities and a random generator, it builds a {@link RandomGen} which can then be used to loop over the next
     * configured iterations and generate a map of results which is printed to console.
     */
    public static void arraySolution() {

        // Definte number of iterations
        final int iterations = Integer.MAX_VALUE / 2;

        // Initialise Arguments
        final Random random = new Random();
        final int[] randomNums = new int[]{5,4,3,2,1};
        final float[] randomProbabilities = new float[]{0.35f, 0.25f, 0.15f, 0.12f, 0.13f};

        // Create new random generator
        final RandomGen randomGen = new RandomGen(randomNums, randomProbabilities, random);


        // To print results, create a map
        final Map<Integer, Integer> results = new HashMap<>();

        for(int i = 0; i < iterations; i++) {
            int result = randomGen.nextNum();

            if (results.containsKey(result)) {
                results.replace(result, results.get(result) + 1);
            } else {
                results.put(result, 1);
            }
        }
        System.out.println(results);

    }

    /**
     * Another entrypoint into the application except using {@link java.util.TreeMap} as an object to loop over.
     * This entrypoint also allows for provided probabilities to have a sum greater than 1.
     */
    public static void treeMapSolution() {
        // Definte number of iterations
        final int iterations = Integer.MAX_VALUE / 2;

        // initialise Arguments
        final Random random = new Random();
        final int[] randomNums = new int[]{5,4,3,2,1};
        final float[] randomProbabilities = new float[]{0.5f, 0.4f, 0.3f, 0.2f, 0.1f};

        // Create new random generator
        final TreeMapRandomGen randomGen = new TreeMapRandomGen(randomNums, randomProbabilities, random);

        // To print results neatly, create a map to store results
        final Map<Integer, Integer> results = new HashMap<>();

        for(int i = 0; i < iterations; i++) {
            int result = randomGen.nextNum();

            if (results.containsKey(result)) {
                results.replace(result, results.get(result) + 1);
            } else {
                results.put(result, 1);
            }
        }
        System.out.println(results);
    }
}