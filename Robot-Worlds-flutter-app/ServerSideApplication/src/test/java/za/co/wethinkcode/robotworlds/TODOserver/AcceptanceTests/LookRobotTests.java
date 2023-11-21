package za.co.wethinkcode.robotworlds.TODOserver.AcceptanceTests;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Server.RobotWorldClient;
import za.co.wethinkcode.robotworlds.Server.RobotWorldJsonClient;
import java.util.Objects;
import static org.junit.jupiter.api.Assertions.*;

/**
 * As a player
 * I want to launch my robot in the online robot world
 * So that I can break the record for the most robot kills
 */
class LookRobotTests {
    private final static int ITERATION_1_PORT_S1 = 5110; // 1x1, no obstacle
    private final static int ITERATION_2_PORT_S1 = 5210; // 1x1, no obstacle
    private final static int ITERATION_2_PORT_S2 = 5220; // 2x2, no obstacle
    private final static int ITERATION_2_PORT_S2_O10 = 5221; // 2x2, obstacle at [1,0]
    private final static String DEFAULT_IP = "localhost";
    private final RobotWorldClient serverClient1 = new RobotWorldJsonClient();
    private final RobotWorldClient serverClient2 = new RobotWorldJsonClient();
    private final RobotWorldClient serverClient3 = new RobotWorldJsonClient();
    private final RobotWorldClient serverClient4 = new RobotWorldJsonClient();
    private final RobotWorldClient serverClient5 = new RobotWorldJsonClient();
    private final RobotWorldClient serverClient6 = new RobotWorldJsonClient();
    private final RobotWorldClient serverClient7 = new RobotWorldJsonClient();
    private final RobotWorldClient serverClient8 = new RobotWorldJsonClient();


    @Test
    void validLookInEmptyWorldShouldReturnEmpty() {
        // Given that I am connected to a running Robot Worlds server
        // And the world is of size 1x1 (The world is configured or hardcoded to this size)

        serverClient1.connect(DEFAULT_IP, ITERATION_1_PORT_S1);
        String request = "{" +
                "  \"robot\": \"lookEmpty1x1World\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        serverClient1.sendRequest(request);
        assertTrue(serverClient1.isConnected());

        // When I send a valid look request to the server
        request = "{" +
                "  \"robot\": \"lookEmpty1x1World\"," +
                "  \"command\": \"look\"," +
                "  \"arguments\": []" +
                "}";
        JsonNode response = serverClient1.sendRequest(request);

        // Then I should get a valid response from the server
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());

