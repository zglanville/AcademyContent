package za.co.wethinkcode.robotworlds.Server.Web;

import org.json.simple.parser.ParseException;
import za.co.wethinkcode.robotworlds.Server.maze.RandomMaze;
import za.co.wethinkcode.robotworlds.Server.robot.Robot;
import za.co.wethinkcode.robotworlds.Server.world.obstacles.Obstacle;

import java.util.List;

public interface WorldDatabase {

    RandomMaze get(Integer id);

    List<RandomMaze> all();

    RandomMaze add(RandomMaze world);

    RandomMaze current(RandomMaze world);

    RandomMaze load(RandomMaze world, String worldName);

    Object process(RandomMaze world, String robotName, String command, RobotsData robotsData) throws ParseException;

    List<Obstacle> addObs(RandomMaze world, String obstacles);

    String deleteObs(RandomMaze world, String obstacles);

    List<String> allWorlds();

//    List<Robot> getRobots(RandomMaze world);

}
