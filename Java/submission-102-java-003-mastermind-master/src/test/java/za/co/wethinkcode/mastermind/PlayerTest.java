package za.co.wethinkcode.mastermind;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void getGuess() {
        Player player = new Player(new ByteArrayInputStream("1234".getBytes()));
        assertEquals("1234", player.getGuess());
    }

    @Test
    void getGuesses() {
        Player player = new Player();
        assertEquals(12, player.getGuesses());
    }
}