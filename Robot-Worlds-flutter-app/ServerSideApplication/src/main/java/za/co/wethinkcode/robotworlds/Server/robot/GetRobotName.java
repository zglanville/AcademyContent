package za.co.wethinkcode.robotworlds.Server.robot;

import org.json.simple.JSONObject;
import za.co.wethinkcode.robotworlds.Server.ServerMain;

import java.util.List;

/**
 * Class checks whether the robot name has already been taken
 * @author Issa
 */
public class GetRobotName {
    private List<RobotHandler> robots;
    private String robotNameToCheck;
    private boolean isTaken;

    public GetRobotName(String name){
        this.robotNameToCheck = name;
        this.robots = ServerMain.robots;
        this.isTaken = isNameTaken();
    }

    /**
     * Method checks whether a robot has been launched and whether another robot
     * has an identical name
     * @return true if another robot has the same name.
     */
    private boolean isNameTaken() {
        for (RobotHandler robot : ServerMain.robots) {
            if (robot.isLaunched)
                if (robot.getRobot().getName().equalsIgnoreCase(this.robotNameToCheck))
                    return true;
        }
        return false;
    }

    public boolean getIsTaken(){return this.isTaken;}

    /**
     * Method creates the error response to be sent to the client side application
     * @return JSON protocol object.
     */
    public JSONObject nameTakenJSONObject(){
        JSONObject obj = new JSONObject();
        JSONObject obj2 = new JSONObject();
        obj.put("result", "ERROR");
        obj2.put("message", "Too many of you in this world");
        obj.put("data", obj2);
        return obj;
    }
}
