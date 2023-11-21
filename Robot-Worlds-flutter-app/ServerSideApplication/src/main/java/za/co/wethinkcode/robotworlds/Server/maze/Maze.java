/**
 * @author Thonifho
 */
package za.co.wethinkcode.robotworlds.Server.maze;

import za.co.wethinkcode.robotworlds.Server.world.Position;

/**
 * Interface to represent a maze. A World will be loaded with a Maze, and will delegate the work to check if a path is blocked by certain obstacles etc to this maze instance.
 */
public interface Maze {

    /**
     * Checks if this maze has at least one obstacle that blocks the path that goes from coordinate (x1, y1) to (x2, y2).
     * Since our robot can only move in horizontal or vertical lines (no diagonals yet), we can assume that either x1==x2 or y1==y2.
     *  @param a first position
     *  @param b second position
     *  @return `true` if there is an obstacle is in the way
     */

    boolean blocksPath(Position a, Position b);

    /**
     * Function returns Name of selected maze.
     *  @returns type String - name of the maze.
     */
    String getName();
}
