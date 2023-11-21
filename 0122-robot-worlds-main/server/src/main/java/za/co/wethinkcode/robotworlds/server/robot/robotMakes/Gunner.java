package za.co.wethinkcode.robotworlds.server.robot.robotMakes;

import za.co.wethinkcode.robotworlds.server.world.IWorld;
import za.co.wethinkcode.robotworlds.server.robot.Robot;
import za.co.wethinkcode.robotworlds.server.configReader.ConfigReader;

public class Gunner extends Robot {

    public Gunner(String name, ConfigReader config) {
        super(name);
        this.config = config;
        this.status = "Ready";
        this.currentDirection = IWorld.Direction.NORTH;
        this.totalHealth = Integer.parseInt(config.getGunnerShield())+1;
        this.currentHealth = totalHealth;
        this.vision = Integer.parseInt(config.getGunnerVision());
        this.bulletRange = Integer.parseInt(config.getGunnerBullets());
        this.bullets = Integer.parseInt(config.getBullets()) - Integer.parseInt(config.getGunnerBullets());
        this.currentBullets = bullets;
        this.make = "gunner";
    }
}
