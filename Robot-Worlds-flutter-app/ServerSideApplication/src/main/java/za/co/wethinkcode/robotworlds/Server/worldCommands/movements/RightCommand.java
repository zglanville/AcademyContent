/**
 * @author Tshepo
 */
package za.co.wethinkcode.robotworlds.Server.worldCommands.movements;

import za.co.wethinkcode.robotworlds.Server.robot.Robot;
import za.co.wethinkcode.robotworlds.Server.worldCommands.Command;

/**
 * Method to handle the right command movement
 * @author Issa
 * @author Tshepo
 */

public class RightCommand extends Command {

    public RightCommand() {super("right");}

    @Override
    public boolean execute(Robot target) {
        target.updateDirection(true);
        target.setStatus("Turned right.");
        return true;
    }
}
