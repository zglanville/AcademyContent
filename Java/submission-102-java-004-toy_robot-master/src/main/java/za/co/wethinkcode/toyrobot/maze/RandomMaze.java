package za.co.wethinkcode.toyrobot.maze;

import za.co.wethinkcode.toyrobot.Position;
import za.co.wethinkcode.toyrobot.world.Obstacle;
import za.co.wethinkcode.toyrobot.world.SquareObstacle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomMaze implements Maze{
    public ArrayList<Obstacle> obstacleList = new ArrayList<Obstacle>();

    public void setObstacles(){
        Random rand = new Random();
        int nrObs = rand.nextInt(1000);
        for (int i = 0; i < nrObs; i++) {
            this.obstacleList.add(new SquareObstacle(rand.nextInt(196) - 100, rand.nextInt(396) - 200));
        }
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
    public RandomMaze(){setObstacles();}

}