        // And the objects list should have 4 objects
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("objects"));
        assertEquals(4, response.get("data").get("objects").size());

        // And I should also get the state of the robot
        assertNotNull(response.get("state"));

        serverClient1.disconnect();
    }

    @Test
    void seeAnObstacle() {
        // Given a world of size 2x2
        //and the world has an obstacle at coordinate [0,1]
        //and I have successfully launched a robot into the world
        //When I ask the robot to look
        //Then I should get an response back with an object of type OBSTACLE at a distance of 1 step.
        serverClient1.connect(DEFAULT_IP, ITERATION_2_PORT_S2_O10);
        String request = "{" +
                "  \"robot\": \"seeObstacle2x2\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        serverClient1.sendRequest(request);
        assertTrue(serverClient1.isConnected());

        // When I send a valid look request to the server
        request = "{" +
                "  \"robot\": \"seeObstacle2x2\"," +
                "  \"command\": \"look\"," +
                "  \"arguments\": []" +
                "}";
        JsonNode response = serverClient1.sendRequest(request);

        // Then I should get a valid response from the server
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());
        System.out.println(response);

        // And the objects list should contain an obstacle at distance 1
        JsonNode objectsNode = response.get("data").get("objects");
        Boolean testbool = false;
        String type;
        String distance;
        for (JsonNode value : objectsNode) {
            type = value.get("type").asText();
            distance = value.get("distance").asText();
            if (type.equalsIgnoreCase("OBSTACLE") && Objects.equals(distance, "1")) {
                testbool = true;
            }
        }
        assertTrue(testbool);
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("objects"));

        //assertEquals(5, response.get("data").get("objects").size());

        // And I should also get the state of the robot
        assertNotNull(response.get("state"));

        serverClient1.disconnect();
    }

    /*@Test
    void seeARobot() {
        // Given a world of size 2x2
        //and I have successfully launched two robots into the world
        //When I ask one of the robots to look
        //Then I should get an response back with an object of type ROBOT a distance of 1 step.
        serverClient1.connect(DEFAULT_IP, ITERATION_2_PORT_S2);
        String request = "{" +
                "  \"robot\": \"seeARobot1\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        serverClient1.sendRequest(request);
        assertTrue(serverClient1.isConnected());

        //Launch 2nd robot
        serverClient2.connect(DEFAULT_IP, ITERATION_2_PORT_S2);
        request = "{" +
                "  \"robot\": \"seeARobot2\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        serverClient2.sendRequest(request);

        //Launch 3rd robot, just in case
        serverClient3.connect(DEFAULT_IP, ITERATION_2_PORT_S2);
        request = "{" +
                "  \"robot\": \"seeARobot3\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        serverClient3.sendRequest(request);

        // When I send a valid look request to the server
        request = "{" +
                "  \"robot\": \"seeARobot1\"," +
                "  \"command\": \"look\"," +
                "  \"arguments\": []" +
                "}";
        JsonNode response = serverClient1.sendRequest(request);

        // Then I should get a valid response from the server
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());

        // And the objects list should contain a robot at distance 1
        JsonNode objectsNode = response.get("data").get("objects");
        Boolean testbool = false;
        String type;
        String distance;
        for (JsonNode value : objectsNode) {
            type = value.get("type").asText();
            distance = value.get("distance").asText();
            if (Objects.equals(type, "ROBOT") && Objects.equals(distance, "1")) {
                testbool = true;
            }
        }
        assertTrue(testbool);
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("objects"));
        //assertEquals(5, response.get("data").get("objects").size());

        // And I should also get the state of the robot
        assertNotNull(response.get("state"));

        serverClient1.disconnect();
    }*/

    @Test
    void seeARobotAndObstacle() {
        // Given a world of size 2x2
        //and the world has an obstacle at coordinate [0,1]
        //and I have successfully launched 8 robots into the world
        //When I ask the first robot to look
        //Then I should get an response back with
        //one object being an OBSTACLE that is one step away
        //and three objects should be ROBOTs that is one step away


        //Launch 1st robot
        serverClient1.connect(DEFAULT_IP, ITERATION_2_PORT_S2_O10);
        String request = "{" +
            "  \"robot\": \"seeRobotAndObstacle1\"," +
            "  \"command\": \"launch\"," +
            "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
        "}";
        serverClient1.sendRequest(request);
        assertTrue(serverClient1.isConnected());

        //Launch 2nd robot
        serverClient2.connect(DEFAULT_IP, ITERATION_2_PORT_S2_O10);
        request = "{" +
                "  \"robot\": \"seeRobotAndObstacle2\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        serverClient2.sendRequest(request);

        //Launch 3rd robot
        serverClient3.connect(DEFAULT_IP, ITERATION_2_PORT_S2_O10);
        request = "{" +
                "  \"robot\": \"seeRobotAndObstacle3\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        serverClient3.sendRequest(request);

        //Launch 4th robot
        serverClient4.connect(DEFAULT_IP, ITERATION_2_PORT_S2_O10);
        request = "{" +
                "  \"robot\": \"seeRobotAndObstacle4\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        serverClient4.sendRequest(request);

        //Launch 5th robot
        serverClient5.connect(DEFAULT_IP, ITERATION_2_PORT_S2_O10);
        request = "{" +
                "  \"robot\": \"seeRobotAndObstacle5\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        serverClient5.sendRequest(request);

        //Launch 6th robot
        serverClient6.connect(DEFAULT_IP, ITERATION_2_PORT_S2_O10);
        request = "{" +
                "  \"robot\": \"seeRobotAndObstacle6\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        serverClient6.sendRequest(request);

        //Launch 7th robot
        serverClient7.connect(DEFAULT_IP, ITERATION_2_PORT_S2_O10);
        request = "{" +
                "  \"robot\": \"seeRobotAndObstacle7\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        serverClient7.sendRequest(request);

        //Launch 8th robot
        serverClient8.connect(DEFAULT_IP, ITERATION_2_PORT_S2_O10);
        request = "{" +
                "  \"robot\": \"seeRobotAndObstacle8\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        serverClient8.sendRequest(request);

        // When I send a valid look request to the server
        request = "{" +
                "  \"robot\": \"seeRobotAndObstacle1\"," +
                "  \"command\": \"look\"," +
                "  \"arguments\": []" +
                "}";
        JsonNode response = serverClient1.sendRequest(request);

        // Then I should get a valid response from the server
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());
        System.out.println(response);

        // And the objects list should contain a robot at distance 1
        JsonNode objectsNode = response.get("data").get("objects");
        Boolean testbool1 = false;
        Boolean testbool2 = false;
        String type;
        String distance;
        for (JsonNode value1 : objectsNode) {
            type = value1.get("type").asText();
            distance = value1.get("distance").asText();
            if (type.equalsIgnoreCase("ROBOT") && Objects.equals(distance, "1")) {
                testbool1 = true;
            }
        }
        for (JsonNode value2 : objectsNode) {
            type = value2.get("type").asText();
            distance = value2.get("distance").asText();
            if (type.equalsIgnoreCase("OBSTACLE") && Objects.equals(distance, "1")) {
                testbool2 = true;
            }
        }

        assertTrue(testbool1);
        assertTrue(testbool2);
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("objects"));
        //assertEquals(5, response.get("data").get("objects").size());

        // And I should also get the state of the robot
        assertNotNull(response.get("state"));

        serverClient1.disconnect();
    }
}
