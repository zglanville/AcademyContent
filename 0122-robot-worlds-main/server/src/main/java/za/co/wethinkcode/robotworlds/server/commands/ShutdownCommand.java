package za.co.wethinkcode.robotworlds.server.commands;

import za.co.wethinkcode.robotworlds.server.robot.Robot;

public class ShutdownCommand extends Command {

    /**
     * Constructor for ShutdownCommand;
     * */
    public ShutdownCommand() {
        super ("off");
    }

    /**
     * Execute override for the shutdown, does what it needs to in execute
     *
     * @param target: takes the za.co.wethinkcode.server.robot as a parameter
     * @return: false to turn off the za.co.wethinkcode.server.robot.
     * */
    @Override
    public boolean execute(Robot target) {
        target.setStatus("Shutting down...");
        return true;
    }
}
