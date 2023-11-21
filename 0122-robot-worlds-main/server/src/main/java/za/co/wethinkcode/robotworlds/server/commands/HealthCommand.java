package za.co.wethinkcode.robotworlds.server.commands;

import za.co.wethinkcode.robotworlds.server.robot.Robot;

public class HealthCommand extends Command{


    public HealthCommand() {
        super("health");
    }

    @Override
    public boolean execute(Robot target) {
        target.setStatus("The health of your za.co.wethinkcode.server.robot is "+ target.getCurrentHealth()+".");
        return true;
    }
}
