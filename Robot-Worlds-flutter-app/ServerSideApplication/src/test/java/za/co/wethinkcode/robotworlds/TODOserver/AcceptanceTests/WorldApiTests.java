package za.co.wethinkcode.robotworlds.TODOserver.AcceptanceTests;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.junit.jupiter.api.*;
import za.co.wethinkcode.robotworlds.Server.RobotWorldClient;
import za.co.wethinkcode.robotworlds.Server.RobotWorldJsonClient;
import za.co.wethinkcode.robotworlds.Server.Web.WorldsWebServer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WorldApiTests {
    private static WorldsWebServer server;
    private static final RobotWorldClient serverClient = new RobotWorldJsonClient();


//    @BeforeAll
//    public static void startServer() {
//        serverClient.connect("localhost", 1234);
////        server = new WorldsWebServer();
////        server.start(5000);
//    }
//
//    @AfterAll
//    public static void stopServer() {
//        serverClient.disconnect();
////        server.stop();
//    }

    @Test
    @DisplayName("GET /world/{worldName}")
    public void loadWorld() throws UnirestException {
        serverClient.connect("localhost", 5000);

        HttpResponse<JsonNode> response = Unirest.get("http://localhost:5000/world/test").asJson();
        assertEquals(200, response.getStatus());
        assertEquals("application/json", response.getHeaders().getFirst("Content-Type"));

        JSONObject jsonObject = response.getBody().getObject();
        assertEquals("test", jsonObject.get("name"));

        serverClient.disconnect();
    }

    @Test
    @DisplayName("GET /world")
    public void getCurrentWorld() throws UnirestException {
        serverClient.connect("localhost", 5000);
        HttpResponse<JsonNode> response1 = Unirest.get("http://localhost:5000/world/test1").asJson();
        assertEquals(200, response1.getStatus());
        HttpResponse<JsonNode> response = Unirest.get("http://localhost:5000/world").asJson();
        assertEquals(200, response.getStatus());
        assertEquals("application/json", response.getHeaders().getFirst("Content-Type"));

        JSONObject jsonObject = response.getBody().getObject();
        assertEquals("test1", jsonObject.get("name"));

        serverClient.disconnect();
    }

//    @Test
//    @DisplayName("GET /world")
//    void getAllQuotes() throws UnirestException {
//        HttpResponse<JsonNode> response = Unirest.get("http://localhost:5000/world").asJson();
//        assertEquals(200, response.getStatus());
//        assertEquals("application/json", response.getHeaders().getFirst("Content-Type"));
//
//        JSONArray jsonArray = response.getBody().getArray();
//        assertTrue(jsonArray.length() > 1);
//    }
//
    @Test
    @DisplayName("POST /robot/{robotName}")
    void launchRobot() throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.post("http://localhost:5000/robot/testRobot")
                .header("Content-Type", "application/json")
                .body("{\"robot\":\"testName\",\"arguments\":[\"shooter\",\"5\",\"5\"],\"command\":\"launch\"}")
                .asJson();
        assertEquals(201, response.getStatus());
        JSONObject jsonObject = response.getBody().getObject();
        assertEquals("OK", jsonObject.get("result"));
        assertEquals("{\"shields\":5,\"position\":[0,0],\"shots\":5,\"direction\":\"NORTH\",\"status\":\"NORMAL\"}", jsonObject.get("state").toString());}

}
