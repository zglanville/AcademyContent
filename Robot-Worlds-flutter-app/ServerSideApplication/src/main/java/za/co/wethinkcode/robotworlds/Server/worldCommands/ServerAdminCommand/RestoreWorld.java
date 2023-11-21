package za.co.wethinkcode.robotworlds.Server.worldCommands.ServerAdminCommand;

import za.co.wethinkcode.robotworlds.Server.maze.AbstractMaze;
import za.co.wethinkcode.robotworlds.Server.maze.RandomMaze;
import za.co.wethinkcode.robotworlds.Server.world.obstacles.SquareMine;
import za.co.wethinkcode.robotworlds.Server.world.obstacles.SquareObstacle;
import za.co.wethinkcode.robotworlds.Server.world.obstacles.SquarePit;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RestoreWorld {

    public static final String DISK_DB_URL_PREFIX = "jdbc:sqlite:";
    private String dbUrl = null;
    AbstractMaze world;
    String worldName;
//    private List<String> worlds = new ArrayList<>();

    public RestoreWorld(RandomMaze world, String worldName) {
        this.worldName = worldName;
        world.clearWorld();
        this.world = world;
        this.world.setName(worldName);
        final File dbFile = new File("ServerSideApplication/src/main/java/za/co/wethinkcode/robotworlds/Server/RobotWorldsDatabase.sqlite");
        if (dbFile.exists()) {
            dbUrl = DISK_DB_URL_PREFIX + "RobotWorldsDatabase.sqlite";
        } else {
            throw new IllegalArgumentException("Database file " + dbFile.getName() + " not found.");
        }
        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            readWorldSize(connection);
            readObstacles(connection);
            readPits(connection);
            readMines(connection);
            world.generateOpenSpaces();
//            AbstractMaze.generateEdges();
//            readWorldNames(connection);
//            System.out.println("Worlds: " + worlds);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void readMines(final Connection connection) throws SQLException {

        try (final Statement stmt = connection.createStatement()) {
            boolean gotAResultSet = stmt.execute("SELECT * FROM mines WHERE worldName = " + "\"" + worldName + "\"");
            if (!gotAResultSet)
                throw new RuntimeException("fail");
            try (ResultSet results = stmt.getResultSet()) {
                while(results.next()) {
                    int x = results.getInt("x");
                    int y = results.getInt("y");
                    world.addMine(new SquareMine(x, y));
                }
            }
        }
    }

    private void readPits(final Connection connection) throws SQLException {

        try (final Statement stmt = connection.createStatement()) {
            boolean gotAResultSet = stmt.execute("SELECT * FROM pits WHERE worldName = " + "\"" + worldName + "\"");
            if (!gotAResultSet)
                throw new RuntimeException("fail");
            try (ResultSet results = stmt.getResultSet()) {
                while(results.next()) {
                    int x = results.getInt("x");
                    int y = results.getInt("y");
                    world.addPit(new SquarePit(x, y));
                }
            }
        }
    }

    private void readObstacles(final Connection connection) throws SQLException {

        try (final Statement stmt = connection.createStatement()) {
            boolean gotAResultSet = stmt.execute("SELECT * FROM obstacles WHERE worldName = " + "\"" + worldName + "\"");
            if (!gotAResultSet)
                throw new RuntimeException("fail");
            try (ResultSet results = stmt.getResultSet()) {
                while(results.next()) {
                    int x = results.getInt("x");
                    int y = results.getInt("y");
                    world.addObstacle(new SquareObstacle(x, y));
                }
            }
        }
    }

    private void readWorldSize(final Connection connection) throws SQLException {

        try (final Statement stmt = connection.createStatement()) {
            boolean gotAResultSet = stmt.execute("SELECT width, height FROM worlds WHERE worldName = " + "\"" + worldName + "\"");
            if (!gotAResultSet)
                throw new RuntimeException("fail");
            try (ResultSet results = stmt.getResultSet()) {
                results.next();
                int w = results.getInt("width");
                int h = results.getInt("height");
                world.setSize(w, h);
            }
        }
    }

}
