package za.co.wethinkcode.robotworlds.server.maze;

import za.co.wethinkcode.robotworlds.server.world.Obstacle;
import za.co.wethinkcode.robotworlds.server.position.Position;

import java.util.List;

/**
 * Interface to represent a za.co.wethinkcode.server.maze. A World will be loaded with a Maze, and will delegate the work to check if a path is blocked by certain obstacles etc to this za.co.wethinkcode.server.maze instance.
 */
public interface Maze {
    /**
     * @return the list of obstacles, or an empty list if no obstacles exist.
     */
    List<Obstacle> getObstacles();

    List<Obstacle> getPits();

    List<Obstacle> getMines();

    /**
     * Checks if this za.co.wethinkcode.server.maze has at least one obstacle that blocks the path that goes from coordinate (x1, y1) to (x2, y2).
     * Since our za.co.wethinkcode.server.robot can only move in horizontal or vertical lines (no diagonals yet), we can assume that either x1==x2 or y1==y2.
     * @param a first za.co.wethinkcode.server.position
     * @param b second za.co.wethinkcode.server.position
     * @return `true` if there is an obstacle is in the way
     */
    boolean blocksObsPath(Position a, Position b);

    boolean blocksPitsPath(Position a, Position b);

    boolean blocksMine(Position a, Position b);

    int generateRandomNumber(int upper,int lower,int max);

    int getUpperHeight();

    int getUpperWidth();



    /**
     * Setter to set the obstacles.
     * @param obstacle: takes a parameter of the obstacles it will set.
     * */
    void setObstacles(List<Obstacle> obstacle);
}
