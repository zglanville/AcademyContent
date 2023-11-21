package za.co.wethinkcode.toyrobot;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.toyrobot.world.Obstacle;
import za.co.wethinkcode.toyrobot.world.SquareObstacle;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObstacleTest {
    @Test
    void testObstacleCorrectPosition11(){
        Obstacle obstacle = new SquareObstacle(1,1);
        assertTrue(obstacle.blocksPosition(new Position(1,1)));
    }

    @Test
    void testObstacleCorrectPosition22(){
        Obstacle obstacle = new SquareObstacle(1,1);
        assertTrue(obstacle.blocksPosition(new Position(2,2)));
    }

    @Test
    void testObstacleCorrectPosition33(){
        Obstacle obstacle = new SquareObstacle(1,1);
        assertTrue(obstacle.blocksPosition(new Position(3,3)));
    }

    @Test
    void testObstacleCorrectPosition44(){
        Obstacle obstacle = new SquareObstacle(1,1);
        assertTrue(obstacle.blocksPosition(new Position(4,4)));
    }

    @Test
    void testObstacleCorrectPosition55(){
        Obstacle obstacle = new SquareObstacle(1,1);
        assertTrue(obstacle.blocksPosition(new Position(5,5)));
    }

    @Test
    void testObstacleIncorrectPositions(){
        Obstacle obstacle = new SquareObstacle(1,1);
        for (int i = 6; i < 100; i++) {
            for (int j = 6; j < 200; j++) {
                assertFalse(obstacle.blocksPosition(new Position(i,j)));
            }
        }
    }

    @Test
    void testBlockPathUP(){
        Obstacle obstacle = new SquareObstacle(1,1);
        assertTrue(obstacle.blocksPath(new Position(1,0), new Position(1,10)));
        assertTrue(obstacle.blocksPath(new Position(2,0), new Position(2, 20)));
        assertTrue(obstacle.blocksPath(new Position(3,0), new Position(3, 30)));
        assertTrue(obstacle.blocksPath(new Position(4,0), new Position(4, 40)));
        assertTrue(obstacle.blocksPath(new Position(5,0), new Position(5, 50)));
        assertFalse(obstacle.blocksPath(new Position(1,10), new Position(1,20)));
        assertFalse(obstacle.blocksPath(new Position(2,20), new Position(2, 40)));
        assertFalse(obstacle.blocksPath(new Position(3,30), new Position(3, 60)));
        assertFalse(obstacle.blocksPath(new Position(4,40), new Position(4, 80)));
        assertFalse(obstacle.blocksPath(new Position(5,50), new Position(5, 100)));
    }

    @Test
    void testBlockPathRIGHT(){
        Obstacle obstacle = new SquareObstacle(1,1);
        assertTrue(obstacle.blocksPath(new Position(0,1), new Position(10,1)));
        assertTrue(obstacle.blocksPath(new Position(0,2), new Position(20, 2)));
        assertTrue(obstacle.blocksPath(new Position(0,3), new Position(30, 3)));
        assertTrue(obstacle.blocksPath(new Position(0,4), new Position(40, 4)));
        assertTrue(obstacle.blocksPath(new Position(0,5), new Position(50, 5)));
        assertFalse(obstacle.blocksPath(new Position(10,1), new Position(20,1)));
        assertFalse(obstacle.blocksPath(new Position(20,2), new Position(40, 2)));
        assertFalse(obstacle.blocksPath(new Position(30,3), new Position(60, 3)));
        assertFalse(obstacle.blocksPath(new Position(40,4), new Position(80, 4)));
        assertFalse(obstacle.blocksPath(new Position(50,5), new Position(100, 5)));
    }

    @Test
    void testBlockPathDOWN(){
        Obstacle obstacle = new SquareObstacle(1,1);
        assertTrue(obstacle.blocksPath(new Position(1,10), new Position(1,-10)));
        assertTrue(obstacle.blocksPath(new Position(2,10), new Position(2, -20)));
        assertTrue(obstacle.blocksPath(new Position(3,10), new Position(3, -30)));
        assertTrue(obstacle.blocksPath(new Position(4,10), new Position(4, -40)));
        assertTrue(obstacle.blocksPath(new Position(5,10), new Position(5, -50)));
        assertFalse(obstacle.blocksPath(new Position(1,0), new Position(1,-20)));
        assertFalse(obstacle.blocksPath(new Position(2,0), new Position(2, -40)));
        assertFalse(obstacle.blocksPath(new Position(3,0), new Position(3, -60)));
        assertFalse(obstacle.blocksPath(new Position(4,0), new Position(4, -80)));
        assertFalse(obstacle.blocksPath(new Position(5,0), new Position(5, -100)));
    }

    @Test
    void testBlockPathLEFT(){
        Obstacle obstacle = new SquareObstacle(1,1);
        assertTrue(obstacle.blocksPath(new Position(10,1), new Position(-10,1)));
        assertTrue(obstacle.blocksPath(new Position(10,2), new Position(-20, 2)));
        assertTrue(obstacle.blocksPath(new Position(10,3), new Position(-30, 3)));
        assertTrue(obstacle.blocksPath(new Position(10,4), new Position(-40, 4)));
        assertTrue(obstacle.blocksPath(new Position(10,5), new Position(-50, 5)));
        assertFalse(obstacle.blocksPath(new Position(-10,1), new Position(-20,1)));
        assertFalse(obstacle.blocksPath(new Position(-20,2), new Position(-40, 2)));
        assertFalse(obstacle.blocksPath(new Position(-30,3), new Position(-60, 3)));
        assertFalse(obstacle.blocksPath(new Position(-40,4), new Position(-80, 4)));
        assertFalse(obstacle.blocksPath(new Position(-50,5), new Position(-100, 5)));
    }

    @Test
    void testObstacleSize() {
        Obstacle obstacle = new SquareObstacle(1,1);
        assertEquals(1, obstacle.getBottomLeftX());
        assertEquals(1, obstacle.getBottomLeftY());
        assertTrue(obstacle.blocksPosition(new Position(4,4)));
        assertEquals(5, obstacle.getSize());
    }
}
