package za.co.wethinkcode.robotworlds.server.world;

import za.co.wethinkcode.robotworlds.server.Server;
import za.co.wethinkcode.robotworlds.server.position.Position;
import za.co.wethinkcode.robotworlds.server.configReader.ConfigReader;
import za.co.wethinkcode.robotworlds.server.maze.Maze;

import java.util.List;
import java.util.Set;

public class TextWorld extends AbstractWorld{

    public TextWorld(Maze maze, ConfigReader config){
        this.maze = maze;
        this.config = config;
        this.TOP_LEFT = setWorldBoundary("topLeft");
        this.BOTTOM_RIGHT = setWorldBoundary("bottomRight");
        this.position = setRandomSpawn();
        this.currentDirection = Direction.NORTH;
    }

    public UpdateResponse updatePosition(int nrSteps) {
        int oldX = this.position.getX();
        int oldY = this.position.getY();
        int newX = this.position.getX();
        int newY = this.position.getY();

        switch (this.currentDirection) {
            case NORTH:
                newY = newY + nrSteps;
                break;
            case SOUTH:
                newY = newY - nrSteps;
                break;
            case WEST:
                newX = newX - nrSteps;
                break;
            case EAST:
                newX = newX + nrSteps;
                break;
        }

        Position oldPosition = new Position(oldX, oldY);
        Position newPosition = new Position(newX, newY);
        if (maze.blocksPitsPath(oldPosition, newPosition)) {
            System.out.println("Bottomless Pit");
            this.position = newPosition;
            return UpdateResponse.BOTTOMLESS_PIT;
        } else if (maze.blocksObsPath(oldPosition, newPosition)) {
            System.out.println("Obstacle in way");
            return UpdateResponse.FAILED_OBSTRUCTED;
        }   else if (maze.blocksMine(oldPosition, newPosition)) {
            System.out.println("Mine Encountered");
            this.position = newPosition;
            return UpdateResponse.MINE;
        } else if (isNewPositionAllowed(newPosition)) {
            System.out.println("Within World.");
            this.position = newPosition;
            return UpdateResponse.SUCCESS;
        }else if (isNewPositionAllowed(newPosition)){
            System.out.println("Robot Encountered");
            this.position = newPosition;
            return UpdateResponse.ROBOT_ENCOUNTERED;
        }
        else {
            System.out.println("Outside World");
            return UpdateResponse.FAILED_OUTSIDE_WORLD;
        }
    }

    public void updateDirection(boolean turnRight) {
        int directionIndex;

        if (turnRight) {
            directionIndex = getCurrentDirection().ordinal() + 1;
            if (directionIndex == 4)
                directionIndex = 0;
        } else {
            directionIndex = getCurrentDirection().ordinal() - 1;
            if (directionIndex == -1)
                directionIndex = 3;
        }
        setCurrentDirection(Direction.values()[directionIndex]);
    }

    public void reset() {
        List<Obstacle> emptyObstacleList = this.maze.getObstacles();
        emptyObstacleList.removeAll(this.maze.getObstacles());
        this.maze.setObstacles(emptyObstacleList);
        this.position = CENTRE;
        this.currentDirection = Direction.NORTH;
    }

    public List<Obstacle> getObstacles() {
        return maze.getObstacles();
    }

    public List<Obstacle> getPits() {return maze.getPits();}

    public List<Obstacle> getMines() {return maze.getMines();}

    public void showObstacles() {
        maze.getObstacles();
        for (Obstacle maze : maze.getObstacles()) {
            System.out.println("Obstacle: - At za.co.wethinkcode.server.position "+ maze.getBottomLeftX()+ "," +maze.getBottomLeftY()+ " (to "+
                    (maze.getBottomLeftX() + maze.getSize()) + "," + (maze.getBottomLeftY() + maze.getSize()) + ")");
        }
    }

    public void showPits() {
//        za.co.wethinkcode.server.maze.getPits();
        for (Obstacle maze : maze.getPits()) {
            System.out.println("Pits: - At za.co.wethinkcode.server.position "+ maze.getBottomLeftX()+ "," +maze.getBottomLeftY()+ " (to "+
                    (maze.getBottomLeftX() + maze.getSize()) + "," + (maze.getBottomLeftY() + maze.getSize()) + ")");
        }
    }

    public void showMines() {
//        za.co.wethinkcode.server.maze.getPits();
        for (Obstacle mine : maze.getMines()) {
            System.out.println("Mines: - At za.co.wethinkcode.server.position "+ mine.getBottomLeftX()+ "," + mine.getBottomLeftY());
        }
    }

    public Position setRandomSpawn(){
        int x = (int) Math.floor(Math.random() * (maze.getUpperWidth() - (-maze.getUpperWidth() + 1)) + (-maze.getUpperWidth()));
        int y = (int) Math.floor(Math.random() * (maze.getUpperHeight() - (-maze.getUpperHeight() + 1)) + (-maze.getUpperHeight()));
        Set<String> robotNames = Server.getListOfRobots().keySet();

        for (int j = 0; j < 100_000; j++) {

            for (int i = 0; i < getMines().size(); i++) {
                if (getMines().get(i).blocksPosition(new Position(x , y))) {
                    x = (int) Math.floor(Math.random() * (maze.getUpperWidth() - (-maze.getUpperWidth() + 1)) + (-maze.getUpperWidth()));
                    y = (int) Math.floor(Math.random() * (maze.getUpperHeight() - (-maze.getUpperHeight() + 1)) + (-maze.getUpperHeight()));
                    i = 0;
                }
            }

            for (int i = 0; i < getObstacles().size(); i++) {
                if (getObstacles().get(i).blocksPosition(new Position(x,y))) {
                    x = (int) Math.floor(Math.random() * (maze.getUpperWidth() - (-maze.getUpperWidth() + 1)) + (-maze.getUpperWidth()));
                    y = (int) Math.floor(Math.random() * (maze.getUpperHeight() - (-maze.getUpperHeight() + 1)) + (-maze.getUpperHeight()));
                    i = 0;
                }
            }

            for (String name : robotNames) {
                if ((Server.getListOfRobots().get(name).getWorld().getPosition().getX() == x) && Server.getListOfRobots().get(name).getWorld().getPosition().getY() == y) {
                    x = (int) Math.floor(Math.random() * (maze.getUpperWidth() - (-maze.getUpperWidth() + 1)) + (-maze.getUpperWidth()));
                    y = (int) Math.floor(Math.random() * (maze.getUpperHeight() - (-maze.getUpperHeight() + 1)) + (-maze.getUpperHeight()));
                }
            }

            for (int i = 0; i < getPits().size(); i++) {
                if (getPits().get(i).blocksPosition(new Position(x,y))) {
                    x = (int) Math.floor(Math.random() * (maze.getUpperWidth() - (-maze.getUpperWidth() + 1)) + (-maze.getUpperWidth()));
                    y = (int) Math.floor(Math.random() * (maze.getUpperHeight() - (-maze.getUpperHeight() + 1)) + (-maze.getUpperHeight()));
                    i = 0;
                }
            }
        }
        return new Position(x,y);
    }
}
