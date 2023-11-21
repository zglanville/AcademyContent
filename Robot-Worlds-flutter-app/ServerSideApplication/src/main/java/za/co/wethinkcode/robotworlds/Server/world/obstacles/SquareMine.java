/**
 * @author Thonifho
 */
package za.co.wethinkcode.robotworlds.Server.world.obstacles;

import za.co.wethinkcode.robotworlds.Server.world.Position;

/**
 * Implements the creation of the mine obstacle
 * Action: Thoni
 */

public class SquareMine extends SquareObstacle {
    int x;
    int y;

    public SquareMine(int x, int y) {
        super(x, y);
    }

    /**
     * Getter methods to return the size of the mine obstacle
     * @return size of the mine
     */
    @Override
    public int getSize() { return 1; }

    /**
     * Method to check whether a future position is occupied by a mine
     * @param position the position to check
     * @return true if a mine is in place
     */
    @Override
    public boolean blocksPosition(Position position) {
        return position.equals(new Position(this.x, this.y));
    }
}
