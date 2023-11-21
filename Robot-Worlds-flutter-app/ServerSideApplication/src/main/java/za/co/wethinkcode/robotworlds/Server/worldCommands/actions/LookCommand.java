package za.co.wethinkcode.robotworlds.Server.worldCommands.actions;

import org.json.simple.JSONObject;
import za.co.wethinkcode.robotworlds.Server.ServerMain;
import za.co.wethinkcode.robotworlds.Server.robot.Robot;
import za.co.wethinkcode.robotworlds.Server.robot.RobotHandler;
import za.co.wethinkcode.robotworlds.Server.world.Position;
import za.co.wethinkcode.robotworlds.Server.world.obstacles.Edge;
import za.co.wethinkcode.robotworlds.Server.world.obstacles.HistoricalObstacleVisibility;
import za.co.wethinkcode.robotworlds.Server.world.obstacles.Obstacle;
import za.co.wethinkcode.robotworlds.Server.world.obstacles.SquareObstacle;
import za.co.wethinkcode.robotworlds.Server.worldCommands.Command;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to return the obstacles in the robots view limited to configured visibility.
 * Action: Issa
 */
public class LookCommand extends Command {

    public static List<JSONObject> obstacleInVision = new ArrayList<>();

    public LookCommand() { super("look");}

    @Override
    public boolean execute(Robot target) {

        ArrayList<Obstacle> obstaclesHistory = HistoricalObstacleVisibility.getObstacleHistory();
        ArrayList<Object> obstructions = new ArrayList<>();
        
        obstructions.addAll(target.world.getObstacles());
        //obstructions.addAll(ServerMain.robots);
        obstructions.addAll(target.world.getPits());
        //obstructions.addAll(target.world.getEdges());
        obstructions.addAll(target.world.getMines());

        Position currentPosition = target.getPosition();
        Position northPosition = new Position(currentPosition.getX(), currentPosition.getY()+ target.getVisibility());
        Position southPosition = new Position(currentPosition.getX(), currentPosition.getY() - target.getVisibility());
        Position westPosition = new Position(currentPosition.getX()- target.getVisibility(), currentPosition.getY() );
        Position eastPosition = new Position(currentPosition.getX()+ target.getVisibility(), currentPosition.getY() );



        for (Object obstruction: obstructions) {
            JSONObject obstaclePosition = new JSONObject();
            Obstacle obs = (Obstacle) obstruction;
            if (obs.blocksPath(currentPosition, northPosition)) {
                obstaclesHistory.add(obs);
                obstaclePosition.put("direction", "North");
                obstaclePosition.put("type", getType(obs));
                int distance = obs.getBottomLeftY() - currentPosition.getY();
                obstaclePosition.put("distance", distance);

                obstacleInVision.add(obstaclePosition);
            }
            else if (obs.blocksPath(currentPosition,southPosition)) {
                obstaclesHistory.add(obs);
                obstaclePosition.put("direction", "South");
                obstaclePosition.put("type", getType(obs));
                int distance = currentPosition.getY()- obs.getBottomLeftY();
                obstaclePosition.put("distance", distance);

                obstacleInVision.add(obstaclePosition);
            }
            else if (obs.blocksPath(currentPosition, westPosition)) {
                obstaclesHistory.add(obs);
                obstaclePosition.put("direction", "West");
                obstaclePosition.put("type", getType(obs));
                int distance = currentPosition.getX()- obs.getBottomLeftX();
                obstaclePosition.put("distance", distance);

                obstacleInVision.add(obstaclePosition);
            }
            else if (obs.blocksPath(currentPosition,eastPosition)) {
                obstaclesHistory.add(obs);
                obstaclePosition.put("direction", "East");
                obstaclePosition.put("type", getType(obs));
                int distance = obs.getBottomLeftX() - currentPosition.getX();
                obstaclePosition.put("distance", distance);

                obstacleInVision.add(obstaclePosition);
            }
        }
        
        /*for (Obstacle obstacle: target.world.getObstacles()) {
            JSONObject obstaclePosition = new JSONObject();
            if (obstacle.blocksPath(currentPosition, northPosition)) {
                obstaclesHistory.add(obstacle);
                obstaclePosition.put("direction", "North");
                obstaclePosition.put("type", "Obstacle");
                int distance = obstacle.getBottomLeftY() - currentPosition.getY();
                obstaclePosition.put("distance", distance);

                obstacleInVision.add(obstaclePosition);
            }
            else if (obstacle.blocksPath(currentPosition,southPosition)) {
                obstaclesHistory.add(obstacle);
                obstaclePosition.put("direction", "South");
                obstaclePosition.put("type", "Obstacle");
                int distance = currentPosition.getY()- obstacle.getBottomLeftY();
                obstaclePosition.put("distance", distance);

                obstacleInVision.add(obstaclePosition);
            }
            else if (obstacle.blocksPath(currentPosition, westPosition)) {
                obstaclesHistory.add(obstacle);
                obstaclePosition.put("direction", "West");
                obstaclePosition.put("type", "Obstacle");
                int distance = currentPosition.getX()- obstacle.getBottomLeftX();
                obstaclePosition.put("distance", distance);

                obstacleInVision.add(obstaclePosition);
            }
            else if (obstacle.blocksPath(currentPosition,eastPosition)) {
                obstaclesHistory.add(obstacle);
                obstaclePosition.put("direction", "East");
                obstaclePosition.put("type", "Obstacle");
                int distance = obstacle.getBottomLeftX() - currentPosition.getX();
                obstaclePosition.put("distance", distance);

                obstacleInVision.add(obstaclePosition);
            }
        }*/

        /*for (SquarePit obstacle: target.world.getPits()) {
            JSONObject obstaclePosition = new JSONObject();
            if (obstacle.blocksPath(currentPosition, northPosition)) {
                obstaclesHistory.add(obstacle);
                obstaclePosition.put("direction", "North");
                obstaclePosition.put("type", "Pit");
                int distance = obstacle.getBottomLeftY() - currentPosition.getY();
                obstaclePosition.put("distance", distance);
                obstacleInVision.add(obstaclePosition);
            }
            else if (obstacle.blocksPath(currentPosition,southPosition)) {
                obstaclesHistory.add(obstacle);
                obstaclePosition.put("direction", "South");
                obstaclePosition.put("type", "Pit");
                int distance = currentPosition.getY()- obstacle.getBottomLeftY();
                obstaclePosition.put("distance", distance);

                obstacleInVision.add(obstaclePosition);
            }
            else if (obstacle.blocksPath(currentPosition, westPosition)) {
                obstaclesHistory.add(obstacle);
                obstaclePosition.put("direction", "West");
                obstaclePosition.put("type", "Pit");
                int distance = currentPosition.getX()- obstacle.getBottomLeftX();
                obstaclePosition.put("distance", distance);

                obstacleInVision.add(obstaclePosition);
            }
            else if (obstacle.blocksPath(currentPosition,eastPosition)) {
                obstaclesHistory.add(obstacle);
                obstaclePosition.put("direction", "East");
                obstaclePosition.put("type", "Pit");
                int distance = obstacle.getBottomLeftX() - currentPosition.getX();
                obstaclePosition.put("distance", distance);

                obstacleInVision.add(obstaclePosition);
            }
        }*/

        for (RobotHandler robot: ServerMain.robots) {

            JSONObject obstaclePosition = new JSONObject();
            Position position = robot.getRobot().getPosition();
            Obstacle obstacle = new SquareObstacle(position.getX(), position.getY());

            if (obstacle.blocksPath(currentPosition, northPosition)) {
                obstaclePosition.put("direction", "North");
                obstaclePosition.put("type", "robot");
                int distance = obstacle.getBottomLeftY() - currentPosition.getY();
                obstaclePosition.put("distance", distance);

                obstacleInVision.add(obstaclePosition);
            }
            else if (obstacle.blocksPath(currentPosition,southPosition)) {
                obstaclePosition.put("direction", "South");
                obstaclePosition.put("type", "robot");
                int distance = currentPosition.getY()- obstacle.getBottomLeftY();
                obstaclePosition.put("distance", distance);

                obstacleInVision.add(obstaclePosition);
            }
            else if (obstacle.blocksPath(currentPosition, westPosition)) {
                obstaclePosition.put("direction", "West");
                obstaclePosition.put("type", "robot");
                int distance = currentPosition.getX()- obstacle.getBottomLeftX();
                obstaclePosition.put("distance", distance);

                obstacleInVision.add(obstaclePosition);
            }
            else if (obstacle.blocksPath(currentPosition,eastPosition)) {
                obstaclePosition.put("direction", "East");
                obstaclePosition.put("type", "robot");
                int distance = obstacle.getBottomLeftX() - currentPosition.getX();
                obstaclePosition.put("distance", distance);

                obstacleInVision.add(obstaclePosition);
            }
        }

        /*for (Obstacle obstacle: target.world.getMines()) {
            JSONObject obstaclePosition = new JSONObject();
            if (obstacle.blocksPath(currentPosition, northPosition)) {
                obstaclesHistory.add(obstacle);
                obstaclePosition.put("direction", "North");
                obstaclePosition.put("type", "Mine");
                int distance = northPosition.getY() - currentPosition.getY();
                obstaclePosition.put("distance", distance);

                obstacleInVision.add(obstaclePosition);
            }
            else if (obstacle.blocksPath(currentPosition,southPosition)) {
                obstaclesHistory.add(obstacle);
                obstaclePosition.put("direction", "South");
                obstaclePosition.put("type", "Mine");
                int distance = currentPosition.getY()-southPosition.getY();
                obstaclePosition.put("distance", distance);

                obstacleInVision.add(obstaclePosition);
            }
            else if (obstacle.blocksPath(currentPosition, westPosition)) {
                obstaclesHistory.add(obstacle);
                obstaclePosition.put("direction", "West");
                obstaclePosition.put("type", "Mine");
                int distance = currentPosition.getX()-westPosition.getX();
                obstaclePosition.put("distance", distance);

                obstacleInVision.add(obstaclePosition);
            }
            else if (obstacle.blocksPath(currentPosition,eastPosition)) {
                obstaclesHistory.add(obstacle);
                obstaclePosition.put("direction", "East");
                obstaclePosition.put("type", "Mine");
                int distance = eastPosition.getX() - currentPosition.getX();
                obstaclePosition.put("distance", distance);

                obstacleInVision.add(obstaclePosition);
            }
        }*/

        //edge 
        for (Edge edge: target.world.getEdges()) {
            JSONObject obstaclePosition = new JSONObject();
            if (edge.blocksPath(currentPosition, northPosition)) {
                obstaclePosition.put("direction", "North");
                obstaclePosition.put("type", "edge");
                int distance = edge.getBottomLeftY() - currentPosition.getY();
                obstaclePosition.put("distance", distance);
                //obstaclePosition.put("distance", 1);

                obstacleInVision.add(obstaclePosition);
            }
            else if (edge.blocksPath(currentPosition,southPosition)) {
                obstaclePosition.put("direction", "South");
                obstaclePosition.put("type", "edge");
                int distance = currentPosition.getY()- edge.getBottomLeftY();
                obstaclePosition.put("distance", distance);
                //obstaclePosition.put("distance", 1);


                obstacleInVision.add(obstaclePosition);
            }
            else if (edge.blocksPath(currentPosition, westPosition)) {
                obstaclePosition.put("direction", "West");
                obstaclePosition.put("type", "edge");
                int distance = currentPosition.getX()- edge.getBottomLeftX();
                obstaclePosition.put("distance", distance);
                //obstaclePosition.put("distance", 1);


                obstacleInVision.add(obstaclePosition);
            }
            else if (edge.blocksPath(currentPosition,eastPosition)) {
                obstaclePosition.put("direction", "East");
                obstaclePosition.put("type", "edge");
                int distance = edge.getBottomLeftX() - currentPosition.getX();
                obstaclePosition.put("distance", distance);
                //obstaclePosition.put("distance", 1);


                obstacleInVision.add(obstaclePosition);
            }
        }

        return true;
    }

    private static String getType(Obstacle obstruction) {
        switch (obstruction.getClass().getName()) {
            case "SquareObstacle":
                return "Obstacle";
            case "SquareMine":
                return "Mine";
            case "SquarePit":
                return "Pit";
        }
        return null;
    }

    public static List<JSONObject> getObstacleInVision() {return obstacleInVision;}

}
