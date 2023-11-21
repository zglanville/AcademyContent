package za.co.wethinkcode.robotworlds.TODOserver.maze;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Server.maze.RandomMaze;
import za.co.wethinkcode.robotworlds.Server.robot.RobotWorldConfiguration;
import za.co.wethinkcode.robotworlds.Server.world.obstacles.Obstacle;
import za.co.wethinkcode.robotworlds.TODOserver.TODORobot.RobotConfigurationTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RandomMazeTest {

    // @Test
    // void createdObstacles() {
    //     RandomMaze randomMaze = new RandomMaze();
    //     assertTrue(randomMaze.getObstacles().size()>0);
    // }

    // @Test
    // void createdPits() {
    //     RandomMaze randomMaze = new RandomMaze();
    //     for (Obstacle obstacle: randomMaze.getObstacles())
    //         if (obstacle.getIsPit()) {
    //             assertTrue(obstacle.getIsPit());
    //             return;
    //         }
    //     assertTrue(false);
    // }

    // @Test
    // void getName() {
    //     RandomMaze randomMaze = new RandomMaze();
    //     assertTrue(!randomMaze.getName().isBlank());
    // }

    @Test
    void testEdges() {

        // TODO: these tests
        RobotWorldConfiguration config = new RobotWorldConfiguration();
        RandomMaze maze = new RandomMaze(config);
        System.out.println(maze.getEdges());

        assertTrue(true);
    }
}