package za.co.wethinkcode.robotworlds.server.robot.robotMakes;

import za.co.wethinkcode.robotworlds.server.robot.Robot;
import za.co.wethinkcode.robotworlds.server.world.IWorld;
import za.co.wethinkcode.robotworlds.server.configReader.ConfigReader;

public class Sniper extends Robot {

    public Sniper(String name, ConfigReader config) {
        super(name);
        this.config = config;
        this.status = "Ready";
        this.currentDirection = IWorld.Direction.NORTH;
        this.totalHealth = Integer.parseInt(config.getSniperShield())+1;
        this.currentHealth = totalHealth;
        this.vision = Integer.parseInt(config.getSniperVision());
        this.bulletRange = Integer.parseInt(config.getSniperBullets());
        this.bullets = Integer.parseInt(config.getBullets()) - Integer.parseInt(config.getSniperBullets());
        this.currentBullets = bullets;
        this.make = "sniper";
    }
}
