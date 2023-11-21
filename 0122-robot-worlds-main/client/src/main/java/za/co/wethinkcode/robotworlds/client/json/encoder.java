package za.co.wethinkcode.robotworlds.client.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class encoder {
    private String name;
    private String command;
    private String argument;
    //gets request.
    //sends a request to server
    //possible request - name, commands

//    private JSONObject launch;

    public encoder(String name, String command, String argument){
        this.name = name;
        this.command = command;
        this.argument = argument;
    }

    public JSONObject request() {
        JSONObject request = new JSONObject();
        request.put("robot", getName());
        request.put("command", getCommand());
        request.put("arguments", getArgument());

        return request;
    }

    public String getName(){
        return name;
    }

    public JSONArray getArgument(){
        JSONArray list = new JSONArray();
        list.add(argument);
        return list;
    }

    public String getCommand(){
        return command;
    }
}
