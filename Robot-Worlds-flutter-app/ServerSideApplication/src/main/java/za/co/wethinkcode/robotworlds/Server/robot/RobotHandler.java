/**
 * @author Thonifho
 */
package za.co.wethinkcode.robotworlds.Server.robot;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import za.co.wethinkcode.robotworlds.Server.ServerMain;
import za.co.wethinkcode.robotworlds.Server.clientHandler.InputHandler;
import za.co.wethinkcode.robotworlds.Server.clientHandler.ServerMessageProtocol;
import za.co.wethinkcode.robotworlds.Server.maze.RandomMaze;
import za.co.wethinkcode.robotworlds.Server.worldCommands.ShutdownCommand;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
/**
 * Method handles the entire robot and world. Implements runnable interface for the
 * purpose of threading.
 * Action: Thoni & Issa
 */
public class RobotHandler implements Runnable {
    private Robot robot = null;
    private Socket socket;

    private PrintStream out;
    private BufferedReader in;

    private boolean robotAlive = true;
    private RandomMaze world;
    public boolean isLaunched = false;

    public RobotHandler(Socket socket, RandomMaze world) throws IOException {
        this.socket = socket;

        this.out = new PrintStream(socket.getOutputStream());
        this.in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));

        this.world = world;
    }

    /**
     * Activates the robot and is the base method for the entire robot operation
     * @throws IOException if unable to read data
     * @throws ClassNotFoundException if a class that has been called is not found
     */
    private void activateRobot() throws IOException, ClassNotFoundException {

        ServerMessageProtocol testResponse = new ServerMessageProtocol();

        try {
            while (robotAlive) {

                if(socket.isClosed()) {
                    robotAlive = false;
                    new ShutdownCommand();
                }

                String input = in.readLine();
                if (input == null) {
                    System.out.println("in.readLine() == null, disconnecting");
                    this.disconnect();
                    return;
                }

                JSONParser parser = new JSONParser();
                JSONObject incomingRequest = (JSONObject) parser.parse(input);
                System.out.println("in: " + incomingRequest);

                String name = (String) incomingRequest.get("robot");
                String command = (String) incomingRequest.get("command");

                if (command.equalsIgnoreCase("launch")) {
                    System.out.println("\n[Status] Creating a robot..\n");
                    GetRobotName nameCheck = new GetRobotName(name);
                    boolean nameTaken = nameCheck.getIsTaken();
                    // boolean isPositionOpen = world.getIsPositionOpen();

                    if (nameTaken) {
                        JSONObject nameTakenResponse = nameCheck.nameTakenJSONObject();
                        System.out.println("out: " + nameTakenResponse);
                        out.println(nameTakenResponse.toString());
                        out.flush();
                        continue;
                    }
                    if (world.getOpenSpaces().size() == 0) {
                        JSONObject response = InputHandler.errorNoMoreSpace();
                        System.out.println("out: " + response);
                        out.println(response.toString());
                        out.flush();
                        System.out.println("[Status] World is Full waiting " +
                                "a client to disconnect.");
                        continue;
                    }

                    robot = new Robot(name, world);
                    // world.addLaunch();
                    isLaunched = true;
                    robot.setClientSocket(socket);
                    JSONObject protocol = testResponse.createJSON("launch", "", robot);
                    System.out.println("out: " + protocol);
                    out.println(protocol.toString());
                    out.flush();
                }

                // state command given but robot name doesn't match 
                if (robot != null && command.equalsIgnoreCase("state") && 
                !robot.getName().equalsIgnoreCase(name)) {
                    JSONObject value = InputHandler.errorRobotDoesNotExist();
                    System.out.println("out: " + value.toString());
                    out.println(value.toString());
                    out.flush();
                } // attempt interpret robot command here
                else if (!command.equalsIgnoreCase("off")
                        && robot != null && !command.equalsIgnoreCase("launch")) {
                    JSONObject inputHandler = 
                        new InputHandler(incomingRequest, robot).getOutput();
                    System.out.println("out: " + inputHandler);
                    out.println(inputHandler.toString());
                    out.flush();
                } // switch robot off here
                else if (command.equalsIgnoreCase("off")) {
                    break;
                } // reaches here when robot was never launched, so command unsupported
                else if (robot == null) {
                    JSONObject value = InputHandler.unsupportedCommand();
                    System.out.println("out: " + value.toString());
                    out.println(value.toString());
                    out.flush();
                }

                System.out.println("[Client] " + robot.getName() +
                                    " : " + robot.getStatus());

                if (robot.getRobotStatus() == RobotStatus.DEAD) {
                    robotAlive = false;
                    new ShutdownCommand();
                }
            }
        } catch (EOFException e) {
            disconnect();
        } catch (SocketException e) {
            disconnect();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * When client disconnects.
     */
    public void disconnect() throws IOException {
        if (this.robot != null) {
            world.freePosition(this.robot.getPosition().getX(), this.robot.getPosition().getY());
            System.out.println("Freed position occupied by " + this.robot.getName());
        }
        ServerMain.robots.remove(this);
        System.out.println("Closing socket: " + socket.getRemoteSocketAddress());
        socket.close();
    }

    /**
     * Get the robot that has been instantiated.
     * @return Robot object
     */
    public Robot getRobot() { return this.robot; }

    /**
     * Get the socket that the robot has been initialized on
     * @return socket object
     */
    public Socket getSocket() { return this.socket; }

    public RandomMaze getWorld() { return this.world; }
 
    /**
     * run method to handle for the creation of the thread in the ServerMain.
     */
    @Override
    synchronized public void run() {
        try {
            activateRobot();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
