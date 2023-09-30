package com.swaseem.probability.distribution.service;

import com.swaseem.probability.distribution.exceptions.RandomGenValidationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;
import java.util.stream.Stream;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RandomGenTest {

    @Mock
    Random random;

    @ParameterizedTest
    @MethodSource("validationTestArguments")
    void validation_shouldThrowErrors(final int[] randomNums, final float[] probabilities, final String expectedErrorMessage) {

        RandomGenValidationException result = Assertions.assertThrows(RandomGenValidationException.class, () -> {
            new RandomGen(randomNums, probabilities);
        });
        Assertions.assertEquals(expectedErrorMessage, result.getMessage());
    }

    @Test
    void nextNum_success() {
        final int[] randomNums = new int[]{1,2};
        final float[] probabilities = new float[]{0.45f, 0.1f};

        when(this.random.nextFloat()).thenReturn(0.5f);

        final RandomGen randomGen = new RandomGen(randomNums, probabilities, random);

        final int result = randomGen.nextNum();

        Assertions.assertEquals(2, result);
    }

    @Test
    void nextNum_calledTwice() {
        final int[] randomNums = new int[]{1,2};
        final float[] probabilities = new float[]{0.1f, 0.2f};

        when(this.random.nextFloat()).thenReturn(0.4f).thenReturn(0.05f);

        final RandomGen randomGen = Mockito.spy(new RandomGen(randomNums, probabilities, random));

        final int result = randomGen.nextNum();

        Assertions.assertEquals(1, result);
        Mockito.verify(randomGen, times(2)).nextNum();
        Mockito.verifyNoMoreInteractions(randomGen);
    }

    @AfterEach
    void afterEach() {
        Mockito.verifyNoMoreInteractions(this.random);
    }

    static Stream<Arguments> validationTestArguments() {
        return Stream.of(
                Arguments.of(null, new float[]{1f}, "Null array provided, unable to process"),
                Arguments.of(new int[]{1}, null, "Null array provided, unable to process"),
                Arguments.of(new int[]{}, new float[]{1f}, "Empty arrays provided, unable to process"),
                Arguments.of(new int[]{1}, new float[]{}, "Empty arrays provided, unable to process"),
                Arguments.of(new int[]{1,2}, new float[]{0.5f}, "Random numbers and probabilities size do not match"),
                Arguments.of(new int[]{1,2}, new float[]{1f, 1.2f}, "One of the probability values provided are not in the range of 0 and 1"),
                Arguments.of(new int[]{1,2}, new float[]{0.1f, -0.3f}, "One of the probability values provided are not in the range of 0 and 1"),
                Arguments.of(new int[]{1,2}, new float[]{0.5f, 0.6f}, "Total probability greater than 1")
        );
    }
}