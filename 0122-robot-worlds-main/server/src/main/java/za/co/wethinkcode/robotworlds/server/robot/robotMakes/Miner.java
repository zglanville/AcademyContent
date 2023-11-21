package za.co.wethinkcode.robotworlds.server.robot.robotMakes;

import za.co.wethinkcode.robotworlds.server.world.IWorld;
import za.co.wethinkcode.robotworlds.server.robot.Robot;
import za.co.wethinkcode.robotworlds.server.configReader.ConfigReader;

public class Miner extends Robot {

    public Miner(String name, ConfigReader config) {
        super(name);
        this.config = config;
        this.status = "Ready";
        this.currentDirection = IWorld.Direction.NORTH;
        this.totalHealth = Integer.parseInt(config.getMinerShield()) +1;
        this.currentHealth = totalHealth;
        this.vision = Integer.parseInt(config.getMinerVision());
        this.bulletRange = Integer.parseInt(config.getMinerBullets());
        this.bullets = Integer.parseInt(config.getBullets())*Integer.parseInt(config.getMinerBullets());
        this.currentBullets = bullets;
        this.make = "miner";
    }
}
