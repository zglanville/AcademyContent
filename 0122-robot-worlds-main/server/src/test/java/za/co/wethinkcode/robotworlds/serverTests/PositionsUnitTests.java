package za.co.wethinkcode.robotworlds.serverTests;

import org.junit.Test;
import za.co.wethinkcode.robotworlds.server.position.Position;

import static org.junit.jupiter.api.Assertions.*;

public class PositionsUnitTests {
    @Test
    public void shouldKnowPosition() {
        Position position = new Position(10, 20);
        assertEquals(10, position.getX());
        assertEquals(20, position.getY());
    }

    @Test
    public void insidePosition() {
        Position TopLX = new Position(-10, 10);
        Position bottomRY = new Position(0, 0);
        assertTrue((new Position(-5,5)).isIn(TopLX, bottomRY));
        assertFalse((new Position(-11,0)).isIn(TopLX, bottomRY));
        assertFalse((new Position(100,-100)).isIn(TopLX, bottomRY));
    }

}
