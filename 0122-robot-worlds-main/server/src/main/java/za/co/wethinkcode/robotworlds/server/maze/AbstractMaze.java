package za.co.wethinkcode.robotworlds.server.maze;

import za.co.wethinkcode.robotworlds.server.robot.Robot;
import za.co.wethinkcode.robotworlds.server.world.BottomlessPits;
import za.co.wethinkcode.robotworlds.server.world.MapObstacles;
import za.co.wethinkcode.robotworlds.server.world.Mine;
import za.co.wethinkcode.robotworlds.server.world.Obstacle;
import za.co.wethinkcode.robotworlds.server.position.Position;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMaze implements Maze {

    protected List<Obstacle> obstaclesList = new ArrayList<>();
    protected List<Obstacle> pitsList = new ArrayList<>();
    protected List<Obstacle> mineList = new ArrayList<>();

    /**
     * checks if za.co.wethinkcode.server.maze has an obstacle that block the path from coords (x1,y1) to (x1,y2).
     * za.co.wethinkcode.server.robot can only move horizontally or vertically through the za.co.wethinkcode.server.maze so x1==x2 or y1==y2.
     * @param a first za.co.wethinkcode.server.position
     * @param b second za.co.wethinkcode.server.position
     * @return true if there is an obstacle in the za.co.wethinkcode.server.maze
     */
    @Override
    public boolean blocksObsPath(Position a, Position b) {
        for (Obstacle obstacle : getObstacles()) {
            if (obstacle.blocksPosition(b) || obstacle.blocksPath(a, b)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean blocksPitsPath(Position a, Position b) {
        for (Obstacle obstacle : getPitsList()) {
            if (obstacle.blocksPosition(b) || obstacle.blocksPath(a, b)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean blocksMine(Position a, Position b) {
        for (Obstacle mine : getMineList()) {
            if (mine.blocksPosition(b) || mine.blocksPath(a, b)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Create the obstacles list, by adding in a new obstacles at the za.co.wethinkcode.server.position.
     * @param position: takes in the za.co.wethinkcode.server.position, and pass it into the new square za.co.wethinkcode.server.position.
     * */
    public void createObstacles(Position position) {
        this.obstaclesList.add(new MapObstacles(position.getX(), position.getY()));
    }

    public void createPits(Position position) {
        this.pitsList.add(new BottomlessPits(position.getX(), position.getY()));
    }

    public void createMines(Position position) {
        this.mineList.add(new Mine(position.getX(), position.getY()));
    }

    public void removeMines(Robot target, Position minePosition) {
        System.out.println(mineList.toString());
//        for (Obstacle mine : mineList) {
//            if ((target.getWorld().getPosition().getX() == mine.getBottomLeftX()) && (target.getWorld().getPosition().getY() == mine.getBottomLeftY())) {
//                this.mineList.remove(mineList[mine]);
//            }
//        }
//
//        this.mineList.remove(mineList.)
    }


    public void setObstacles(List<Obstacle> obstacle) {
        this.obstaclesList = obstacle;
    }

    public List<Obstacle> getObstacles() {
        return this.obstaclesList;
    }

    public void setPitsList(List<Obstacle> pits) {
        this.pitsList = pits;
    }

    public List<Obstacle> getPitsList() {return this.pitsList;}

    public void setMineList(List<Obstacle> mineList) {this.mineList = mineList;}

    public List<Obstacle> getMineList() {return this.mineList;}

}
