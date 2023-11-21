package za.co.wethinkcode.robotworlds.Server.worldCommands.ServerAdminCommand;


import za.co.wethinkcode.robotworlds.Server.maze.RandomMaze;
import za.co.wethinkcode.robotworlds.Server.world.obstacles.SquareMine;
import za.co.wethinkcode.robotworlds.Server.world.obstacles.SquareObstacle;
import za.co.wethinkcode.robotworlds.Server.world.obstacles.SquarePit;

/**
 * Class to provide the representation of all world artifacts
 * @author Issa
 */
public class DumpCommand {

    RandomMaze world;

    public DumpCommand(RandomMaze world) {
        System.out.println("WORLD STATE:");
        this.world = world;
        showWorldObstacles();
        new RobotsCommand();
    }

    /**
     * Display all obstacles
     */
    private void showWorldObstacles() {

        System.out.println("<<<<<<<<<<<<<<OBSTACLES>>>>>>>>>>>>>>");
        for (SquareObstacle obstacle : this.world.getObstacles())
            System.out.printf("[%d,%d] to [%d,%d]  ", obstacle.getBottomLeftX(),
                obstacle.getBottomLeftY(), obstacle.getBottomLeftX() + 4, obstacle.getBottomLeftY() + 4);
        System.out.println("\nNumber of obstacles: " + this.world.getObstacles().size());

        System.out.println("<<<<<<<<<<<<<<PITS>>>>>>>>>>>>>>");
        for (SquarePit pit : this.world.getPits())
            System.out.printf("[%d,%d] to [%d,%d]  ", pit.getBottomLeftX(),
                pit.getBottomLeftY(), pit.getBottomLeftX() + 4, pit.getBottomLeftY() + 4);
        System.out.println("\nNumber of pits: " + this.world.getPits().size());

        System.out.println("<<<<<<<<<<<<<<MINES>>>>>>>>>>>>>>");
        for (SquareMine mine : this.world.getMines())
            System.out.printf("[%d,%d] to [%d,%d]  ", mine.getBottomLeftX(),
                mine.getBottomLeftY(), mine.getBottomLeftX() + 4, mine.getBottomLeftY() + 4);
        System.out.println("\nNumber of mines: " + this.world.getMines().size());
    }
}
