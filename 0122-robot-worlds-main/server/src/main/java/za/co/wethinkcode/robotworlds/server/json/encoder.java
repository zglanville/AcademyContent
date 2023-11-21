package za.co.wethinkcode.robotworlds.server.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import za.co.wethinkcode.robotworlds.server.world.IWorld;

public class encoder {
    private int shields;
    private int shots;
    private IWorld.Direction direction;
    private String status;
    private String position; //change
    private int visibility;
    private int reload;
    private int repair;
    private int mine;
    private String type;
    private int distance;


    public encoder(IWorld.Direction direction, int shields, int shots, String status, String position, int visibility, int reload,int repair,int mine, String type, int distance){
        this.direction = direction;
        this.shields = shields;
        this.shots = shots;
        this.status = status;
        this.position = position;
        this.visibility = visibility;
        this.reload = reload;
        this.repair = repair;
        this.mine = mine;
        this.type = type;
        this.distance = distance;
    }

    //if json request is "launch" a robot, response must be OK

    //create json builder that re

    public JSONObject launchResponse() {
        JSONObject state = new JSONObject();
        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();
        //if error only put result and data
        result.put("result", "OK");
        data.put("position", getPosition());
        data.put("visibility", getVisibility());
        data.put("reload", getReload());
        data.put("repair", getRepair());
        data.put("mine", getMine());
        data.put("shields", getShields());
        result.put("data", data);

        state.put("position", getPosition());
        state.put("direction", getDirection());
        state.put("shields", getShields());
        state.put("shots", getShots());
        state.put("status", getStatus());
        result.put("state", state);
        return result;
        //works
    }

    public JSONObject launchInvalidName(){
        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();

        result.put("result", "ERROR");
        data.put("message","No more space in this world");
        result.put("data", data);
        return result;
    }

    public JSONObject stateResponse(){
        JSONObject state = new JSONObject();
        JSONObject result = new JSONObject();
        state.put("position", getPosition());
        state.put("direction", getDirection());
        state.put("shields", getShields());
        state.put("shots", getShots());
        state.put("status", getStatus());
        result.put("state", state);
        return result;
    }
    public JSONObject movementResponse(){
        JSONObject state = new JSONObject();
        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();
        //response for forward and back
        result.put("result", "OK");
        data.put("message", "Done");
        result.put("data", data);

        state.put("position", getPosition());
        state.put("direction", getDirection());
        state.put("shields", getShields());
        state.put("shots", getShots());
        state.put("status", getStatus());
        result.put("state", state);
        return result;
    }

    public JSONObject movementResponseObstructed(){
        JSONObject state = new JSONObject();
        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray position = new JSONArray();

        result.put("result", "OK");
        data.put("message", "Obstructed");
        result.put("data", data);

        state.put("position", getPosition());
        state.put("direction", getDirection());
        state.put("shields", getShields());
        state.put("shots", getShots());
        state.put("status", getStatus());
        result.put("state", state);
        return result;
    }

    public JSONObject movementResponsePit(){
        JSONObject state = new JSONObject();
        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray position = new JSONArray();

        result.put("result", "OK");
        data.put("message", "Fell");
        result.put("data", data);

        state.put("position", getPosition());
        state.put("direction", getDirection());
        state.put("shields", getShields());
        state.put("shots", getShots());
        state.put("status", getStatus());
        result.put("state", state);
        return result;
    }

    public JSONObject movementResponseSurvivedMine(){
        JSONObject state = new JSONObject();
        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray position = new JSONArray();

        result.put("result", "OK");
        data.put("message", "Mine");
        result.put("data", data);

        state.put("position", getPosition());
        state.put("direction", getDirection());
        state.put("shields", getShields());
        state.put("shots", getShots());
        state.put("status", getStatus());
        result.put("state", state);
        return result;
    }

