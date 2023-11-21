package za.co.wethinkcode.robotworlds.Server.world.obstacles;

import java.util.ArrayList;

/**
 * Method to keep history of obstacles in the robot view
 * @author Issa
 */
public class HistoricalObstacleVisibility {

    public static ArrayList<Obstacle> obstacleHistory = new ArrayList<>();

    public static void addObstacle(Obstacle obstacle){obstacleHistory.add(obstacle);}

    public static ArrayList<Obstacle> getObstacleHistory(){return obstacleHistory;}

}
