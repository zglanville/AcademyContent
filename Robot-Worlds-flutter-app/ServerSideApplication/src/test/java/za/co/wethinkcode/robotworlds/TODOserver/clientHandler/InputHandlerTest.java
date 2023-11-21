// package za.co.wethinkcode.robotworlds.TODOserver.clientHandler;
// /**
//  *
//  * @author Thonifho
//  * to fix - Sarah
//  */

// import org.json.simple.JSONArray;
// import org.json.simple.JSONObject;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import za.co.wethinkcode.robotworlds.Server.clientHandler.InputHandler;
// import za.co.wethinkcode.robotworlds.Server.maze.RandomMaze;
// import za.co.wethinkcode.robotworlds.Server.robot.Robot;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertThrows;

// public class InputHandlerTest {
//     Robot robot;
//     JSONObject testJSON;

//     @BeforeEach
//     void setUp(){
//         RandomMaze maze = new RandomMaze();
//         Robot robot = new Robot("test", maze);
//         this.robot = robot;
//         this.testJSON = new JSONObject();
//     }


//     @Test
//     void testInvalidInputGetOutput() {

//         JSONArray arguments = new JSONArray();
//         arguments.add("");
//         this.testJSON.put("robot", "Bot");
//         this.testJSON.put("arguments", arguments);
//         this.testJSON.put("command","bckx");
        
//         assertThrows(IllegalArgumentException.class, () -> new InputHandler(this.testJSON, robot).getOutput());

//         this.testJSON.clear();
//     }


//     @Test
//     void testValidInputForward(){
//         String[] arguments = {"10"};
//         this.testJSON.put("robot", "Bot");
//         this.testJSON.put("command", "forward");
//         this.testJSON.put("arguments", arguments);

//         assertEquals(this.testJSON.get("robot").toString(), "Bot");
//     }
// }