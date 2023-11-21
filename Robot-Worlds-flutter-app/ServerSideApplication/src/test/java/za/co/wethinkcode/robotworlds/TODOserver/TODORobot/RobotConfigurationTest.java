package za.co.wethinkcode.robotworlds.TODOserver.TODORobot;
/**
 * Testing the robot configuration class
 */

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Server.robot.RobotWorldConfiguration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RobotConfigurationTest {

    @Test
    void ConfigInitTest() {
        RobotWorldConfiguration robotConfiguration = new RobotWorldConfiguration();
        int visibility = robotConfiguration.getVisibility();
        assertTrue(visibility > -1);
    }
}
