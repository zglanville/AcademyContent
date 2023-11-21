/**
 * @authors Thonifho & Tshepo
 */
package za.co.wethinkcode.robotworlds.Server.worldCommands;

import za.co.wethinkcode.robotworlds.Server.robot.Robot;

import java.io.IOException;

/**
 * Method to shutdown the robot on command
 * @author Tshepo
 * @author Issa
 */
public class ShutdownCommand extends Command {

    public ShutdownCommand() {
        super("off");
    }

    @Override
    public boolean execute(Robot target) {
        target.setStatus("Shutting down...");
        try {
            target.getSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
