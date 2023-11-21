package za.co.wethinkcode.robotworlds.Server.Web;
import com.fasterxml.jackson.databind.JsonNode;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import za.co.wethinkcode.robotworlds.Server.RobotWorldClient;
import za.co.wethinkcode.robotworlds.Server.RobotWorldJsonClient;
import za.co.wethinkcode.robotworlds.Server.maze.RandomMaze;
import za.co.wethinkcode.robotworlds.Server.robot.Robot;
import za.co.wethinkcode.robotworlds.Server.world.obstacles.Obstacle;
import za.co.wethinkcode.robotworlds.Server.world.obstacles.SquareObstacle;
import za.co.wethinkcode.robotworlds.Server.worldCommands.ServerAdminCommand.ReadWorld;
import za.co.wethinkcode.robotworlds.Server.worldCommands.ServerAdminCommand.RestoreWorld;



import javax.naming.Context;
import java.io.IOException;
import java.util.*;

public class TestDatabase implements WorldDatabase {
    private Map<Integer, RandomMaze> testWorlds;
    private final RobotWorldClient serverClient = new RobotWorldJsonClient();
//    private List<WebRobotHandler> handlers = new ArrayList<>();

//    private RandomMaze world;

    public TestDatabase() {
        testWorlds = new HashMap<>();
//        this.add(World.create("[INSERT WORLD DATA HERE]","testWorldName1"));
//        this.add(World.create("[INSERT WORLD DATA HERE]", "testWorldName2"));
    }

    @Override
    public RandomMaze get(Integer id) {
        return testWorlds.get(id);
    }

    @Override
    public List<RandomMaze> all() {
        return new ArrayList<>(testWorlds.values());
    }

    @Override
    public List<String> allWorlds() {
        return new ReadWorld().getWorldsList();
    }

    @Override
    public RandomMaze current(RandomMaze world) {
//        this.world = world;
        this.add(world);
//        this.add(World.create(world.getObstacles().toString(), "testWorldName0"));
        ArrayList<RandomMaze> worlds = new ArrayList<>(testWorlds.values());
        return worlds.get(0);
    }

    @Override
    public RandomMaze add(RandomMaze testWorld) {
        Integer index = testWorlds.size() + 1;
        testWorld.setId(index);
        testWorlds.put(index, testWorld);
        return testWorld;
    }
    @Override
    public RandomMaze load(RandomMaze world, String worldName){
        new RestoreWorld(world, worldName);
        this.add(world);
        ArrayList<RandomMaze> worlds = new ArrayList<>(testWorlds.values());
        return worlds.get(0);

    }

    @Override
    public Object process(RandomMaze world, String robotName, String Instruction, RobotsData robotsData) {
        try{
        JSONParser parser = new JSONParser();
        JSONObject instructionObject = (JSONObject) parser.parse(Instruction);
        WebRobotHandler handler = new WebRobotHandler(world, robotName, instructionObject, robotsData);
        return handler.activateRobot();}
        catch(ParseException e){
            System.out.print("Couldn't parse the Instruction string: " + e);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Obstacle> addObs(RandomMaze world, String obstacles){
        List<Obstacle> obstaclesList = new ArrayList<>();
        try{
        JSONParser parser = new JSONParser();
        JSONArray obstaclesObject = (JSONArray) parser.parse(obstacles);
        for(int i = 0; i < obstaclesObject.size(); i++){
            JSONObject ob = (JSONObject) obstaclesObject.get(i);
            String x = (String) ob.get("x");
            String y = (String) ob.get("y");

            world.addObstacle(new String[]{x, y});
            obstaclesList.add(new SquareObstacle(Integer.parseInt(x) ,Integer.parseInt(y)));
        }
        System.out.print(obstaclesList);
//        obstaclesObject.keySet().forEach(keyStr ->
//        {
//            Object keyvalue = obstaclesObject.get(keyStr);
//            System.out.println("key: "+ keyStr + " value: " + keyvalue);
//
//                    //for nested objects iteration if required
//                    //if (keyvalue instanceof JSONObject)
//                    //    printJsonObject((JSONObject)keyvalue);
//        });


        }catch(ParseException e){
            System.out.print("Couldn't parse the obstacles string: " + e);
        }
        world.generateOpenSpaces();
        return obstaclesList;
    }

    @Override
    public String deleteObs(RandomMaze world, String obstacles){
//        List<Obstacle> obstaclesList = new ArrayList<>();
        List<SquareObstacle> currentObs = world.getObstacles();
        try{
            JSONParser parser = new JSONParser();
            JSONArray obstaclesObject = (JSONArray) parser.parse(obstacles);

            for(int i = 0; i < obstaclesObject.size(); i++){
                JSONObject ob = (JSONObject) obstaclesObject.get(i);
                String x = (String) ob.get("x");
                String y = (String) ob.get("y");
                for(int j = 0; j < currentObs.size(); j++){
                    if(currentObs.get(j).getBottomLeftX() == Integer.parseInt(x) && currentObs.get(j).getBottomLeftY() == Integer.parseInt(y)){
                        currentObs.remove(currentObs.get(j));
                    }
                }
            }
//        obstaclesObject.keySet().forEach(keyStr ->
//        {
//            Object keyvalue = obstaclesObject.get(keyStr);
//            System.out.println("key: "+ keyStr + " value: " + keyvalue);
//
//                    //for nested objects iteration if required
//                    //if (keyvalue instanceof JSONObject)
//                    //    printJsonObject((JSONObject)keyvalue);
//        });


        }catch(ParseException e){
            System.out.print("Couldn't parse the obstacles string: " + e);
        }
        world.setObstacles(currentObs);
        world.generateOpenSpaces();
        return obstacles;
    }

//    @Override
//    public List<Robot> getRobots(RandomMaze world){
//        return world.getRobots();
//    }



//    @Override
//    public JSON createResponse(RandomMaze world){
//        String obstacles = world.getObstacles().toString();
//        String mines = world.getMines().toString();
//        String pits = world.getPits().toString();
//
//    }

}