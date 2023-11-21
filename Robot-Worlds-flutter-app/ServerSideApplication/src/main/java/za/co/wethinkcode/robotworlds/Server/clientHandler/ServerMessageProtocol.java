package za.co.wethinkcode.robotworlds.Server.clientHandler;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import za.co.wethinkcode.robotworlds.Server.robot.Robot;
import za.co.wethinkcode.robotworlds.Server.worldCommands.actions.LookCommand;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
Protocol handler to convert output to JSONObject to be returned to the client
 String command, String result Robot object required by the constructor.
Action: Issa
 */
public class ServerMessageProtocol {
        JSONObject returnJson = new JSONObject();
        JSONObject data = new JSONObject();
        JSONObject state = new JSONObject();
        JSONArray robotPosition = new JSONArray();
        JSONParser parser = new JSONParser();

        public JSONObject createJSON(String command, String message, Robot target) {

            int visibility = target.getVisibility();
            int reload = target.getReload();
            int repair = target.getRepair();
            int mine = target.getMine();
            int shields = target.getShields();
            int shots = target.getShots();

            state.clear();
            data.clear();
            returnJson.clear();

            returnJson.put("result", "OK");

            robotPosition.add(target.getPosition().getX());
            robotPosition.add(target.getPosition().getY());

            String direction = target.getCurrentDirection().toString();

            String status = target.getRobotStatus().toString();

            switch (command) {
                case "help":
                    data.put("message", target.getStatus());
                    break;
                case "launch":
                    data.put("position", robotPosition);
                    data.put("visibility", visibility);
                    data.put("reload", reload);
                    data.put("repair", repair);
                    data.put("mine", mine);
                    data.put("shields", shields);
                    break;
                case "forward":
                case "back":
                case "turn":
                case "repair":
                case "reload":
                case "mine":
                    data.put("message", message);
                    break;
                case "fire":
                    File file = new File("").getAbsoluteFile();
                    String jsonFilePath = "/ServerSideApplication/src/main/java/za/co/wethinkcode/robotworlds/" +
                            "Server/worldCommands/actions/FireHitOrMiss.json";
                    try {
                        JSONObject dataJSON = (JSONObject) parser.parse(new FileReader(file + jsonFilePath));
                        data.put("data", dataJSON);
                    } catch (ParseException e) { System.out.println("Could not parse: " +e);}
                    catch (IOException e) {System.out.println("Could not parse: " +e);}
                    break;
                case "look":
                    List<JSONObject> obstacles = LookCommand.getObstacleInVision();
                    data.put("objects", obstacles);
                    break;
                case "state":
            }

            //adding the state params to the state JSONObject
            state.put("position", robotPosition);
            state.put("direction", direction);
            state.put("shields", shields);
            state.put("shots", shots);
            state.put("status", status);

            returnJson.put("state", state);

            if (data.size() > 0) { returnJson.put("data", data); }

            return returnJson;
        }
}
