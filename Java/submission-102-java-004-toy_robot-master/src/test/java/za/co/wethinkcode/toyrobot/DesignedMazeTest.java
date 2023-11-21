package za.co.wethinkcode.toyrobot;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.toyrobot.maze.DesignedMaze;
import za.co.wethinkcode.toyrobot.maze.Maze;

import static org.junit.jupiter.api.Assertions.*;

public class DesignedMazeTest {

    @Test
    void testDesignedMazeIsDesigned() {
        Maze maze = new DesignedMaze();
        int ExpectedObstacleNumber = 20;
        assertTrue(ExpectedObstacleNumber == maze.getObstacles().size());
        assertTrue(ExpectedObstacleNumber > 0);
        assertTrue(maze.getObstacles().size() > 0);
    }
}
