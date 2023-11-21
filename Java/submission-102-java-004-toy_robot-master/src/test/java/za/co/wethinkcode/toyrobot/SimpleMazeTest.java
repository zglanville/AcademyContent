package za.co.wethinkcode.toyrobot;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.toyrobot.maze.SimpleMaze;
import za.co.wethinkcode.toyrobot.maze.Maze;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleMazeTest {

    @Test
    void testSimpleMazeIsSimple() {
        Maze maze = new SimpleMaze();
        assertEquals(1, maze.getObstacles().size());
    }

}