package za.co.wethinkcode.robotworlds.serverTests;

import org.junit.Test;
import za.co.wethinkcode.robotworlds.server.configReader.ConfigReader;
import za.co.wethinkcode.robotworlds.server.maze.Maze;
import za.co.wethinkcode.robotworlds.server.maze.RandomMap;
import za.co.wethinkcode.robotworlds.server.robot.Robot;
import za.co.wethinkcode.robotworlds.server.robot.robotMakes.Gunner;
import za.co.wethinkcode.robotworlds.server.robot.robotMakes.Miner;
import za.co.wethinkcode.robotworlds.server.robot.robotMakes.Sniper;
import za.co.wethinkcode.robotworlds.server.robot.robotMakes.Tank;
import za.co.wethinkcode.robotworlds.server.world.TextWorld;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class RobotTests {
    @Test
    public void testTank() {
        Robot robot;
        ConfigReader configReader = new ConfigReader();
        Maze randomMap = new RandomMap(configReader);
        TextWorld world = new TextWorld(randomMap, configReader);
        robot = new Tank("test", configReader);
        assertEquals(9 ,robot.getCurrentHealth());
        assertEquals(5 ,robot.getVision());
        assertEquals(7 ,robot.getBullets());
        assertEquals(3, robot.getBulletRange());
    }

    @Test
    public void testGunner() {
        Robot robot;
        ConfigReader configReader = new ConfigReader();
        Maze randomMap = new RandomMap(configReader);
        TextWorld world = new TextWorld(randomMap, configReader);
        robot = new Gunner("test", configReader);
        assertEquals(6 ,robot.getCurrentHealth());
        assertEquals(10 ,robot.getVision());
        assertEquals(5 ,robot.getBullets());
        assertEquals(5, robot.getBulletRange());
    }

    @Test
    public void testSniper() {
        Robot robot;
        ConfigReader configReader = new ConfigReader();
        Maze randomMap = new RandomMap(configReader);
        TextWorld world = new TextWorld(randomMap, configReader);
        robot = new Sniper("test", configReader);
        assertEquals(4 ,robot.getCurrentHealth());
        assertEquals(30 ,robot.getVision());
        assertEquals(-5 ,robot.getBullets());
        assertEquals(15, robot.getBulletRange());
    }

    @Test
    public void testMiner() {
        Robot robot;
        ConfigReader configReader = new ConfigReader();
        Maze randomMap = new RandomMap(configReader);
        TextWorld world = new TextWorld(randomMap, configReader);
        robot = new Miner("test", configReader);
        assertEquals(6 ,robot.getCurrentHealth());
        assertEquals(10 ,robot.getVision());
        assertEquals(0 ,robot.getBullets());
        assertEquals(0, robot.getBulletRange());
    }


}
