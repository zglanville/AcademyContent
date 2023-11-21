/**
 * @author Thonifho
 */
package za.co.wethinkcode.robotworlds.Server.world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class to create position objects.
 * Action: Tshepo
 */
public class Position {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }

    public int getY() { return y; }

    @Override
    public String toString() {
        return "Position: (" + this.x + ", " + this.y + ")"; 
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (x != position.x) return false;
        return y == position.y;
    }

    /**
     * Overwrite hashCode() so that two Positions with the same x and y coordiates hash the same
     */
    @Override
    public int hashCode()  {
        return Objects.hash(x, y);
    }
    
    /**
     * Checks whether a given position is within the rectangle
     * defined by the top-left and bottom-right positions
     * 
     * @param topLeft
     * @param bottomRight
     * @return true if within the rectange, fales otherwise
     */
    public boolean isIn(Position topLeft, Position bottomRight) {
        boolean withinTop = this.y <= topLeft.getY();
        boolean withinBottom = this.y >= bottomRight.getY();
        boolean withinLeft = this.x >= topLeft.getX();
        boolean withinRight = this.x <= bottomRight.getX();
        return withinTop && withinBottom && withinLeft && withinRight;
    }

    /**
     * Returns a list of Positions between this (exclusive) and other (inclusive)
     *
     * @return ArrayList of Positions
     */
    public ArrayList<Position> getPath(Position other) {
        ArrayList<Position> positionArray = new ArrayList<>();

        if (this.x == other.x) {
            List<Integer> range = getRange(this.y, other.y);
            for (int y : range)
                positionArray.add(new Position(this.x, y));
        }
        else if (this.y == other.y) {
            List<Integer> range = getRange(this.x, other.x);
            for (int x : range)
                positionArray.add(new Position(x, this.y));
        }
        else
            throw new UnsupportedOperationException("getPath does not support diagonals.");
        positionArray.remove(0);
        return positionArray;
    }

    /**
     * Return new Position steps away from this Position given a Direction
     */
    public Position getNewPosition(Direction direction, int steps) {
        int newX = this.getX();
        int newY = this.getY();
        if (Direction.NORTH.equals(direction))
            newY = newY + steps;
        else if (Direction.SOUTH.equals(direction))
            newY = newY - steps;
        else if (Direction.EAST.equals(direction))
            newX = newX + steps;
        else if (Direction.WEST.equals(direction))
            newX = newX - steps;
        return new Position(newX, newY);
    }

    /**
     * Helper function that returns an ArrayList of Integers from int start to int end, inclusive
     */
    private ArrayList<Integer> getRange(int start, int end) {
        ArrayList<Integer> range = new ArrayList<>();
        if (start <= end) {
            List<Integer> increasing = IntStream.rangeClosed(start, end)
                    .boxed().collect(Collectors.toList());
            for (int i : increasing)
                range.add(i);
        }
        else {
            List<Integer> decreasing = IntStream.rangeClosed(end, start)
                    .boxed().collect(Collectors.toList());
            for (int i : decreasing)
                range.add(i);
            Collections.reverse(range);
        }
        return range;
    }
}
