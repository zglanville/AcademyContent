package za.co.wethinkcode.robotworlds.server.commands;

import za.co.wethinkcode.robotworlds.server.robot.Robot;

public class TurnCommand extends Command{

    /**
     * Constructor for the TurnCommand
     * @param argument, Takes in argument of either left or right.
     * */
    public TurnCommand(String argument) {
        super("turn", argument);
    }

    /**
     * Override for execute: - initialize an index za.co.wethinkcode.server.position for the direction,
     * check if the command is right, if it is increase 1 on the enums ordinal direction;
     * (check if it is the end and reset).
     * else check if it is left instead. (check if it is at the end reset.)
     * @param target: Takes the za.co.wethinkcode.server.robot as a target.
     * @return: True
     * */
    @Override
    public boolean execute(Robot target) {
        target.getWorld().updateDirection(getArgument().equals("right"));
        target.setStatus("Turned " + getArgument() + ".");
        return true;
    }
}
