/**
 *
 * @author Thonifho
 */
package za.co.wethinkcode.robotworlds.Server.maze;

import za.co.wethinkcode.robotworlds.Server.robot.RobotWorldConfiguration;
import za.co.wethinkcode.robotworlds.Server.world.Position;
import za.co.wethinkcode.robotworlds.Server.world.obstacles.Edge;
import za.co.wethinkcode.robotworlds.Server.world.obstacles.Obstacle;
import za.co.wethinkcode.robotworlds.Server.world.obstacles.SquareMine;
import za.co.wethinkcode.robotworlds.Server.world.obstacles.SquareObstacle;
import za.co.wethinkcode.robotworlds.Server.world.obstacles.SquarePit;

import java.util.ArrayList;
import java.util.List;

/**
 * Class implements the maze interface to handle.
 * Methods to handle the instantiation of the Random maze
 * Action: Thoni
 */

public abstract class AbstractMaze implements Maze {
    RobotWorldConfiguration config;

    List<SquareObstacle> obstacles = new ArrayList<>();
    List<SquareMine> mines = new ArrayList<>();
    List<SquarePit> pits = new ArrayList<>();
    List<Edge> edges = new ArrayList<>();

    String name;
    int width;
    int height;
    int size;
    Position TOP_LEFT;
    Position BOTTOM_RIGHT;

    List<String> openSpaces = new ArrayList<>();

    /**
     * @return list of obstacles and things
     */
    public List<SquareObstacle> getObstacles() { return obstacles; }
    public List<Edge> getEdges() { return edges; }
    public List<SquareMine> getMines() { return mines; }
    public List<SquarePit> getPits() { return pits; }
    public int getWidth(){ return width; }
    public int getHeight() { return height; }
    public List<String> getOpenSpaces() {
        return openSpaces;
    }
    public RobotWorldConfiguration getConfig() { return config; }
    public void setObstacles(List<SquareObstacle> obs){this.obstacles = obs;}

    public void clearWorld() {
        obstacles.clear();
        mines.clear();
        pits.clear();
        openSpaces.clear();
//        edges.clear();
    }

    /**
     * Checks if the path is blocked
     * @param a first position
     * @param b second position
     * @return true is the path is blocked and false if not
     */
    @Override
    public boolean blocksPath(Position a, Position b) {
        for (Obstacle each : obstacles)
            if (each.blocksPath(a,b))
                return true;
        return false;
    }

    /**
     * Get the name of the robot
     * @return robot name
     */
    @Override
    public String getName() {
        return name;
    }

    public void setBounds(int width, int height) {
        this.TOP_LEFT = new Position(-width/2, height/2);
        this.BOTTOM_RIGHT = new Position(width/2, -height/2);
    }

    /**
     * Adds a mine obstacle to the mines list
     * @param mine obstacle created when the mine is set.
     */
    public void addMine(SquareMine mine) {
        mines.add(mine);
    }

    public void addPit(SquarePit pit) {
        pits.add(pit);
    }
    public void addObstacle(SquareObstacle obstacle) {
        obstacles.add(obstacle);
    }

    public void setSize(int w, int h) {
        width = w;
        height = h;
    }

    public void generateEdges() {

        int TLX = this.TOP_LEFT.getX() - 1;
        int TLY = this.TOP_LEFT.getY() + 1;
        int BRX = this.BOTTOM_RIGHT.getX() + 1;
        int BRY = this.BOTTOM_RIGHT.getY() - 1;

        for (int i = TLX; i <= BRX; i++) {
            edges.add(new Edge(i, TLY));
            edges.add(new Edge(i, BRY));
        }
        for (int j = TLY - 1; j < BRY; j++) {
            edges.add(new Edge(TLX, j));
            edges.add(new Edge(BRX, j));
        }
    }

    public abstract void setId(Integer index);

    public abstract Integer getId();

    public abstract void setName(String worldName);

//    private boolean isPositionOpen(int x, int y) {
//        List<Obstacle> obstacleList = this.getObstacles();
//        List<Obstacle> mineList = this.getMines();
//        List<RobotHandler> robotHandlerList = ServerMain.robots;
//
//        //checks if launch position on obstacle
//        for (Obstacle obstacle : obstacleList) {
//            if (obstacle.getBottomLeftX() <= x && x <= obstacle.getBottomLeftX() + 4) {
//                if (obstacle.getBottomLeftY() <= y && y <= obstacle.getBottomLeftY() + 4) {
//                    return false;
//                }
//            }
//        }
//        //checks if launch position on mine
//
//        for (Obstacle mine : mineList) {
//            if (mine.getBottomLeftX() <= x && x <= mine.getBottomLeftX() + 4) {
//                if (mine.getBottomLeftY() <= y && y <= mine.getBottomLeftY() + 4) {
//                    return false;
//                }
//            }
//        }
//
//        //checks if launchPosition on robot
//        for (RobotHandler robots : robotHandlerList) {
//
//            Robot robot = robots.getRobot();
////            Position robotPosition = robot.getPosition(); /// this line is making the test hang!!!!!!
//            robot.getPosition();
////            if (robotPosition.getX() == x && robotPosition.getY() == y) {
////                return false;
////            }
//        }
//        return false;
//
//    }
//
//    public boolean isWorldFull(){
//        RobotConfiguration config = new RobotConfiguration();
//        JSONObject configData = config.getConfigData();
//        Object worldHeight = configData.get("height");
//        Object worldWidth = configData.get("width");
//        JSONObject worldConfig = new RobotConfiguration().getWorld();
//        int width = (int) ((long) worldConfig.get("width"));
//        int height = (int) ((long) worldConfig.get("height"));
////        boolean isEmpty = false;
//
//        int tempWidth = width;
//        while(height != 0){
//            while(tempWidth != 0){
//                if(isPositionOpen(tempWidth, height)){
//                    return false;
//                }
//                tempWidth--;
//
//            }
//            height--;
//            tempWidth = width;
//
//
//        }
//        return true;
//    }

//    public boolean getIsPositionOpen(Position launchPosition){
//        return isPositionOpen(launchPosition);
//
//    }
}
