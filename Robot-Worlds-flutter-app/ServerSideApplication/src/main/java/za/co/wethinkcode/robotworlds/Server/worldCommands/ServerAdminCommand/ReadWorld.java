package za.co.wethinkcode.robotworlds.Server.worldCommands.ServerAdminCommand;

import za.co.wethinkcode.robotworlds.Server.maze.AbstractMaze;
import za.co.wethinkcode.robotworlds.Server.maze.RandomMaze;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReadWorld {
    public static final String DISK_DB_URL_PREFIX = "jdbc:sqlite:";
    private String dbUrl = null;
    AbstractMaze world;
    private List<String> worlds = new ArrayList<>();

    public ReadWorld() {

//        this.world = world;
        final File dbFile = new File("ServerSideApplication/src/main/java/za/co/wethinkcode/robotworlds/Server/RobotWorldsDatabase.sqlite");
        if (dbFile.exists()) {
            dbUrl = DISK_DB_URL_PREFIX + "RobotWorldsDatabase.sqlite";
        } else {
            throw new IllegalArgumentException("Database file " + dbFile.getName() + " not found.");
        }
        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            readWorldNames(connection);
            System.out.println("Worlds: " + worlds);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void readWorldNames(final Connection connection) throws SQLException {

        try (final Statement stmt = connection.createStatement()) {
            boolean gotAResultSet = stmt.execute("SELECT worldName FROM worlds");
            if (!gotAResultSet)
                throw new RuntimeException("fail");
            try (ResultSet results = stmt.getResultSet()) {
                while(results.next()) {
                    String worldName = results.getString("worldName");
                    worlds.add(worldName);
                }
            }
        }
    }

    public boolean isNameTaken(String newName){
        for (String name : this.worlds){
            if (Objects.equals(newName, name)){
                return true;
            }
        }
        return false;
    }

    public List<String> getWorldsList(){
        // new ReadWorld(); // TODO: remove
        return worlds;
    }
}
