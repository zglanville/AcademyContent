package za.co.wethinkcode.toyrobot.maze;

import za.co.wethinkcode.toyrobot.Position;
import za.co.wethinkcode.toyrobot.world.Obstacle;
import za.co.wethinkcode.toyrobot.world.SquareObstacle;

import java.util.ArrayList;
import java.util.List;

public class EmptyMaze implements Maze{
    public ArrayList<Obstacle> obstacleList = new ArrayList<Obstacle>();

    @Override
    public List<Obstacle> getObstacles() {
        return this.obstacleList;
    }

    @Override
    public boolean blocksPath(Position a, Position b) {
        for (Obstacle i:obstacleList) {
            if (i.blocksPath(a, b)){
                return true;
            }
        }
        return false;
    }

}
