package za.co.wethinkcode.robotworlds.Server.worldCommands.ServerAdminCommand;

import za.co.wethinkcode.robotworlds.Server.ServerMain;
import za.co.wethinkcode.robotworlds.Server.robot.RobotHandler;
import za.co.wethinkcode.robotworlds.Server.worldCommands.ShutdownCommand;

import java.io.IOException;

/**
 * Method to handle the Server admin command to purge a robot
 * @author Issa
 */
public class PurgeCommand{
        String robotName;

        public PurgeCommand(String robotName){
            this.robotName = robotName;
            purgeRobot(getRobotToPurge());
        }

    /**
     * Get the robot to be purged
     * @return the robothandler object
     */
    private RobotHandler getRobotToPurge(){
            for (RobotHandler robotHandler: ServerMain.robots){
                if (robotHandler.getRobot()!=null){
                    if (robotHandler.getRobot().getName().equalsIgnoreCase(this.robotName)){
                        return robotHandler;
                    }
                }
            }
            return null;
        }

    /**
     * purge the robot provided.
     * @param robotHandler
     * @return true if successful; false if not.
     */
    public boolean purgeRobot(RobotHandler robotHandler){
            if (robotHandler != null){
                try {
                    robotHandler.getSocket().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ServerMain.robots.remove(robotHandler);
                robotHandler.getRobot().handleCommand(new ShutdownCommand());
                return true;
            }
            return false;
        }
}
