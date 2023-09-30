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

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TreeMapRandomGenTest {

    @Mock
    Random random;

    @ParameterizedTest
    @MethodSource("validationTestArguments")
    void validation_shouldThrowErrors(final int[] randomNums, final float[] probabilities, final String expectedErrorMessage) {

        RandomGenValidationException result = Assertions.assertThrows(RandomGenValidationException.class, () -> {
            new TreeMapRandomGen(randomNums, probabilities, random);
        });
        Assertions.assertEquals(expectedErrorMessage, result.getMessage());
    }

    @Test
    void nextNum_success() {
        final int[] randomNums = new int[]{1,2};
        final float[] probabilities = new float[]{0.45f, 0.1f};

        when(this.random.nextFloat(0.45f + 0.1f)).thenReturn(0.5f);

        final TreeMapRandomGen randomGen = new TreeMapRandomGen(randomNums, probabilities, random);

        final int result = randomGen.nextNum();

        Assertions.assertEquals(1, result);
    }

    @AfterEach
    void afterEach() {
        Mockito.verifyNoMoreInteractions(random);
    }


    static Stream<Arguments> validationTestArguments() {
        return Stream.of(
                Arguments.of(null, new float[]{1f}, "Null array provided, unable to process"),
                Arguments.of(new int[]{1}, null, "Null array provided, unable to process"),
                Arguments.of(new int[]{}, new float[]{1f}, "Empty arrays provided, unable to process"),
                Arguments.of(new int[]{1}, new float[]{}, "Empty arrays provided, unable to process"),
                Arguments.of(new int[]{1,2}, new float[]{0.5f}, "Random numbers and probabilities size do not match"),
                Arguments.of(new int[]{1}, new float[]{-1f}, "Probability provided is less than or equal to 0"),
                Arguments.of(new int[]{1}, new float[]{0f}, "Probability provided is less than or equal to 0")
        );
    }

}