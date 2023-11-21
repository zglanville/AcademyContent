/**
 * @author Thonifho, Tshepo
 */
package za.co.wethinkcode.robotworlds.Server.robot;

import za.co.wethinkcode.robotworlds.Server.maze.AbstractMaze;
import za.co.wethinkcode.robotworlds.Server.maze.RandomMaze;
import za.co.wethinkcode.robotworlds.Server.world.Direction;
import za.co.wethinkcode.robotworlds.Server.world.Position;
import za.co.wethinkcode.robotworlds.Server.world.obstacles.MovementStatus;
import za.co.wethinkcode.robotworlds.Server.world.obstacles.Obstacle;
import za.co.wethinkcode.robotworlds.Server.worldCommands.Command;

import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class responsible for the robot object and managing the robot.
 * Constructor require the robot name and the world (Random maze)
 * Action: Thoni & Issa
 */

public class Robot {
    private static final Position TOP_LEFT = new Position(-1,1);
    private static final Position BOTTOM_RIGHT = new Position(1,-1);
    public static final Position CENTRE = new Position(0,0);
    
    private RobotWorldConfiguration config;

    private Position position;
    private Direction currentDirection;
    private String status;
    private String name;
    private RobotStatus robotStatus;
    private int reload;
    private int repair;
    private int mine;
    private int shields;
    private int shots;
    private int visibility;
    public RandomMaze world;
    Socket clientSocket;

    public Robot(String name, RandomMaze world) {
//        world.addRobot(this);
        this.config = world.getConfig();
        this.name = name;
        this.status = "Ready..";
//        int coefficientX = new Random().nextInt(2) == 0 ? -1 : 1;
//        int coefficientY = new Random().nextInt(2) == 0 ? -1 : 1;
//        this.position = new Position(new Random().nextInt(100) * coefficientX, new Random().nextInt(200) * coefficientY);
        // TODO: make position generate within the confines of the world
        if (world.isPositionOpen("[0, 0]")) {
            this.position = new Position(0,0);
        } else {
            List<String> openSpaces = world.getOpenSpaces();
            int randomPositionIndex = ThreadLocalRandom.current().nextInt(0, world.getOpenSpaces().size());
            String[] randomPosition = openSpaces.get(randomPositionIndex).replace(" ","").replace("[","").replace("]","").split(",");
            int x = Integer.parseInt(randomPosition[0]);
            int y = Integer.parseInt(randomPosition[1]);
            this.position = new Position(x,y);
        }
        world.fillPosition(this.position.getX(),this.position.getY());
        this.currentDirection = Direction.NORTH;
        this.robotStatus = RobotStatus.NORMAL;
        this.reload = config.getReload();
        this.repair = config.getRepair();
        this.mine = config.getMine();
        this.shields = config.getShield();
        this.shots = config.getShots();
        this.visibility = config.getVisibility();
        this.world = world;
    }

    /**
     * Getter methods to return the state of the robot
     * @return the object called.
     */

    public int getVisibility() { return this.visibility; }

    public int getReload() { return this.reload; }

    public int getShots() { return this.shots; }

    public String getName() { return this.name; }

    public int getRepair() { return this.repair; }

    public int getMine() { return this.mine; }

    public int getShields() { return this.shields; }

    public RobotStatus getRobotStatus() {return this.robotStatus;}

    public Position getPosition() { return this.position; }

    public Direction getCurrentDirection() { return this.currentDirection; }

    public Socket getSocket() { return this.clientSocket;}

    /**
     * Sets the socket of the robot from socket created
     * @param socket
     */
    public void setClientSocket(Socket socket) { this.clientSocket = socket; }

    /**
     * Sets the robots shots according to the action.
     * @param type (shot should decrement shot, reload to increment shots)
     */

    public void setShots(String type) {
        switch (type) {
            case "shot":
                this.shots--;
                break;
            case "reload":
                this.shots = config.getShots();
                break;
        }
    }

