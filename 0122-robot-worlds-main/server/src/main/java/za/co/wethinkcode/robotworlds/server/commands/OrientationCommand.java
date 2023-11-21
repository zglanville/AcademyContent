package za.co.wethinkcode.robotworlds.server.commands;

import za.co.wethinkcode.robotworlds.server.robot.Robot;

public class OrientationCommand extends Command{
    /**
     * Constructor for orientation
     * */
    public OrientationCommand() {
        super("orientation");
    }

    /**
     * Sets the status to show what direction the za.co.wethinkcode.server.robot is facing.
     * @param target: receives the za.co.wethinkcode.server.robot as a parameter
     * @return: returns za.co.wethinkcode.server.robot.
     * */
    @Override
    public boolean execute(Robot target) {
        target.setStatus("I am facing " + target.getWorld().getCurrentDirection());
        return true;
    }
}
