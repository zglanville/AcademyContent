/**
 * @author Thonifho
 */
package za.co.wethinkcode.robotworlds.Server.clientHandler;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import za.co.wethinkcode.robotworlds.Server.robot.Robot;
import za.co.wethinkcode.robotworlds.Server.worldCommands.Command;

import java.util.HashMap;

/**
 * InputHandler manages the input from the user and initiates
 * the method call for commands to be processed.
 * JSONObject from client
 * Robot object
 * Action Thoni && Issa
 */
public class InputHandler {
    String input;
    Robot target;
    String argument;

    public InputHandler(JSONObject input, Robot target){
        this.input = (String) input.get("command");
        JSONArray argArray = (JSONArray) input.get("arguments");
        argArray.toArray();
        this.argument = argArray.size()>0 ? (String) argArray.get(0) : "";
        this.target = target;
    }

    /**
     * Create the command and obtain the outcome of the respective command.
     * @return JSONObject from the server protocol to be sent to the client.
     */
    public JSONObject getOutput(){
        ServerMessageProtocol protocol = new ServerMessageProtocol();
        Command command = Command.create(this.input+" "+this.argument);
        JSONObject response = new JSONObject();
        JSONObject data = new JSONObject();
        if (target.handleCommand(command)) {
            response = protocol.createJSON(this.input, command.getMessage(), this.target);
            return response;
        }
        // data.put("message", "Unsupported command in getOutput()");
        data.put("message", "Unsupported command");
        response.put("result", "ERROR");
        response.put("data", data);
        return response;
    }

    public static JSONObject unsupportedCommand(){
        JSONObject response = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("message", "Unsupported command");
        // data.put("message", "Unsupported command in unsupportedCommand()");
        response.put("result", "ERROR");
        response.put("data", data);
        return response;
    }

    public static JSONObject errorNoMoreSpace(){
        JSONObject response = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("message", "No more space in this world");
        response.put("result", "ERROR");
        response.put("data", data);
        return response;
    }

    public static JSONObject errorRobotDoesNotExist(){
        JSONObject response = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("message", "Robot does not exist");
        response.put("result", "ERROR");
        response.put("data", data);
        return response;
    }

    public static JSONObject nullRobot(){
        JSONObject response = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("message", "Robot is NULL");
        response.put("result", "ERROR");
        response.put("data", data);
        return response;
    }
}
