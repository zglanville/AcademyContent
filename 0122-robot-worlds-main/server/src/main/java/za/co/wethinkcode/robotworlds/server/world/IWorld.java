package za.co.wethinkcode.robotworlds.server.world;

import za.co.wethinkcode.robotworlds.server.position.Position;

import java.util.List;

/**
 * Your Text and Turtle worlds must implement this interface.
 */
public interface IWorld {

    void showPits();

    void showMines();

    List<Obstacle> getPits();

    List<Obstacle> getMines();

    /**
     * Enum used to track direction
     */
    enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST
    }

    /**
     * Enum that indicates response for updatePosition request
     */
    enum UpdateResponse {
        SUCCESS, //za.co.wethinkcode.server.position was updated successfully
        FAILED_OUTSIDE_WORLD, //za.co.wethinkcode.server.robot will go outside za.co.wethinkcode.server.world limits if allowed, so it failed to update the za.co.wethinkcode.server.position
        FAILED_OBSTRUCTED, //za.co.wethinkcode.server.robot obstructed by at least one obstacle, thus cannot proceed.
        BOTTOMLESS_PIT,
        ROBOT_ENCOUNTERED,
        MINE
    }

    Position CENTRE = new Position(0,0);

    /**
     * Updates the za.co.wethinkcode.server.position of your za.co.wethinkcode.server.robot in the za.co.wethinkcode.server.world by moving the nrSteps in the robots current direction.
     * @param nrSteps steps to move in current direction
     * @return true if this does not take the za.co.wethinkcode.server.robot over the za.co.wethinkcode.server.world's limits, or into an obstacle.
     */
    UpdateResponse updatePosition(int nrSteps); //same in both - AbstractWorld

    /**
     * Updates the current direction your za.co.wethinkcode.server.robot is facing in the za.co.wethinkcode.server.world by cycling through the directions UP, RIGHT, BOTTOM, LEFT.
     * @param turnRight if true, then turn 90 degrees to the right, else turn left.
     */
    void updateDirection(boolean turnRight); // Different, Abstract method

    /**
     * Retrieves the current za.co.wethinkcode.server.position of the za.co.wethinkcode.server.robot
     */
    Position getPosition(); //Same in both - AbstractWorld

    /**
     * Gets the current direction the za.co.wethinkcode.server.robot is facing in relation to a za.co.wethinkcode.server.world edge.
     * @return Direction.UP, RIGHT, DOWN, or LEFT
     */
    Direction getCurrentDirection(); //Different in both -Turtle za.co.wethinkcode.server.commands

    /**
     * Checks if the new za.co.wethinkcode.server.position will be allowed, i.e. falls within the constraints of the za.co.wethinkcode.server.world, and does not overlap an obstacle.
     * @param position the za.co.wethinkcode.server.position to check
     * @return true if it is allowed, else false
     */
    boolean isNewPositionAllowed(Position position); //Same in both- AbstractWorld

    /**
     * Checks if the za.co.wethinkcode.server.robot is at one of the edges of the za.co.wethinkcode.server.world
     * @return true if the za.co.wethinkcode.server.robot's current is on one of the 4 edges of the za.co.wethinkcode.server.world
     */
    boolean isAtEdge(); //Same in both - AbstractWorld

    /**
     * Reset the za.co.wethinkcode.server.world by:
     * - moving current za.co.wethinkcode.server.robot za.co.wethinkcode.server.position to center 0,0 coordinate
     * - removing all obstacles
     * - setting current direction to UP
     */
    void reset(); //Different in both because of turtle

    /**
     * @return the list of obstacles, or an empty list if no obstacles exist.
     */
    List<Obstacle> getObstacles(); //Same in Both

    /**
     * Gives opportunity to za.co.wethinkcode.server.world to draw or list obstacles.
     */
    void showObstacles(); // Different - AbstractWorld

    public void setCurrentDirection(Direction currentDirection);

//    public void setConfigReader (za.co.wethinkcode.server.server.configs.ConfigReader config);
}
