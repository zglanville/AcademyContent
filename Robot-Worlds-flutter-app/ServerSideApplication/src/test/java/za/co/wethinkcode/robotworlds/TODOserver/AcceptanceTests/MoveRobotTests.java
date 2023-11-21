package za.co.wethinkcode.robotworlds.TODOserver.AcceptanceTests;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Server.RobotWorldClient;
import za.co.wethinkcode.robotworlds.Server.RobotWorldJsonClient;

import static org.junit.jupiter.api.Assertions.*;

class MoveRobotTests {
    private final static int ITERATION_2_PORT_S1 = 5210;
    private final static String DEFAULT_IP = "localhost";

    @Test
    void forwardFive() {
        RobotWorldClient serverClient = new RobotWorldJsonClient();
        
        serverClient.connect(DEFAULT_IP, ITERATION_2_PORT_S1);

        // Given that I am connected to a running Robot Worlds server
        // And the world is of size 1x1
        assertTrue(serverClient.isConnected());

        String request = "{" +
        "  \"robot\": \"forwardFive2\"," +
        "  \"command\": \"launch\"," +
        "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
        "}";
        serverClient.sendRequest(request);

        // When I send a move forward request to the server
        request = "{" +
                "  \"robot\": \"forwardFive2\"," +
                "  \"command\": \"forward\"," +
                "  \"arguments\": [\"5\"]" +
                "}";
        JsonNode response = serverClient.sendRequest(request);

        // Then I should get an OK response from the server
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());

        // telling me the robot is against the edge of the world
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("message"));
        assertEquals("At the NORTH edge", response.get("data").get("message").asText());

        // And I should also get the state of the robot at 0,0
        assertNotNull(response.get("state"));
        assertNotNull(response.get("state").get("position"));
        assertEquals(0, response.get("state").get("position").get(0).asInt());
        assertEquals(0, response.get("state").get("position").get(1).asInt());

        serverClient.disconnect();
    }
}