    public JSONObject movementResponseDiedToMine(){
        JSONObject state = new JSONObject();
        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray position = new JSONArray();

        result.put("result", "OK");
        data.put("message", "Mine");
        result.put("data", data);

        state.put("position", getPosition());
        state.put("direction", getDirection());
        state.put("shields", getShields());
        state.put("shots", getShots());
        state.put("status", getStatus());
        result.put("state", state);
        return result;
    }
    public JSONObject lookResponse(){
        JSONObject state = new JSONObject();
        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray position = new JSONArray();
        JSONArray objectsArray = new JSONArray();
        JSONObject objects = new JSONObject();

        result.put("result", "OK");
        objects.put("direction", getDirection());
        objects.put("type", getType());
        objects.put("distance", getDistance());
        objectsArray.add(0,objects);
        data.put("objects", objectsArray);
        result.put("data", data);

        state.put("position", getPosition());
        state.put("direction", getDirection());
        state.put("shields", getShields());
        state.put("shots", getShots());
        state.put("status", getStatus());
        result.put("state", state);
        return result;
    }

    public JSONObject turnResponse(){
        JSONObject state = new JSONObject();
        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();

        result.put("result", "OK");
        data.put("message", "Done");
        result.put("data", data);

        state.put("position", getPosition());
        state.put("direction", getDirection());
        state.put("shields", getShields());
        state.put("shots", getShots());
        state.put("status", getStatus());
        result.put("state", state);
        return result;
    }

    public JSONObject repairResponse(){
        JSONObject state = new JSONObject();
        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();

        result.put("result", "OK");
        data.put("message", "Done");
        result.put("data", data);

        state.put("position", getPosition());
        state.put("direction", getDirection());
        state.put("shields", getShields());
        state.put("shots", getShots());
        state.put("status", getStatus());
        result.put("state", state);
        return result;
    }

    public JSONObject reloadResponse(){
        JSONObject state = new JSONObject();
        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();

        result.put("result", "OK");
        data.put("message", "Done");
        result.put("data", data);

        state.put("position", getPosition());
        state.put("direction", getDirection());
        state.put("shields", getShields());
        state.put("shots", getShots());
        state.put("status", getShots());
        result.put("state", state);
        return result;
    }

    public JSONObject setMineResponse(){
        JSONObject state = new JSONObject();
        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray position = new JSONArray();

        result.put("result", "OK");
        data.put("message", "Done");
        result.put("data", data);

        state.put("position", getPosition());
        state.put("direction", getDirection());
        state.put("shields", getShields());
        state.put("shots", getShots());
        state.put("status", getShots());
        result.put("state", state);
        return result;
    }

    public JSONObject fireHitTargetResponse(){
        JSONObject state = new JSONObject();
        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray position = new JSONArray();

        result.put("result", "OK");
        data.put("message", "Hit");
        data.put("distance", "stepsaway");
        data.put("robot", "getOtherRobotName()");
        data.put("state", "{state of other robot}");
        result.put("data", data);

        state.put("position", position);
        state.put("direction", "getDirection()");
        state.put("shields", "getHealth()");
        state.put("shots", "getBullets()");
        state.put("status", "NORMAL");
        result.put("state", state);
        return result;
    }

    public JSONObject fireMissTargetResponse(){
        JSONObject state = new JSONObject();
        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray position = new JSONArray();

        result.put("result", "OK");
        data.put("message", "Miss");
        result.put("data", data);

        state.put("position", position);
        state.put("direction", "getDirection()");
        state.put("shields", "getHealth()");
        state.put("shots", "getBullets()//shots left");
        state.put("status", "NORMAL");
        result.put("state", state);
        return result;
    }

    public String getType(){
        return type;
    }

    public int getDistance(){
        return distance;
    }

    public int getReload(){
        return reload;
    }

    public int getRepair(){
        return repair;
    }

    public int getVisibility(){
        return visibility;
    }

    public JSONArray getPosition(){ //change later
        JSONArray array = new JSONArray();
        array.add(position);
        return array;
    }

    public int getMine(){
        return mine;
    }

    public int getShields(){
        return shields;
    }

    public int getShots(){
        return shots;
    }

    public IWorld.Direction getDirection(){
        return direction;
    }

    public String getStatus(){
        return status;
    }
}
