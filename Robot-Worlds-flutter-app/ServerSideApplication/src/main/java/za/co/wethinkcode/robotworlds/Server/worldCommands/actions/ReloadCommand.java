package za.co.wethinkcode.robotworlds.Server.worldCommands.actions;

import za.co.wethinkcode.robotworlds.Server.robot.Robot;
import za.co.wethinkcode.robotworlds.Server.worldCommands.Command;

/**
 * Implements the reloading of the weapon by the robot.
 * Thread to sleep for the configured amount of time and then
 * reload weaponry.
 * Action: Issa
 */
public class ReloadCommand extends Command {
    public ReloadCommand(){super("reload");}

    @Override
    public boolean execute(Robot target) {
        target.setShots("reload");
        try {
            Thread.sleep(1000*target.getReload());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }
}
