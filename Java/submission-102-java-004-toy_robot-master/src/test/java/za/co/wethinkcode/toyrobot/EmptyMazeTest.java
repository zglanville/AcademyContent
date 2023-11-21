package za.co.wethinkcode.toyrobot;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import za.co.wethinkcode.toyrobot.maze.EmptyMaze;
import za.co.wethinkcode.toyrobot.maze.Maze;

public class EmptyMazeTest {

    @Test
    void testEmptyMazeIsEmpty() {
        Maze maze = new EmptyMaze();
        int ExpectedObstacleNumber = 0;
        assertTrue(ExpectedObstacleNumber == maze.getObstacles().size());
        assertFalse(ExpectedObstacleNumber > 0);
        assertFalse(maze.getObstacles().size() > 0);
    }
}
