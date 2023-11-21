package za.co.wethinkcode.robotworlds.Server.robot;

import java.io.*;
import java.util.Properties;

/**
 * Class reads and returns data from the JSON config file to the Server.
 * Action: Issa
 * @author sgerber
 */

public class RobotWorldConfiguration {

    private int visibility, reload, repair, mine, shield, shots, port, width, height, size;
    String placedObstacle;

    public RobotWorldConfiguration() {
        try {
            readConfig();
        } catch (Exception e) {
            System.out.println("Could not read data from configuration Data " + e);
        }
    }

    /**
     * Getter methods to return the world configurations.
     * @return world configurations
     */
    public int getVisibility() { return this.visibility; }
    public int getReload() { return this.reload; }
    public int getRepair() { return this.repair; }
    public int getMine() { return this.mine; }
    public int getShield() { return this.shield; }
    public int getShots() { return this.shots; }
    
    public String getPlacedObstacle() {
        return this.placedObstacle;
        // return this.placedObstacle.split(",");
    }

    public int getPort() { return this.port; }
    public int getHeight() { return this.height; }
    public int getWidth() { return this.width; }
    public int getSize() { return this.size; }

    /**
     * Reads world and robot defaults from config file and sets 
     * @return
     */
    public void readConfig() {
        try {
            FileReader reader = 
                new FileReader(
                    new File("ServerSideApplication/src/main/java/za/co/wethinkcode/robotworlds/Server/config.properties"));
    
            Properties config = new Properties();
            config.load(reader);

            shots = Integer.parseInt(config.getProperty("shots"));
            visibility = Integer.parseInt(config.getProperty("visibility"));
            reload = Integer.parseInt(config.getProperty("reload"));
            repair = Integer.parseInt(config.getProperty("repair"));
            mine = Integer.parseInt(config.getProperty("mine"));
            shield = Integer.parseInt(config.getProperty("shield"));
            port = Integer.parseInt(config.getProperty("port"));
            width = Integer.parseInt(config.getProperty("worldSize"));
            height = Integer.parseInt(config.getProperty("worldSize"));
            size = Integer.parseInt(config.getProperty("worldSize"));
            placedObstacle = config.getProperty("placedObstacle");

        } catch(Exception e) {
            System.out.println("Read Config error: " + e);
        }
    }

    public void setCommandLineParams(int portNo, int worldSize, String placedObstacle) {
        this.port = portNo;
        this.size = worldSize;
        this.placedObstacle = placedObstacle;
    }
}
