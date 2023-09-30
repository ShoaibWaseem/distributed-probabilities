package com.swaseem.probability.distribution;

import com.swaseem.probability.distribution.service.RandomGen;
import com.swaseem.probability.distribution.service.TreeMapRandomGen;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RandomGenSolution {
    public static void main(String[] args) {

        RandomGenSolution.arraySolution();

        // Different solution but differs from spec provided
         RandomGenSolution.treeMapSolution();
    }

    public static void arraySolution() {
        final Random random = new Random();
        final int[] randomNums = new int[]{5,4,3,2,1};
        final float[] randomProbabilities = new float[]{0.35f, 0.25f, 0.15f, 0.12f, 0.13f};

        final RandomGen randomGen = new RandomGen(randomNums, randomProbabilities, random);

        final Map<Integer, Integer> results = new HashMap<>();

        for(int i = 0; i < Integer.MAX_VALUE / 2; i++) {
            int result = randomGen.nextNum();

            if (results.containsKey(result)) {
                results.replace(result, results.get(result) + 1);
            } else {
                results.put(result, 1);
            }
        }
        System.out.println(results);

    }

    public static void treeMapSolution() {
        final Random random = new Random();
        final int[] randomNums = new int[]{5,4,3,2,1};
        final float[] randomProbabilities = new float[]{0.5f, 0.4f, 0.3f, 0.2f, 0.1f};

        final TreeMapRandomGen randomGen = new TreeMapRandomGen(randomNums, randomProbabilities, random);

        final Map<Integer, Integer> results = new HashMap<>();

        for(int i = 0; i < Integer.MAX_VALUE / 2; i++) {
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