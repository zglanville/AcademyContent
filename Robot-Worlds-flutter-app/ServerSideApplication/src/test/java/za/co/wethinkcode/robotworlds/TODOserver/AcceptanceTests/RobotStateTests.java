package za.co.wethinkcode.robotworlds.TODOserver.AcceptanceTests;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Server.RobotWorldClient;
import za.co.wethinkcode.robotworlds.Server.RobotWorldJsonClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * As a player
 * I want to launch my robot in the online robot world
 * So that I can break the record for the most robot kills
 */
class RobotStateTests {
    private final static int ITERATION_1_PORT_S1 = 5110;
    private final static String DEFAULT_IP = "localhost";
    private final RobotWorldClient serverClient = new RobotWorldJsonClient();

    @BeforeEach
    void connectToServer() {
        serverClient.connect(DEFAULT_IP, ITERATION_1_PORT_S1);
    }

    @AfterEach
    void disconnectFromServer() {
        serverClient.disconnect();
    }

    @Test
    void robotExists() {

        // Send a launch request to the server
        String launch = "{" +
                "  \"robot\": \"robotExists\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        serverClient.sendRequest(launch);

        // Ask the server what the state of a launched robot is
        String request = "{" +
        "  \"robot\": \"robotExists\"," +
        "  \"command\": \"state\"," +
        "  \"arguments\": []" +
        "}";
        JsonNode response = serverClient.sendRequest(request);

        // Then I should get a valid state response from the server
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());

        assertNotNull(response.get("state"));
        assertNotNull(response.get("state").get("position"));
        assertNotNull(response.get("state").get("direction"));
        assertNotNull(response.get("state").get("shields"));
        assertNotNull(response.get("state").get("shots"));
        assertNotNull(response.get("state").get("status"));

        // TODO: Possible add checks for state values
        
    }

    @Test
    void robotDoesNotExist() {

        // Send a launch request to the server
        String launch = "{" +
                "  \"robot\": \"notHAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        serverClient.sendRequest(launch);

        // Ask the server what the state of a NOT launched robot is
        String request = "{" +
        "  \"robot\": \"definitelyNotHAL\"," +
        "  \"command\": \"state\"," +
        "  \"arguments\": []" +
        "}";
        JsonNode response = serverClient.sendRequest(request);

        // Then I should get an error response from the server
        assertNotNull(response.get("result"));
        assertEquals("ERROR", response.get("result").asText());

        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("message"));
        assertEquals("Robot does not exist", response.get("data").get("message").asText());
    }

}
