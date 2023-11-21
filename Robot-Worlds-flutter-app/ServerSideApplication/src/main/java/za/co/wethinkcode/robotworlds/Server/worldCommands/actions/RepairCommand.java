package za.co.wethinkcode.robotworlds.Server.worldCommands.actions;

import za.co.wethinkcode.robotworlds.Server.robot.Robot;
import za.co.wethinkcode.robotworlds.Server.worldCommands.Command;


/**
 * Repair command configures the robots shield back to maximum.
 * Action: Issa
 */
public class RepairCommand extends Command {
    public RepairCommand(){super("repair");}

    @Override
    public boolean execute(Robot target) {
        target.setRobotStatus("repair");
        target.setShields("repair");
        try {
            Thread.sleep(1000*target.getRepair());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }
}
