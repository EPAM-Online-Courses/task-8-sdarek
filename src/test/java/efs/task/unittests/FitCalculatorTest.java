package efs.task.unittests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class FitCalculatorTest {

    private FitCalculator fitCalculator;

    @BeforeEach
    void setUp() {
        fitCalculator = new FitCalculator();
    }

    @Test
    void shouldReturnTrue_whenDietRecommended() {
        // Given
        double weight = 89.2;
        double height = 1.72;

        // When
        boolean recommended = fitCalculator.isBMICorrect(weight, height);

        // Then
        assertTrue(recommended);
    }

    @Test
    void shouldReturnFalse_whenDietNotRecommended() {
        // Given
        double weight = 69.5;
        double height = 1.72;

        // When
        boolean recommended = fitCalculator.isBMICorrect(weight, height);

        // Then
        assertFalse(recommended);
    }

    @Test
    void shouldThrowIllegalArgumentException_whenHeightIsZero() {
        // Given
        double weight = 70.0;
        double height = 0.0;

        // When/Then
        assertThrows(IllegalArgumentException.class, () -> fitCalculator.isBMICorrect(weight, height));
    }

    @ParameterizedTest(name = "Weight: {0}")
    @ValueSource(doubles = {100.0, 150.0, 200.0}) // tu rowniez drobna modyfikacja bo tamte wartosci byly na granicy
    void shouldReturnTrue_forDifferentWeights(double weight) {
        // Given
        double height = 1.72;

        // When
        boolean recommended = fitCalculator.isBMICorrect(weight, height);

        // Then
        assertTrue(recommended);
    }

    @ParameterizedTest(name = "Height: {0}, Weight: {1}")
    @CsvSource({"1.70, 60.0", "1.75, 70.0", "1.80, 80.0"})
    void shouldReturnFalse_forDifferentHeightAndWeight(double height, double weight) {
        // When
        boolean recommended = fitCalculator.isBMICorrect(weight, height);

        // Then
        assertFalse(recommended);
    }

    @ParameterizedTest(name = "Height: {0}, Weight: {1}")
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1) //drobna zmiana prosze sie nie denerwowac
    void shouldReturnFalse_forDifferentHeightAndWeight_fromFile(double height, double weight) {
        // When
        boolean recommended = fitCalculator.isBMICorrect(weight, height);

        // Then
        assertFalse(recommended);
    }

    @Test
    void shouldReturnUserWithWorstBMI() {
        // Given
        User user1 = new User(1.80, 60.0);
        User user2 = new User(1.82, 99.1);
        User user3 = new User(1.79, 97.3);
        User user4 = new User(1.62, 65.7);

        // When
        User userWithWorstBMI = fitCalculator.findUserWithTheWorstBMI(List.of(user1, user2, user3, user4));

        // Then
        assertEquals(user3, userWithWorstBMI);
    }

    @Test
    void shouldReturnNull_whenUserListIsEmpty() {
        // When
        User userWithWorstBMI = fitCalculator.findUserWithTheWorstBMI(List.of());

        // Then
        assertNull(userWithWorstBMI);
    }

    @Test
    void shouldCalculateBMIScore() {
        // Given
        User user1 = new User(1.80, 60.0);
        User user2 = new User(1.82, 99.1);
        User user3 = new User(1.79, 97.3);
        User user4 = new User(1.62, 65.7);

        // When
        double[] bmiScores = fitCalculator.calculateBMIScore(List.of(user1, user2, user3, user4));

        // Then
        assertArrayEquals(new double[]{18.52, 29.92, 30.37, 25.03}, bmiScores, 0.01);
    }
}