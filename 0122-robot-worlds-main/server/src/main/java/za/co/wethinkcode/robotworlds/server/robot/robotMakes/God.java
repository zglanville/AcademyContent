package za.co.wethinkcode.robotworlds.server.robot.robotMakes;

import za.co.wethinkcode.robotworlds.server.world.IWorld;
import za.co.wethinkcode.robotworlds.server.configReader.ConfigReader;
import za.co.wethinkcode.robotworlds.server.robot.Robot;

public class God extends Robot {

    public God (String name, ConfigReader config) {
        super(name);
        this.config = config;
        this.status = "Ready";
        this.currentDirection = IWorld.Direction.NORTH;
        this.totalHealth = 10_000;
        this.currentHealth = totalHealth;
        this.vision = 100;
        this.bulletRange = 100;
        this.bullets = 1000;
        this.currentBullets = bullets;
        this.make = "god";
    }
}
