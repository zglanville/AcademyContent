package za.co.wethinkcode.robotworlds.Server.worldCommands.ServerAdminCommand;

import za.co.wethinkcode.robotworlds.Server.maze.RandomMaze;
import za.co.wethinkcode.robotworlds.Server.world.obstacles.Obstacle;

import java.io.File;
import java.sql.*;

public class SaveWorld {

    public static final String DISK_DB_URL_PREFIX = "jdbc:sqlite:";
    public static final String SEPARATOR = "\t";
    private String dbUrl = null;
    RandomMaze world;
    String worldName;

    public SaveWorld(RandomMaze world, String worldName) {
        this.world = world;
        this.worldName = worldName;
        this.world.setName(worldName);
        final File dbFile = new File("ServerSideApplication/src/main/java/za/co/wethinkcode/robotworlds/Server/RobotWorldsDatabase.sqlite");
        if (dbFile.exists()) {
                dbUrl = DISK_DB_URL_PREFIX + "RobotWorldsDatabase.sqlite";
            } else {
                throw new IllegalArgumentException("Database file " + dbFile.getName() + " not found.");
            }

        try (Connection connection = DriverManager.getConnection(dbUrl)) { // <1>Other ways to get a Connection
            useTheDatabase(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    private void useTheDatabase(final Connection connection) throws SQLException {   
//        deleteData(connection);
        createData(connection);
    }

    private void createData(final Connection connection) throws SQLException {

        try (final Statement stmt = connection.createStatement()) {
            String query;

            //Storing world name and size (height, width)
            query = "INSERT INTO worlds(worldName, height, width) VALUES (\"" + 
                worldName + "\", \"" + world.getHeight() + "\", \"" + world.getWidth() + "\")";
            // System.out.println(query);
            boolean gotAResultCreateWorld = stmt.execute(query);

            if (gotAResultCreateWorld) {
                throw new RuntimeException("Unexpectedly got a SQL resultset [CreateWorld].");
            } else {
                final int updateCount = stmt.getUpdateCount();
                if (updateCount == 1) {
                    System.out.println("1 row INSERTED into worlds");
                } else {
                    throw new RuntimeException("Expected 1 row to be inserted into words , but got " + updateCount);
                }
            }

            //Storing obstacles (x, y, size)
            for (Obstacle ob : world.getObstacles()) {
                query = "INSERT INTO obstacles(worldName, x, y, size) VALUES (\"" + worldName + "\", \"" + ob.getBottomLeftX() + "\", \"" + ob.getBottomLeftY() + "\", \"" + ob.getSize() + "\")";
                boolean gotAResultSetObstacles = stmt.execute(query);
                // System.out.println(query);
            if (gotAResultSetObstacles) {
                    throw new RuntimeException("Unexpectedly got a SQL resultset [Obstacles].");
                }
            }

            //Storing mines (x, y, size)
            for (Obstacle mine : world.getMines()) {
                boolean gotAResultSetMines = stmt.execute(
                        "INSERT INTO mines(x, y, size) VALUES (\"" + worldName + "\", \"" + mine.getBottomLeftX() + "\", \"" + mine.getBottomLeftY() + "\", \"" + mine.getSize() + "\")");

                if (gotAResultSetMines) {
                    throw new RuntimeException("Unexpectedly got a SQL resultset.");
                }
            }

            //Storing pits(x, y, size)
            for (Obstacle pit : world.getPits()) {
                boolean gotAResultSetPits = stmt.execute(
                        "INSERT INTO pits(x, y, size) VALUES (\"" + worldName + "\", \"" + pit.getBottomLeftX()+"\", \"" + pit.getBottomLeftY()+"\", \"" + pit.getSize()+"\")");

                if (gotAResultSetPits) {
                    throw new RuntimeException("Unexpectedly got a SQL resultset.");
                }
            }
        }
    }

//    private void updateData(final Connection connection) throws SQLException {
//        try (final Statement stmt = connection.createStatement()) {
//            boolean gotAResultSet = stmt.execute(// return value should be `false` as before
//                    "UPDATE products SET name = \"Sourflat IPA\" WHERE name = \"Lemondrop IPA\""
//           );
//            if (gotAResultSet) {
//                throw new RuntimeException("Unexpectedly got a SQL resultset.");
//            } else {
//                final int updateCount = stmt.getUpdateCount();
//                if (updateCount == 1) {
//                    System.out.println("1 row UPDATED in products");
//                } else {
//                    throw new RuntimeException("Expected 1 row to be inserted, but got " + updateCount);
//                }
//            }
//        }
//    }

    private void deleteData(final Connection connection) throws SQLException {

        try (final Statement stmt = connection.createStatement()) {

            boolean gotAResultSetObstacles = stmt.execute(
                    "DELETE FROM obstacles"
            );
            boolean gotAResultSetMines = stmt.execute(
                    "DELETE FROM mines"
            );
            boolean gotAResultSetPits = stmt.execute(
                    "DELETE FROM pits"
            );
            boolean gotAResultSetName = stmt.execute(
                    "DELETE FROM worlds"
            );

        }
    }

//    private void readData(final Connection connection) throws SQLException {
//        try (final Statement stmt = connection.createStatement()) {
//            boolean gotAResultSet = stmt.execute(
//                    "SELECT p.name productname, i.type, i.name ingredname, r.qty, r.units "
//                            + "FROM products p, recipe_quantities r, ingredients i "
//                            + "WHERE "
//                            + "        productname = 'Buffalo Bay Blonde'"
//                            + "    AND p.id = r.product_id "
//                            + "    AND r.ingredient_id = i.id"
//           );
//            if (!gotAResultSet) {
//                throw new RuntimeException("Expected a SQL resultset, but we got an update count instead!");
//            }
//            try (ResultSet results = stmt.getResultSet()) {
//                int rowNo = 1;
//                while(results.next()) {
//                    final String productName = results.getString("productname");
//                    final String ingredType = results.getString("type");
//                    final String ingredName = results.getString("ingredname");
//                    final int qty = results.getInt("qty");
//                    final String units = results.getString("units");
//
//                    final StringBuilder b = new StringBuilder("Row ").append(rowNo).append(SEPARATOR)
//                            .append(productName).append(SEPARATOR)
//                            .append(ingredType).append(SEPARATOR)
//                            .append(ingredName).append(SEPARATOR)
//                            .append(qty).append(SEPARATOR)
//                            .append(units);
//                    System.out.println(b.toString());
//                }
//            }
//        }
//    }

//    private void processCmdLineArgs(String[] args) {
//        if (args.length == 2 && args[ 0 ].equals("-f")) {
//            final File dbFile = new File("/home/wtc/shenzou-robot/ServerSideApplication/src/main/java/za/co/wethinkcode/robotworlds/Server/"+args[ 1 ]);
//            if (dbFile.exists()) {
//                dbUrl = DISK_DB_URL_PREFIX + args[ 1 ];
//            } else {
//                throw new IllegalArgumentException("Database file " + dbFile.getName() + " not found.");
//            }
//        } else {
//            throw new RuntimeException("Expected arguments '-f filenanme' but didn't find it.");
//        }
//    }

}