    /**
     * Set the robots remaining shield based on the action
     * @param type (if robot was shot shield decrements by 1, repair will return shields to configured
     *             shield amount and mine will decrement the shield by 3)
     */
    public void setShields(String type) {
        switch (type) {
            case "shot":
                this.shields--;
                break;
            case "repair":
                this.shields = config.getShield();
                break;
            case "mine":
                this.shields = this.shields - 3;
                break;
        }
    }

    /**
     * Set the robot status based on the action
     * @param status (action type)
     */

    public void setRobotStatus(String status) {
        switch (status) {
            case "hitmine":
                this.robotStatus = RobotStatus.HITMINE;
                break;
            case "obstructed":
                this.robotStatus = RobotStatus.OBSTRUCTED;
                break;
            case "repair":
                this.robotStatus = RobotStatus.REPAIRING;
                break;
            case "reload":
                this.robotStatus = RobotStatus.RELOADING;
                break;
            case "dead":
                this.robotStatus = RobotStatus.DEAD;
                break;
            case "mine":
                this.robotStatus = RobotStatus.SETMINE;
            default:
                this.robotStatus = RobotStatus.NORMAL;
        }
    }

    public String getStatus() { return this.status; }

    public void setStatus(String s) { this.status = s;}

    /**
     * Method executes the command provided
     * @param command
     * @return true if the command was executed and false if not
     */
    public boolean handleCommand(Command command) { return command.execute(this); }

    /**
     * Update the robots position based on the number of steps taken.
     * @param nrSteps
     * @return enum MovementStatus (Status of the movement, whether successful
     * obstructed, fell in a pit, stepped on mine.)
     */
    public MovementStatus updatePosition(int nrSteps) {
    
        Position newPosition = this.position.getNewPosition(this.currentDirection, nrSteps);

        // TODO: Robot movement depends on the order of these loops:
        // If there's an obstacle after a mine in the path
        // it will report bumping into the obstacle

        // TODO: Make robot move up to the obstacle
        // and onto/into the pit/mine

        // so blocksPath must return a Position, not a bool

        for (Obstacle pit: this.world.getPits()) {
            if (pit.blocksPath(this.position, newPosition)) {
                return MovementStatus.fell;
            }
        }

        for (Obstacle obstacle: this.world.getObstacles()) {
            if (obstacle.blocksPath(this.position, newPosition)) {
                return MovementStatus.obstructed;
            }
        }

        for (Obstacle mine: this.world.getMines()) {
            if (mine.blocksPath(this.position, newPosition)) {
                this.position = newPosition;
                return MovementStatus.mine;
            }
        }

        // TODO: Make robot move up to the edge
        if (!newPosition.isIn(TOP_LEFT, BOTTOM_RIGHT)) {
            return MovementStatus.edge;
        }

        this.position = newPosition;
        return MovementStatus.successful;
    }

    /**
     *
     * @param @param turnRight if true, then turn 90 degrees to the right, else turn left.
     */
    public void updateDirection(boolean turnRight) {

        if (turnRight) {
            if (Direction.NORTH.equals(this.currentDirection)) {
                this.currentDirection = Direction.EAST;
            }
            else if (Direction.EAST.equals(this.currentDirection)) {
                this.currentDirection = Direction.SOUTH;
            }
            else if (Direction.SOUTH.equals(this.currentDirection)) {
                this.currentDirection = Direction.WEST;
            }
            else if (Direction.WEST.equals(this.currentDirection)) {
                this.currentDirection = Direction.NORTH;
            }
        } else {
            if (Direction.NORTH.equals(this.currentDirection)) {
                this.currentDirection = Direction.WEST;
            }
            else if (Direction.WEST.equals(this.currentDirection)) {
                this.currentDirection = Direction.SOUTH;
            }
            else if (Direction.SOUTH.equals(this.currentDirection)) {
                this.currentDirection = Direction.EAST;
            }
            else if (Direction.EAST.equals(this.currentDirection)) {
                this.currentDirection = Direction.NORTH;
            }
        }
    }

    @Override
    public String toString() {
        return "[" + this.position.getX() + ", " + this.position.getY() + "] "
                + this.name + "> " + this.status;
    }

    public RandomMaze getWorld() {
        return this.world;
    }
}
