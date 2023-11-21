package za.co.wethinkcode.toyrobot.world;

import za.co.wethinkcode.toyrobot.Play;
import za.co.wethinkcode.toyrobot.Position;
import za.co.wethinkcode.toyrobot.maze.Maze;
import za.co.wethinkcode.toyrobot.maze.RandomMaze;

import java.util.ArrayList;
import java.util.List;

public class TextWorld extends AbstractWorld{
    public Maze maze = new RandomMaze();

    @Override
    public Maze getMaze(){
        return maze;
    }

    public TextWorld(Maze maze){
        this.maze = maze;
    }

//    @Override
//    public UpdateResponse updatePosition(int nrSteps) {
//        return null;
//    }

    @Override
    public void updateDirection(boolean turnRight) {
        int directionIndex;

        if (turnRight) {
            directionIndex = getCurrentDirection().ordinal() + 1;
            if (directionIndex == 4)
                directionIndex = 0;
        } else {
            directionIndex = getCurrentDirection().ordinal() - 1;
            if (directionIndex == -1)
                directionIndex = 3;
        }
        setCurrentDirection(Direction.values()[directionIndex]);
    }

    @Override
    public Position getPosition() {
        return getPPosition();
    }

    @Override
    public Direction getCurrentDirection() {
        return this.currentDirection;
    }

    @Override
    public boolean isNewPositionAllowed(Position position) {
        return position.isIn(TOP_LEFT, BOTTOM_RIGHT);
    }

    @Override
    public boolean isAtEdge() {
        if (getPosition().getX() == 100 || getPosition().getX() == -100 ||
                getPosition().getY() == 200 || getPosition().getY() == -200){
            return true;
        }
        return false;
    }

    @Override
    public void reset() {
        setCurrentDirection(Direction.UP);
        setPosition(CENTRE);
    }

    @Override
    public List<Obstacle> getObstacles() {
        return null;
    }

    @Override
    public void showObstacles() {
        Play.printWords("There are some obstacles:");
        for (Obstacle obstacle:this.maze.getObstacles()){
            Play.printWords("- At position " + obstacle.getBottomLeftX() + "," + obstacle.getBottomLeftY()
                + " (to " + (obstacle.getBottomLeftX()+4) + "," + (obstacle.getBottomLeftY()+4) + ")");
        }
    }
}
