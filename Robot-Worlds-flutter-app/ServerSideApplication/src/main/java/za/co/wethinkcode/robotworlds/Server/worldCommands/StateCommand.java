package za.co.wethinkcode.robotworlds.Server.worldCommands;

import za.co.wethinkcode.robotworlds.Server.robot.Robot;

/**
 * Method to not the state of the robot
 * @author Issa
 */
public class StateCommand extends Command{
    public StateCommand(){super("state");}

    @Override
    public boolean execute(Robot target) {
        return true;
    }
}
