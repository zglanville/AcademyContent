package za.co.wethinkcode.robotworlds.Server.worldCommands.ServerAdminCommand;

import za.co.wethinkcode.robotworlds.Server.ServerMain;
import za.co.wethinkcode.robotworlds.Server.robot.Robot;
import za.co.wethinkcode.robotworlds.Server.robot.RobotHandler;
import za.co.wethinkcode.robotworlds.Server.robot.RobotStatus;
import za.co.wethinkcode.robotworlds.Server.world.Direction;
import za.co.wethinkcode.robotworlds.Server.world.Position;

import java.util.List;

/**
 * Class to handle to robots command; give a representation of all the robots in the world.
 * @author Issa
 */
public class RobotsCommand {

    public RobotsCommand() {
        getRobots();
    }

    /**
     * Get all the robots available in the world
     * @return true
     */
    public boolean getRobots() {
        Robot robot = null;
        int robotsNotLaunched = 0;
        List<RobotHandler> robotHandlerList = ServerMain.robots;
        int i = 1;
        System.out.println("Robots present in the World: ");

        if (robotHandlerList.isEmpty()) {
            System.out.println("No robots in this world");
            return true;
        }

        for (RobotHandler robots : robotHandlerList) {
            robot = robots.getRobot();
            if (robot == null) {
                robotsNotLaunched++;
            } else {

                String robotName = robot.getName();
                Position robotPosition = robot.getPosition();
                Direction robotDirection = robot.getCurrentDirection();
                int robotShields = robot.getShields();
                int robotShots = robot.getShots();
                RobotStatus robotStatus = robot.getRobotStatus();

                System.out.printf("%d: %s\nPosition: [%d,%d]\nDirection: %s\nShields: %d\nShots: %d\nStatus: %s%n",
                        i, robotName, robotPosition.getX(), robotPosition.getY(), robotDirection.toString(), robotShields,
                        robotShots, robotStatus.toString());
                i++;
            }
        }
        if(robotsNotLaunched>0){ System.out.printf("%nNote: Clients connected but not launched: %d%n", robotsNotLaunched); }
        return true;
    }
}
