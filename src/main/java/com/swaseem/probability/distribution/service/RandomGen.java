package com.swaseem.probability.distribution.service;

import com.swaseem.probability.distribution.exceptions.RandomGenValidationException;
import java.util.Random;

public class RandomGen {
    // Values that may be returned by nextNum() private
    private final int[] randomNums;
    // Probability of the occurence of randomNums private
    private final float[] probabilities;

    private final Random random;

    public RandomGen(final int[] randomNum, final float[] probabilities, final Random random) {
        this.randomNums = randomNum;
        this.probabilities = probabilities;
        this.random = random;
        validation();
    }

    /**
     * Perform validation of arrays provided to ensure next num can be called.
     * The following rules are applied:
     * - Ensure Arrays are not null
     * - Ensure Arrays are not empty
     * - Ensure Arrays are of the same size
     * - Ensure total probability is not greater than 1
     *
     * @throws RandomGenValidationException If the validation rules fail
     */
    void validation() {

        if (this.randomNums == null || this.probabilities == null) {
            throw new RandomGenValidationException("Null array provided, unable to process");
        }

        // ensure both arrays are not empty
        if (this.randomNums.length == 0 || this.probabilities.length == 0) {
            throw new RandomGenValidationException("Empty arrays provided, unable to process");
        }

        // ensure both arrays are same size
        if (this.randomNums.length != this.probabilities.length) {
            throw new RandomGenValidationException("Random numbers and probabilities size do not match");
        }

        float totalProbability = 0f;
        for(float probability: this.probabilities) {
            // ensure floats provided are between 0 and 1
            if (probability < 0f || probability > 1f) {
                throw new RandomGenValidationException("One of the probability values provided are not in the range of 0 and 1");
            }
            totalProbability += probability;
        }

        if (totalProbability > 1f) {
            throw new RandomGenValidationException("Total probability greater than 1");
        }
    }

    /**
     Returns one of the randomNums. When this method is called
     multiple times over a long period, it should return the
     numbers roughly with the initialized probabilities.
     */
    public int nextNum() {
        final float randomFloat = this.random.nextFloat();
        float cumulativeProbability = 0f;
        for (int i = 0; i < this.probabilities.length; i++) {
            cumulativeProbability += this.probabilities[0];
            if (randomFloat <= cumulativeProbability) {
                return this.randomNums[i];
            }
        }
        // this is if the randomly generated float is not in range
        return this.nextNum();
    }
}