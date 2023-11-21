package za.co.wethinkcode.toyrobot.world;

import za.co.wethinkcode.toyrobot.Position;
import za.co.wethinkcode.toyrobot.maze.Maze;

import java.util.List;

public class TurtleWorld extends AbstractWorld{
    public Maze maze;
    public TurtleWorld(Maze maze) {
        this.maze = maze;
    }

    @Override
    public void updateDirection(boolean turnRight) {

    }

    @Override
    public Position getPosition() {
        return null;
    }

    @Override
    public Direction getCurrentDirection() {
        return null;
    }

    @Override
    public boolean isNewPositionAllowed(Position position) {
        return false;
    }

    @Override
    public boolean isAtEdge() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public List<Obstacle> getObstacles() {
        return null;
    }

    @Override
    public void showObstacles() {

    }

    @Override
    public Maze getMaze() {
        return null;
    }
}
