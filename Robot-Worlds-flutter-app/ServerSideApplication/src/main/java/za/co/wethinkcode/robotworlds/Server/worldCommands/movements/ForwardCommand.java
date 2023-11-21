/**
 * @author Thonifho & Tshepo
 */
package za.co.wethinkcode.robotworlds.Server.worldCommands.movements;

import za.co.wethinkcode.robotworlds.Server.robot.Robot;
import za.co.wethinkcode.robotworlds.Server.world.obstacles.MovementStatus;
import za.co.wethinkcode.robotworlds.Server.worldCommands.Command;

/**
 * Method to handle the forward command
 * @author Issa
 * @author Tshepo
 */

public class ForwardCommand extends Command {

    public ForwardCommand(String argument) {
        super("forward", argument);
    }

    @Override
    public boolean execute(Robot target) {
        int nrSteps = Integer.parseInt(getArgument());
        MovementStatus state = target.updatePosition(nrSteps);
        if (state == MovementStatus.successful) {
            target.setRobotStatus("normal");
            target.setStatus("Moved forward by "+nrSteps+" steps.");
        } else if (state == MovementStatus.fell) {
            target.setRobotStatus("dead");
            target.setStatus("Robot is dead");
            this.message = "fell";
        } else if (state == MovementStatus.obstructed) {
            target.setRobotStatus("obstructed");
            target.setStatus("Robot is obstructed");
            this.message = "Obstructed";
        } else if (state == MovementStatus.edge) {
            target.setRobotStatus("obstructed");
            target.setStatus("Robot is at the " + target.getCurrentDirection() + " edge");
            this.message = "At the " + target.getCurrentDirection() + " edge";
        } else if (state == MovementStatus.mine) {
            target.setShields("mine");
            if (target.getShields() > 0) {
                target.setRobotStatus("hitmine");
            } else {
                target.setRobotStatus("dead");
            }
            target.setStatus("Robot has stepped on a mine");
            this.message = "Mine";
        }
        return true;
    }
}
