package za.co.wethinkcode.robotworlds.Server.Web;

//

import io.javalin.Javalin;
import picocli.CommandLine;
import io.javalin.plugin.openapi.OpenApiOptions;
import io.javalin.plugin.openapi.OpenApiPlugin;
import io.javalin.plugin.openapi.ui.SwaggerOptions;
import io.swagger.v3.oas.models.info.Info;
import za.co.wethinkcode.robotworlds.Server.maze.RandomMaze;
import za.co.wethinkcode.robotworlds.Server.robot.RobotWorldConfiguration;
import za.co.wethinkcode.robotworlds.Server.robot.RobotHandler;
import za.co.wethinkcode.robotworlds.Server.world.Position;
import za.co.wethinkcode.robotworlds.Server.CommandLineConfig;

import java.util.ArrayList;
import java.util.List;

import static io.javalin.apibuilder.ApiBuilder.*;

public class WorldsWebServer {
    public static List<WebRobotHandler> robotHandlers = new ArrayList<>();

    private final Javalin server;

    /*Manually testing the server:
 curl --request GET "http://localhost:5000/worlds" --include
 curl --request GET 'http://localhost:5000/admin/save/testWorld' --include
 curl --request DELETE 'http://localhost:5000/admin/obstacles' -d '[{"x":"0","y":"0"},{"x":"0","y":"0"}]' --include
 curl --request DELETE 'http://localhost:5000/admin/robot/testName' --include
 curl --request POST 'http://localhost:5000/admin/obstacles' -d '[{"x":"0","y":"0"},{"x":"0","y":"0"}]' --include
 curl --request GET 'http://localhost:5000/admin/robots' --include
 curl --request GET 'http://localhost:5000/world/obsworld11' --include
 curl --request GET 'http://localhost:5000/world' --include
 curl --request POST 'http://localhost:5000/robot/testName' -d '{"robot":"testName","arguments":["shooter","5","5"],"command":"launch"}' --include
 curl --request POST 'http://localhost:5000/robot/testName' -d '{"robot":"testName","arguments":[],"command":"look"}' --include
 curl --request POST 'http://localhost:5000/robot/testName' -d '{"robot":"testName","arguments":["1"],"command":"forward"}' --include
    * */
    public WorldsWebServer(RandomMaze world, RobotsData robotsData) {
        System.out.print("Open spaces" + world.getOpenSpaces());

        server = Javalin.create(config -> {
            config.registerPlugin(new OpenApiPlugin(getOpenApiOptions()));
            config.defaultContentType = "application/json";
        });
        this.server.get("/world", context -> WorldApiHandler.getCurrentWorld(context, world));
        this.server.get("/admin/load/{worldName}", context -> WorldApiHandler.getWorld(context, world));
//        this.server.post("/robot/{robotName}", context -> WorldApiHandler.launch(context, world));
        this.server.post("/robot/{robotName}", context -> WorldApiHandler.command(context, world, robotsData));
        this.server.get("/admin/robots", context -> WorldApiHandler.getRobots(context, robotsData));
        this.server.post("/admin/save/{worldName}", context -> WorldApiHandler.saveWorld(context, world));
        this.server.post("/admin/obstacles", context -> WorldApiHandler.addObstacles(context, world));
        this.server.delete("/admin/robot/{robotName}", context -> WorldApiHandler.deleteRobot(context,robotsData, world));
        this.server.delete("/admin/obstacles", context -> WorldApiHandler.deleteObstacles(context, world));
        //        this.server.get("/worlds", context -> WorldApiHandler.getAll(context));
        this.server.get("/worlds", context -> WorldApiHandler.getWorldsList(context));
//        this.server.get("/world/{id}", context -> WorldApiHandler.getOne(context));
//        this.server.post("/worlds", context -> WorldApiHandler.create(context));
    }

    public static void main(String[] args) {

        new CommandLine(new CommandLineConfig()).execute(args);
        RobotWorldConfiguration config = CommandLineConfig.getConfig();
        RandomMaze world = new RandomMaze(config);
        RobotsData robotsData = new RobotsData();
        WorldsWebServer server = new WorldsWebServer(world, robotsData);
        server.start(5000);
    }

    public void start(int port) {
        this.server.start(port);
    }

    private OpenApiOptions getOpenApiOptions() {
        Info applicationInfo = new Info()
                .version("1.0")
                .description("My Application");
        return new OpenApiOptions(applicationInfo).path("/swagger-docs")
                .path("/swagger-docs")
                .swagger(new SwaggerOptions("/swagger").title("My Swagger Documentation"));
    }


    public void stop() {
        this.server.stop();
    }
}