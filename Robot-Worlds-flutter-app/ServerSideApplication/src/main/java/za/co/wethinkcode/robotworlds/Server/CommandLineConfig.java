
/**
 * Reads commandline arguments when launching server, sends data to be configured
 * @author sgerber
 */
package za.co.wethinkcode.robotworlds.Server;

import picocli.CommandLine;
import za.co.wethinkcode.robotworlds.Server.robot.RobotWorldConfiguration;

public class CommandLineConfig implements Runnable {

    private static RobotWorldConfiguration config = new RobotWorldConfiguration();

    // World defaults are fetched from config file here via RobotWorldConfiguration
    // and overwritten by CL values if they were given

    @CommandLine.Option(names = { "-p" }, description = "Port no.")
    int portNo = config.getPort();

    @CommandLine.Option(names = { "-s" }, description = "World size")
    int worldSize = config.getSize();

    @CommandLine.Option(names = { "-o" }, description = "Placed Obstacle")
    String placedObstacle = config.getPlacedObstacle();

    public static RobotWorldConfiguration getConfig() {
        return config;
    }

    @Override
    public void run() {
        // System.out.println("CommandLineConfig is run");
        config.setCommandLineParams(portNo, worldSize, placedObstacle);
    }
}
