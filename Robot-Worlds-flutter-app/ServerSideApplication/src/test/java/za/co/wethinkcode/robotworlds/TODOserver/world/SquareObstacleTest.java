package za.co.wethinkcode.robotworlds.TODOserver.world;

import org.junit.Test;
import za.co.wethinkcode.robotworlds.Server.world.obstacles.Obstacle;
import za.co.wethinkcode.robotworlds.Server.world.obstacles.SquareObstacle;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SquareObstacleTest {
    @Test
    public void testObstaclePosition(){
        Obstacle obstacle = new SquareObstacle(1,1);
        assertEquals(1, obstacle.getBottomLeftX());
        assertEquals(1, obstacle.getBottomLeftY());
    }

    @Test
    public void testObstacleSize() {
        Obstacle obstacle = new SquareObstacle(1, 1);
        assertEquals(5, obstacle.getSize());
    }
}
