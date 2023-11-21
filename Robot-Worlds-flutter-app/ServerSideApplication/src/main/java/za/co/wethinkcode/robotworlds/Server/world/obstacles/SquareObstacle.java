/**
 * @author Thonifho
 */
package za.co.wethinkcode.robotworlds.Server.world.obstacles;

import za.co.wethinkcode.robotworlds.Server.world.Position;

// TODO: Obstacles cannot overlap each other.

/**
 * Class to implement square obstacles
 * Action: Thoni
 */
public class SquareObstacle implements Obstacle {
    int x;
    int y;

    /**
     * Obstacle is noted by the x and y coordinate as well as a boolean
     * @param x
     * @param y
     */
    public SquareObstacle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter methods access the values of various values.
     * @return BottomLeftX, BottomLeftY, size of the obstacle.
     */
    @Override
    public int getBottomLeftX() { return this.x; }

    @Override
    public int getBottomLeftY() { return this.y; }

    @Override
    public int getSize() { return 5; }
    // public int getSize() { return 1; }

    /**
     * Method to check if the future position is accessible
     * @param position the position to check
     * @return true if it is blocked
     */
    @Override
    public boolean blocksPosition(Position position) {
        return position.equals(new Position(this.x, this.y));
    }

    // // TODO: Obstacles will be defined by their TL and BR positions, 
    // //       for now they are 1x1
    // @Override
    // public boolean blocksPosition(Position position) {
    //     for (int i = 0; i < this.size; i++) {
    //         if ((this.x + i == position.getX()) && (position.getY() == this.y))
    //             return true;
    //         if ((this.x == position.getX()) && (position.getY() == this.y+i))
    //             return true;
    //     }
    //     return false;
    // }

    /**
     * Checks whether the path to be traversed if blocked
     * @param a first position
     * @param b second position
     * @return true if the path is blocked
     */
    @Override
    public boolean blocksPath(Position a, Position b) {
        if (a.getX() == b.getX() && a.getY() == b.getY())
            return true;
        Position check;
        if (a.getX() == b.getX()) {
            if (a.getY() < b.getY()) {
                for (int i = a.getY(); i < b.getY(); i++) {
                    check = new Position(a.getX(), i);
                    if (blocksPosition(check))
                        return true;
                }
            }
            else {
                for (int i = b.getY(); i < a.getY(); i++) {
                    check = new Position(a.getX(), i);
                    if (blocksPosition(check))
                        return true;
                }
            }
        }
        else if (a.getY() == b.getY()) {
            //checkX
            if (a.getX() < b.getX()) {
                for (int i = a.getX(); i < b.getX(); i++) {
                    check = new Position(i, a.getY());
                    if (blocksPosition(check))
                        return true;
                }
            }
            else {
                for (int i = b.getX(); i < a.getX(); i++) {
                    check = new Position(i, b.getY());
                    if (blocksPosition(check))
                        return true;
                }
            }
        }
        return false;
    }
}
