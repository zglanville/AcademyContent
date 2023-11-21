package za.co.wethinkcode.robotworlds.TODOserver.AcceptanceTests;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Server.RobotWorldClient;
import za.co.wethinkcode.robotworlds.Server.RobotWorldJsonClient;

import static org.junit.jupiter.api.Assertions.*;

/**
 * As a player
 * I want to launch my robot in the online robot world
 * So that I can break the record for the most robot kills
 */
class LaunchRobotTests {
    private final static int ITERATION_1_PORT_S1 = 5110;
    private final static String DEFAULT_IP = "localhost";
    private final RobotWorldClient serverClient = new RobotWorldJsonClient();
    private final RobotWorldClient serverClient2 = new RobotWorldJsonClient();

    @BeforeEach
    void connectToServer() {
        serverClient.connect(DEFAULT_IP, ITERATION_1_PORT_S1);
    }

    @AfterEach
    void disconnectFromServer() {
        serverClient.disconnect();
    }
    
    @Test
    void validLaunchShouldSucceed() {
        // Given that I am connected to a running Robot Worlds server
        // And the world is of size 1x1 (The world is configured or hardcoded to this size)
        assertTrue(serverClient.isConnected());

        // When I send a valid launch request to the server
        String request = "{" +
                "  \"robot\": \"validLaunch\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        JsonNode response = serverClient.sendRequest(request);

        // Then I should get a valid response from the server
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());

        // And the position should be (x:0, y:0)
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("position"));
        assertEquals(0, response.get("data").get("position").get(0).asInt());
        assertEquals(0, response.get("data").get("position").get(1).asInt());

        // And I should also get the state of the robot
        assertNotNull(response.get("state"));
        // When I send a valid launch request to the server
    }

    @Test
    void invalidLaunchShouldFail() {
        // Given that I am connected to a running Robot Worlds server
        assertTrue(serverClient.isConnected());

        // When I send a invalid launch request with the command "luanch" instead of "launch"
        String request = "{" +
                "\"robot\": \"invalidLaunch\"," +
                "\"command\": \"luanch\"," +
                "\"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        JsonNode response = serverClient.sendRequest(request);

        // Then I should get an error response
        assertNotNull(response.get("result"));
        assertEquals("ERROR", response.get("result").asText());

        // And the message "Unsupported command"
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("message"));
//        assertTrue(response.get("data").get("message").asText().contains("Unsupported command"));
        assertEquals("Unsupported command",response.get("data").get("message").asText());

    }

    @Test
    void noMoreSpaceInWorld() {
//        Given the ability of the server to allow multiple robots to join a world
//        And there are not more free spaces in the world
//        When the user issues the launch command
//        And the launch command is valid
//        Then the server should respond with an error
//        And the error should say that there is no more space within the world
        //The test world is 1x1
        serverClient2.connect(DEFAULT_IP, ITERATION_1_PORT_S1);
        assertTrue(serverClient2.isConnected());


        assertTrue(serverClient.isConnected());

        // When I launch 2 robots
        String request = "{" +
                "  \"robot\": \"robotOne\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        serverClient.sendRequest(request);
//        connectToServer();

        String request2 = "{" +
                "  \"robot\": \"robotTwo\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";


        JsonNode response = serverClient2.sendRequest(request2);

        // Then I should get an error response from the server
        assertNotNull(response.get("result"));
        assertEquals("ERROR", response.get("result").asText());
//        assertTrue(response.get("data").get("message").asText().contains("There is no more space in this world."));

//
//        String purgerequest = "{" +
//                "  \"robot\": \"HAILEY\"," +
//                "  \"command\": \"purge\"," +
//                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
//                "}";
//
//
//       JsonNode purgeresponse = serverClient.sendRequest(purgerequest);
//       assertTrue(purgeresponse.get("data").get("message").asText().contains("Shutting down...") );

        serverClient2.disconnect();

//        // And the message "No more space"
//        assertNotNull(response.get("data"));
//        assertNotNull(response.get("data").get("message"));
//        assertTrue(response.get("data").get("message").asText().contains("There is no more space in this world."));
    }

    @Test
    void RobotWithSameNameAlreadyExists() {
//        Given the ability of the server to allow multiple robots to join a world
//        When the user issues the launch command
//        And the launch command is valid
//        And the robot name stipulated in the launch command already exist within the world
//        Then the server should respond with an error
//        And the error should say "Too many of you in this world"
//        And ask the user to change their robot's name

        assertTrue(serverClient.isConnected());

        // When I launch 2 robots
        String request = "{" +
                "  \"robot\": \"sameName\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        serverClient.sendRequest(request);

        serverClient2.connect(DEFAULT_IP, ITERATION_1_PORT_S1);
        assertTrue(serverClient2.isConnected());
        JsonNode response = serverClient2.sendRequest(request);


        // Then I should get an error response from the server
        assertNotNull(response.get("result"));
        assertEquals("ERROR", response.get("result").asText());
        assertEquals("Too many of you in this world", response.get("data").get("message").asText());

        serverClient2.disconnect();
    }
}
