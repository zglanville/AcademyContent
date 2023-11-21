package za.co.wethinkcode.toyrobot.maze;

import za.co.wethinkcode.toyrobot.Position;
import za.co.wethinkcode.toyrobot.world.Obstacle;
import za.co.wethinkcode.toyrobot.world.SquareObstacle;

import java.util.ArrayList;
import java.util.List;

public class SimpleMaze implements Maze{

    public ArrayList<Obstacle> obstacleList = new ArrayList<Obstacle>();

    public void setObstacles(){
        this.obstacleList.add(new SquareObstacle(1,1));
    }

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

    public SimpleMaze(){
        setObstacles();
    }
}
