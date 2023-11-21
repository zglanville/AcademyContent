/**
 * @author abecker
 */
package za.co.wethinkcode.robotworlds.Server.world.obstacles;

import za.co.wethinkcode.robotworlds.Server.world.Position;

/**
 * Class to implement edges. Extends SquareObstacle
 * Action: Thoni
 */
public class Edge extends SquareObstacle {

    /**
     * Edge is noted by the x and y coordinate
     * @param x
     * @param y
     */
    public Edge(int x, int y) {
        super(x, y);
    }

    @Override
    public String toString() {
        return "Edge (" + this.x + ", " + this.y + ")";
    }

    @Override
    public int getSize() { return 0; }

    /**
     * Method to check if the future position is accessible
     * @param position the position to check
     * @return true if it is blocked
     */
    @Override
    public boolean blocksPosition(Position position) {
        if ((this.x == position.getX()) && (position.getY() == this.y))
            return true;
        return false;
    }

//     /**
//      * Checks whether the path to be traversed if blocked
//      * @param a first position
//      * @param b second position
//      * @return true if the path is blocked
//      */
//     @Override
//     public boolean blocksPath(Position a, Position b) {
//         if (a.getX() == b.getX() && a.getY() == b.getY())
//             return true;
//         Position check;
//         if (a.getX() == b.getX()) {
//             if (a.getY() < b.getY()) {
//                 for (int i = a.getY(); i < b.getY(); i++) {
//                     check = new Position(a.getX(), i);
//                     if (blocksPosition(check))
//                         return true;
//                 }
//             }
//             else {
//                 for (int i = b.getY(); i < a.getY(); i++) {
//                     check = new Position(a.getX(), i);
//                     if (blocksPosition(check))
//                         return true;
//                 }
//             }
//         }
//         else if (a.getY() == b.getY()) {
//             //checkX
//             if (a.getX() < b.getX()) {
//                 for (int i = a.getX(); i < b.getX(); i++) {
//                     check = new Position(i, a.getY());
//                     if (blocksPosition(check))
//                         return true;
//                 }
//             }
//             else {
//                 for (int i = b.getX(); i < a.getX(); i++) {
//                     check = new Position(i, b.getY());
//                     if (blocksPosition(check))
//                         return true;
//                 }
//             }
//         }
//         return false;
//     }
}
