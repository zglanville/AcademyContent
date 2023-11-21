package za.co.wethinkcode.robotworlds.Server.worldCommands.ServerAdminCommand;

import za.co.wethinkcode.robotworlds.Server.ServerMain;
import za.co.wethinkcode.robotworlds.Server.robot.RobotHandler;
import za.co.wethinkcode.robotworlds.Server.worldCommands.ShutdownCommand;

import java.io.IOException;

/**
 * Class to purge all availalbe robots an shutdown the world
 * @author Issa
 */
public class QuitCommand {

    public QuitCommand() {
        purgeAll();
    }

    /**
     * Purges a single robot at a time. relies on the create command
     * to run the loop
     * @return true
     */
    public boolean purgeAll() {
        for (RobotHandler robotHandler : ServerMain.robots) {
            if (robotHandler != null) {
                try {
                    robotHandler.getSocket().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ServerMain.robots.remove(robotHandler);
                robotHandler.getRobot().handleCommand(new ShutdownCommand());
            }
        }
        return true;
    }
}