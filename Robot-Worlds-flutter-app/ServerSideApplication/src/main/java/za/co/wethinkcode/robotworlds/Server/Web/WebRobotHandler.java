package za.co.wethinkcode.robotworlds.Server.Web;

import java.io.*;
import org.json.simple.JSONObject;
import za.co.wethinkcode.robotworlds.Server.clientHandler.InputHandler;
import za.co.wethinkcode.robotworlds.Server.clientHandler.ServerMessageProtocol;
import za.co.wethinkcode.robotworlds.Server.maze.RandomMaze;
import za.co.wethinkcode.robotworlds.Server.robot.GetRobotName;
import za.co.wethinkcode.robotworlds.Server.robot.Robot;
import za.co.wethinkcode.robotworlds.Server.robot.RobotStatus;import org.json.simple.parser.JSONParser;
import za.co.wethinkcode.robotworlds.Server.worldCommands.ShutdownCommand;
/*
* @author sgerber
* Temporary robot handler for Web API changes while we refactor.
* */
public class WebRobotHandler implements Runnable {

    // demo

    private Robot robot = null;

    private boolean robotAlive = true;
    private final RandomMaze world;
    public boolean isLaunched = false;
    String robotName;
    String command;
    JSONObject instruction;
    JSONObject response;
    RobotsData robotsData;

    public WebRobotHandler(RandomMaze world, String robotName, JSONObject instruction, RobotsData robotsData) {
        this.robotName = robotName;
        this.instruction = instruction;
        this.command = (String) instruction.get("command");
        this.world = world;
        this.robotsData = robotsData;
        System.out.print("Command: " + this.command );
    }

    /**
     * Activates the robot and is the base method for the entire robot operation
     * @throws IOException if unable to read data
     * @throws ClassNotFoundException if a class that has been called is not found
     * @return
     */
    public JSONObject activateRobot() throws IOException, ClassNotFoundException {

        ServerMessageProtocol createResponse = new ServerMessageProtocol();

        try {
            System.out.print("Entered try loop");

            // If it is a launch request:
            if (command.equalsIgnoreCase("launch")) {
                System.out.println("\n[WebStatus] Creating a robot..\n");
                GetRobotName nameCheck = new GetRobotName(robotName);
                boolean nameTaken = nameCheck.getIsTaken();
                // boolean isPositionOpen = world.getIsPositionOpen();

                // error responses
                if (nameTaken) {
                    JSONObject nameTakenResponse = nameCheck.nameTakenJSONObject();
                    System.out.println("[WebStatus] Robot name is taken.");
                    return nameTakenResponse;
                }
                if (world.getOpenSpaces().size() == 0) {
                    JSONObject worldIsFullResponse = InputHandler.unsupportedCommand();
                    System.out.println("[WebStatus] The world is full.");
                    return worldIsFullResponse;
                }

                robot = new Robot(robotName, world);
                robotsData.addRobot(robot);

                isLaunched = true;
                this.response = createResponse.createJSON("launch", "", robot);
            }

            // get robot object from robotsData, returns null if robot doesn't exist
            robot = robotsData.getRobot(robotName);

            // If state is called without robot existing
            if (robot != null && command.equalsIgnoreCase("state") &&
                    !robot.getName().equalsIgnoreCase(robotName)) {
                return InputHandler.errorRobotDoesNotExist();
            
            // Valid command, robot not null
            } else if (!command.equalsIgnoreCase("off")
                    && robot != null && !command.equalsIgnoreCase("launch")) {
                this.response = new InputHandler(instruction, robot).getOutput();

            // robot is null: 
            } else if (robot == null) {
                System.out.print("Robot is dead...");
                JSONObject nullResponse = InputHandler.nullRobot();
                return nullResponse;
            }

            System.out.println("[WebClient] " + robot.getName() +
                    " : " + robot.getStatus());

            if (robot.getRobotStatus() == RobotStatus.DEAD) {
                robotAlive = false;
                new ShutdownCommand();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.response;
    }

    /**
     * When client disconnects.
     */
    public void disconnect() throws IOException {
        if (this.robot != null) {
            world.freePosition(this.robot.getPosition().getX(), this.robot.getPosition().getY());
            System.out.println("Freed position occupied by " + this.robot.getName());
        }
        // WorldsWebServer.robotHandlers.remove(this);
        System.out.println("Removing robot. [DISCONNECT]");
    }

    /**
     * Get the robot that has been instantiated.
     * @return Robot object
     */
    public Robot getRobot() { return this.robot; }

    public RandomMaze getWorld() { return this.world; }

    /**
     * run method to handle for the creation of the thread in the ServerMain.
     */
    @Override
    synchronized public void run() {
        try {
            activateRobot();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
