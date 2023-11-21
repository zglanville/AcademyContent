package za.co.wethinkcode.robotworlds.Server.Web;

import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import io.javalin.http.NotFoundResponse;
// import kong.unirest.json.JSONObject;
import org.json.simple.parser.ParseException;
import za.co.wethinkcode.robotworlds.Server.maze.RandomMaze;
import za.co.wethinkcode.robotworlds.Server.robot.Robot;
import za.co.wethinkcode.robotworlds.Server.world.Position;
import za.co.wethinkcode.robotworlds.Server.world.obstacles.Obstacle;
import za.co.wethinkcode.robotworlds.Server.worldCommands.ServerAdminCommand.ReadWorld;
import za.co.wethinkcode.robotworlds.Server.worldCommands.ServerAdminCommand.RestoreWorld;
import za.co.wethinkcode.robotworlds.Server.worldCommands.ServerAdminCommand.RobotsCommand;
import za.co.wethinkcode.robotworlds.Server.worldCommands.ServerAdminCommand.SaveWorld;

import java.util.ArrayList;
import java.util.List;

public class WorldApiHandler {
    private static final WorldDatabase database = new TestDatabase();
//    private static List<Robot> robots = new ArrayList<>();

    public static void getCurrentWorld(Context context, RandomMaze world) {
        context.json(database.current(world));
    }
    public static void getWorld(Context context, RandomMaze world) {
        String worldName = context.pathParamAsClass("worldName", String.class).get();
        ReadWorld worldData = new ReadWorld();
        if(worldData.isNameTaken(worldName)){
            context.json(database.load(world, worldName));
            System.out.println("World " + worldName + " loaded.");
        }
        else{
            throw new NotFoundResponse("World does not exist: " + worldName);
        }

    }

//
//    public static void getAll(Context context) {
//        context.json(database.all());
//    }

    public static void getWorldsList(Context context) {
        context.json(database.allWorlds());
    }



//    public static void getOne(Context context) {
//        Integer id = context.pathParamAsClass("id", Integer.class).get();
//        RandomMaze world = database.get(id);
//        if (world == null) {
//            throw new NotFoundResponse("World not found: " + id);
//        }
//        context.json(world);
//
//    public static void launch(Context context, RandomMaze world) {
//        String robotName = context.pathParamAsClass("robotName", String.class).get();
//        context.header("Location", "/robot/" + robotName);
//        context.status(HttpCode.CREATED);
//        //sets response to be sent to user
//        context.json("Robot launched");
//        RandomMaze world = context.bodyAsClass(RandomMaze.class);
//        RandomMaze newWorld = database.add(world);
//        context.header("Location", "/world/" + newWorld.getId());
//        context.status(HttpCode.CREATED);
//        context.json(newWorld);
    //}
    public static void command(Context context, RandomMaze world, RobotsData robotsData) throws ParseException {
        String robotName = context.pathParamAsClass("robotName", String.class).get();
        String instruction = context.body();
//        context.result(command);
        context.status(HttpCode.CREATED);
        context.json(database.process(world, robotName, instruction, robotsData));
    }


    public static void getRobots(Context context, RobotsData robotsData){
        context.json(robotsData.getRobots());
    }

    public static void addObstacles(Context context, RandomMaze world){
        String obstacles = context.body();
        context.json(database.addObs(world, obstacles));
    }
    public static void saveWorld(Context context, RandomMaze world){
        String worldName = context.pathParamAsClass("worldName", String.class).get();
        SaveWorld saveWorld = new SaveWorld(world, worldName);
        context.status(HttpCode.CREATED);
    }

    public static void deleteRobot(Context context, RobotsData robotsData, RandomMaze world) throws ParseException {
        String robotName = context.pathParamAsClass("robotName", String.class).get();
//        database.process(world,robotName, "{\"command\":\"shutdown\"}", robotsData);
        Position bot = robotsData.getRobot(robotName).getPosition();
        world.freePosition(bot.getX(), bot.getY());
        context.json(robotsData.deleteRobotData(robotName));

    }

    public static void deleteObstacles(Context context, RandomMaze world){
        String obstacles = context.body();
        context.json(database.deleteObs(world, obstacles));
    }
//    public static void create(Context context) {
//        RandomMaze world = context.bodyAsClass(RandomMaze.class);
//        RandomMaze newWorld = database.add(world);
//        context.header("Location", "/world/" + newWorld.getId());
//        context.status(HttpCode.CREATED);
//        context.json(newWorld);
//    }

}
