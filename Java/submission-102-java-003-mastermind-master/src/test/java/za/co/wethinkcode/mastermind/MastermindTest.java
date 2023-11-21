package za.co.wethinkcode.mastermind;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MastermindTest {

    @Test
    void correct_digits() {
        Mastermind mastermind = new Mastermind();
        ArrayList<Integer> expectedResult = new ArrayList<>();
        expectedResult.add(1);
        expectedResult.add(1);
        assertEquals(expectedResult, mastermind.correct_digits("1234", "1563"));
    }
}