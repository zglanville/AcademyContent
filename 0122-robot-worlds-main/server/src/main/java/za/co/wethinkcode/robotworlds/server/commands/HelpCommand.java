package za.co.wethinkcode.robotworlds.server.commands;

import za.co.wethinkcode.robotworlds.server.robot.Robot;

public class HelpCommand extends Command {

    public HelpCommand() {
        super("help");
    }

    @Override
    public boolean execute(Robot target) {
        target.setStatus("I can understand these za.co.wethinkcode.server.commands:\n" +
                "OFF  - Shut down za.co.wethinkcode.server.robot\n" +
                "HELP - provide information about za.co.wethinkcode.server.commands\n" +
                "FORWARD - move forward by specified number of steps, e.g. 'FORWARD 10'\n" +
                "BACK - move back by specified number of steps, e.g. 'BACK 10'");
        return true;
    }
}
