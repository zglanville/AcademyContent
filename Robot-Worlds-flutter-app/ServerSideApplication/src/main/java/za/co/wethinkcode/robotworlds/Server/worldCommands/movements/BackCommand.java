package za.co.wethinkcode.robotworlds.Server.worldCommands.movements;

import za.co.wethinkcode.robotworlds.Server.robot.Robot;
import za.co.wethinkcode.robotworlds.Server.world.obstacles.MovementStatus;
import za.co.wethinkcode.robotworlds.Server.worldCommands.Command;

/**
 *Method to handle the Back command movements
 * @author  Tshepo
 * @author Issa
 */
public class BackCommand extends Command {

    public BackCommand(String argument) {
        super("back", argument);
    }

    @Override
    public boolean execute(Robot target){
        int nrSteps = Integer.parseInt(getArgument());
        MovementStatus state = target.updatePosition(-nrSteps);
        System.out.println(state);
        if (state == MovementStatus.successful){
            target.setRobotStatus("normal");
            target.setStatus("Moved backward by "+nrSteps+" steps.");
        } else if( state == MovementStatus.fell){
            target.setRobotStatus("dead");
            target.setStatus("Robot is dead");
            this.message = "fell";
        } else if (state == MovementStatus.edge){
            target.setRobotStatus("obstructed");
            // TODO getCurrentDirection should be swapped 180 since we move back
            target.setStatus("Robot is at the " + target.getCurrentDirection() + " edge");
            this.message = "At the " + target.getCurrentDirection() + " edge";
        } else if( state == MovementStatus.obstructed){
            target.setRobotStatus("obstructed");
            target.setStatus("Robot is obstructed");
            this.message = "Obstructed";
        } else if( state == MovementStatus.mine){
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
