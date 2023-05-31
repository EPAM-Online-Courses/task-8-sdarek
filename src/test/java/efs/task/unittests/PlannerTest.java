package efs.task.unittests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
class PlannerTest {

    private Planner planner;

    @BeforeEach
    void setUp() {
        planner = new Planner();
    }

    @ParameterizedTest(name = "Activity Level: {0}")
    @CsvSource({"NO_ACTIVITY, 1681", "LOW_ACTIVITY, 1926", "MEDIUM_ACTIVITY, 2171", "HIGH_ACTIVITY, 2417", "EXTREME_ACTIVITY, 2662"})
    void shouldCalculateDailyCaloriesDemand(ActivityLevel activityLevel, int expectedCalories) {
        // Given
        User user = TestConstants.TEST_USER;

        // When
        int calculatedCalories = planner.calculateDailyCaloriesDemand(user, activityLevel);

        // Then
        assertEquals(expectedCalories, calculatedCalories);
    }

    @Test
    void shouldCalculateDailyIntake() {
        // Given
        User user = TestConstants.TEST_USER;
        DailyIntake expectedIntake = TestConstants.TEST_USER_DAILY_INTAKE;

        // When
        DailyIntake calculatedIntake = planner.calculateDailyIntake(user);

        // Then
        assertEquals(expectedIntake, calculatedIntake);
    }
}