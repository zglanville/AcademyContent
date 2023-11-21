/**
 * @author Thonifho & Tshepo
 */
package za.co.wethinkcode.robotworlds.Server.worldCommands;

import za.co.wethinkcode.robotworlds.Server.robot.Robot;

/**
 * Help command to provide the commands to the client
 * @author Tshepo
 * @author Issa
 */
public class HelpCommand extends Command {

    public HelpCommand() {
        super("help");
    }

    @Override
    public boolean execute(Robot target) {
        String helpMessage =  "I can understand these commands:\n" +
                "OFF  - Shut down robot\n" +
                "HELP - provide information about commands\n" +
                "FORWARD - move forward by specified number of steps, e.g. 'FORWARD 10'\n" +
                "BACK - move backward by specified number of steps, e.g. 'BACK 10'\n" +
                "TURN <LEFT/RIGHT> - turns the robot left/turns the robot right.\n" +
                "FIRE - fires a shot to the direction the robot is facing\n" +
                "MINE - sets a mine at the current position of the robot and moves the robot forward, " +
                "if the robot cannot move forward it will step into the mine and shield will be impacted"+
                "RELOAD - sets the robot shield back to default, a delay of 7 seconds is applied."+
                "REPAIR - repairs the robot shield to the default value, a delay of 7 seconds is applied."+
                "LAUNCH - to launch a robot into the World e.g launch robot <robotName>.";

        target.setStatus(helpMessage);
        return target.getStatus() == helpMessage;
    }
}
