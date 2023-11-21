package za.co.wethinkcode.robotworlds.server;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import za.co.wethinkcode.robotworlds.server.commands.Command;
import za.co.wethinkcode.robotworlds.server.json.encoder;
import za.co.wethinkcode.robotworlds.server.maze.Maze;
import za.co.wethinkcode.robotworlds.server.robot.Robot;
import za.co.wethinkcode.robotworlds.server.world.IWorld;
import za.co.wethinkcode.robotworlds.server.world.TextWorld;
//import za.co.wethinkcode.robotworlds.server.robot.robotMakes.*;
import za.co.wethinkcode.robotworlds.server.configReader.ConfigReader;
import za.co.wethinkcode.robotworlds.server.robot.robotMakes.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;


public class ClientHandler implements Runnable {

    public static final int PORT = 5000;
    private final BufferedReader in;
    private final PrintStream out;
    Robot robot;
    Command command;
    IWorld world;
    ConfigReader config;

    private IWorld.Direction direction;
    private int shields;
    private int shots;
    private String status;
    private int position_x;
    private int position_y;
    private String position;
    private int visibility;
    private int reload;
    private int repair;
    private int mine;
    private String messageFromClient;
    private String type;
    private int distance;


    public ClientHandler(Socket socket, Maze maze, ConfigReader config) throws IOException {
        world = new TextWorld(maze, config);
        world.showObstacles();
        System.out.println();
        world.showPits();
        System.out.println();
        world.showMines();
        this.config = config;
        String clientMachine = socket.getInetAddress().getHostName();
//        System.out.println("Connection from " + clientMachine);

        out = new PrintStream(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
//        System.out.println("Waiting for client...");

    }

    public void run() {
        String robotName = "";

        try {
            while((messageFromClient = in.readLine()) != null) {
                if (robotName.equalsIgnoreCase("")) {
                    robotName = messageFromClient;

                    JSONParser parser = new JSONParser();
                    JSONObject json = (JSONObject) parser.parse(messageFromClient);
                    String name = json.get("robot").toString();
                    String argument = json.get("arguments").toString();
                    argument = argument.replace("[", "").replace("\"", "").replace("]", "");

                    robot = makeRobotClass(name, argument);
                    robot.setWorld(world);

                    direction = robot.getWorld().getCurrentDirection();
                    shields = robot.getCurrentHealth();
                    shots = robot.getCurrentBullets();
                    status = robot.getStatus();
                    position_x = robot.getWorld().getPosition().getX();
                    position_y = robot.getWorld().getPosition().getY();
                    position = position_x + "," + position_y;
                    visibility = robot.getVision();
                    reload = Integer.parseInt(config.getWeaponReload());
                    repair = Integer.parseInt(config.getShieldRepair());
                    mine = 5;
                    type = "";
                    distance = 1;

                    encoder encoder = new encoder(direction, shields, shots, status, position, visibility, reload, repair, mine, type, distance);

                    System.out.println(json);
                    System.out.println("launch response" + encoder.launchResponse());
                    out.println(encoder.launchResponse());
                    out.flush();
                    String robotName2 = String.valueOf(json.get("robot"));
                    Server.addRobots(robotName2, robot);
                    System.out.println(name + " joined the za.co.wethinkcode.server.server");
                    System.out.println("ROBOT: " + Server.getListOfRobots());
                } else {
                    try {
                        JSONObject response = jsonBuilder();

                        out.println(response.toJSONString());
                        out.flush();
                        System.out.println("response: " + response.toJSONString());

                    } catch (IllegalArgumentException e) {
                        robot.setStatus("Sorry, I did not understand '" + messageFromClient + "'.");
                    }
                }
            }
        } catch(IOException | ParseException ex) {
            Server.removeRobot("bob", robot); //change string
            System.out.println("Shutting down single client za.co.wethinkcode.server.server");
            System.out.println("robot list" + Server.getListOfRobots());
        } finally {
            closeQuietly();
        }
    }

    private JSONObject jsonBuilder() throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(messageFromClient);
        String argument2 = String.valueOf(json.get("arguments"));
        argument2 = argument2.replace("[", "").replace("\"", "").replace("]", "");
        String command2 = json.get("command").toString();
//        System.out.println(command2 + " " + argument2);

        command = Command.create(command2 + " " + argument2);
        robot.handleCommand(command);

        direction = robot.getWorld().getCurrentDirection();
        shields = robot.getCurrentHealth();
        shots = robot.getCurrentBullets();
        status = robot.getStatus();
        position_x = robot.getWorld().getPosition().getX();
        position_y = robot.getWorld().getPosition().getY();
        position = "x: " + position_x + "," + "y: " + position_y;
        visibility = robot.getVision();
        reload = Integer.parseInt(config.getWeaponReload());
        repair = Integer.parseInt(config.getShieldRepair());
        mine = 5; //change when mines are done
        type = "";
        distance = 1;

        encoder encoder = new encoder(direction, shields, shots, status, position, visibility, reload, repair, mine, type, distance);

        if(command2.equals("state")){
            return encoder.stateResponse();
        }else if(command2.equals("forward") || command2.equals("back")){
            JSONObject response = encoder.movementResponse();
            String status = String.valueOf(response.get("status"));
            if(status.equals("boom, you died! LOL NOOB GG REKT")){
                response = encoder.movementResponsePit();
            }else if(status.equals("Sorry, Obstacle in my way.")){
                response = encoder.movementResponseObstructed();
            }else if(status.equals("Ouch you stepped on a mine!")){
                response = encoder.movementResponseSurvivedMine();
            }else if(status.equals("You have died. :(!")){
                response = encoder.movementResponseDiedToMine();
            }return response;
        }else if(command2.equals("turn")){
            return encoder.turnResponse();
        }else if(command2.equals("repair")){
            System.out.println("repair");
            return encoder.repairResponse();
        }else if(command2.equals("reload")){
            return encoder.reloadResponse();
        }else if(command2.equals("mine")){
            return encoder.setMineResponse();
        }else{
            return encoder.lookResponse();
        }
    }

    private void closeQuietly() {
        try { in.close(); out.close();
        } catch(IOException ex) {}
    }

    private boolean newRobotName(String message) {
        System.out.println("message: " + message);
        String[] args = message.split(" ");
        return !Server.getListOfRobots().containsKey(args[2]);
    }

    private Robot makeRobotClass(String name, String make) {
        switch (make.toLowerCase()) {
            case "sniper": return new Sniper(name, this.config);
            case "tank": return new Tank(name, this.config);
            case "gunner": return new Gunner(name, this.config);
            case "miner": return new Miner(name, this.config);
            case "god": return new God(name, this.config);
            default: new Gunner(name, this.config);
        }
        return new Gunner(name, this.config);
    }
}