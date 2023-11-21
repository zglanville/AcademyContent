package za.co.wethinkcode.robotworlds.TODOserver.AcceptanceTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Server.RobotWorldJsonClient;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class LaunchTests2x2 {
    private final static int DEFAULT_PORT_NO_OBSTACLE= 5220;
    private final static int DEFAULT_PORT_OBSTACLE = 5221;
    private final static String DEFAULT_IP = "localhost";
    private final ArrayList<RobotWorldJsonClient> connectedClients = new ArrayList<>();
    private RobotWorldJsonClient serverClient;
    private RobotWorldJsonClient serverClient2;

    @AfterEach
    void disconnectFromServer() {
        for (RobotWorldJsonClient client : connectedClients) {
            client.disconnect();
        }
        connectedClients.clear();
    }

    @Test
    void worldWithoutObstacleIsFull() throws JsonProcessingException {
//      Given a world of size 2x2
//      and I have successfully launched 9 robots into the world
//      When I launch one more robot
//      Then I should get an error response back with the message "No more space in this world"
        
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode response = objectMapper.readTree("{}");
        for (int i = 1; i <= 10; i++) {
            serverClient = new RobotWorldJsonClient();
            serverClient.connect(DEFAULT_IP, DEFAULT_PORT_NO_OBSTACLE);
            connectedClients.add(serverClient);
            String request = "{" +
                "  \"robot\": \"worldWithoutObstacleIsFull" + i + "\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
            response = serverClient.sendRequest(request);
            if (i <= 9) {
                assertEquals("OK", response.get("result").asText()); //TODO fix
            }
        }

        assertEquals("ERROR", response.get("result").asText());
        assertEquals("No more space in this world", response.get("data").get("message").asText());
    }

    @Test
    void launchRobotsIntoWorldWithObstacle() throws JsonProcessingException {
    //  Given a world of size 2x2
    //and the world has an obstacle at coordinate [0,1]
    //When I launch 8 robots into the world
    //Then each robot cannot be in position [0,1].

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode response = objectMapper.readTree("{}");
        for (int i = 1; i <= 8; i++) {
            serverClient = new RobotWorldJsonClient();
            serverClient.connect(DEFAULT_IP, DEFAULT_PORT_OBSTACLE);
            connectedClients.add(serverClient);
            String request = "{" +
                "  \"robot\": \"launchIntoWorldWithObstacle" + i + "\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
            response = serverClient.sendRequest(request);
            assertEquals("OK", response.get("result").asText());
            String position = response.get("data").get("position").get(0) + "," + response.get("data").get("position").get(1);
            assertNotEquals("0,1", position);

        }
    }

    @Test void canLaunchAnotherRobot(){
//        Given a world of size 2x2
//        and robot "HAL" has already been launched into the world
//        When I launch robot "R2D2" into the world
//        Then the launch should be successful
//        and a randomly allocated position of R2D2 should be returned.
        serverClient = new RobotWorldJsonClient();
        serverClient.connect(DEFAULT_IP, DEFAULT_PORT_NO_OBSTACLE);
        connectedClients.add(serverClient);
        String request = "{" +
                "  \"robot\": \"canLaunchAnotherRobot1\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        serverClient.sendRequest(request);

        //secondlaunch
        serverClient2 = new RobotWorldJsonClient();
        serverClient2.connect(DEFAULT_IP, DEFAULT_PORT_NO_OBSTACLE);
        connectedClients.add(serverClient2);
        String request2 = "{" +
                "  \"robot\": \"canLaunchAnotherRobot2\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";

        JsonNode response = serverClient2.sendRequest(request2);
        assertEquals("OK", response.get("result").asText());
        String position = response.get("data").get("position").get(0) + "," + response.get("data").get("position").get(1);
        assertNotEquals("0,0",position);

    }
    @Test
    void worldWithObstaclesIsFull() throws JsonProcessingException {
//      Given a world of size 2x2
//      and the world has an obstacle at coordinate [0,1]
//      and I have successfully launched 8 robots into the world
//      When I launch one more robot
//      Then I should get an error response back with the message "No more space in this world"

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode response = objectMapper.readTree("{}");
        for (int i = 1; i <= 9; i++) {
            serverClient = new RobotWorldJsonClient();
            serverClient.connect(DEFAULT_IP, DEFAULT_PORT_OBSTACLE);
            connectedClients.add(serverClient);
            String request = "{" +
                    "  \"robot\": \"worldWithObstacleIsFull" + i + "\"," +
                    "  \"command\": \"launch\"," +
                    "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                    "}";
            response = serverClient.sendRequest(request);
            if (i <= 8) {
                assertEquals("OK", response.get("result").asText());
            }
        }

        assertEquals("ERROR", response.get("result").asText());
        assertEquals("No more space in this world", response.get("data").get("message").asText());
    }
}
