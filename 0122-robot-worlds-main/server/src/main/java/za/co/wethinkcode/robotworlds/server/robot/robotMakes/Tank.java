package za.co.wethinkcode.robotworlds.server.robot.robotMakes;

import za.co.wethinkcode.robotworlds.server.world.IWorld;
import za.co.wethinkcode.robotworlds.server.configReader.ConfigReader;
import za.co.wethinkcode.robotworlds.server.robot.Robot;

public class Tank extends Robot {

    public Tank(String name, ConfigReader config){
        super(name);
        this.config = config;
        this.status = "Ready";
        this.currentDirection = IWorld.Direction.NORTH;
        this.totalHealth = Integer.parseInt(config.getTankShield()) +1;
        this.currentHealth = totalHealth;
        this.vision = Integer.parseInt(config.getTankVision());
        this.bulletRange = Integer.parseInt(config.getTankBullets());
        this.bullets = Integer.parseInt(config.getBullets()) - Integer.parseInt(config.getTankBullets());
        this.currentBullets = bullets;
        this.make = "tank";
    }

}
