package za.co.wethinkcode.robotworlds.server.maze;

import za.co.wethinkcode.robotworlds.server.world.Obstacle;
import za.co.wethinkcode.robotworlds.server.position.Position;
import za.co.wethinkcode.robotworlds.server.configReader.ConfigReader;

import java.util.List;
import java.util.Random;

public class RandomMap extends AbstractMaze{
    private final ConfigReader config;

    private final Random rand = new Random();
    private final int numberOfObs;
    private final int numberOfPits;
    private int obsSize;
    private final int pitSize;
    protected int upperWidth;
    protected int upperHeight;

    /**
     * Constructor for the random za.co.wethinkcode.server.maze.
     * */
    public RandomMap(ConfigReader config) {
        this.config = config;
        this.numberOfObs = Integer.parseInt(config.getMaxObstacles());
        this.numberOfPits = Integer.parseInt(config.getMaxBottomPits());
        this.pitSize = Integer.parseInt(config.getPitsSize());
        this.upperWidth = Integer.parseInt(config.getWidth()) / 2;
        this.upperHeight = Integer.parseInt(config.getHeight()) / 2;
        generateListObs();
        generateListPits();
        generateListMine();
    }

    /**
     * getter for numberOfObs
     * */
    public int getNumberOfObs() {
        return this.numberOfObs;
    }

    public int getNumberOfPits() {
        return this.numberOfPits;
    }

    /**
     * Checks if it overlaps the start (from -5 to 0) as we start at 0,0 and the square is 5 long.
     * @param bottomLeftX: the bottomleft X co-ordinate.
     * @param bottomLeftY: the bottomleft Y co-ordinate.
     * @return: true if overlaps start.
     * */
    public boolean overLappingStart(int bottomLeftX, int bottomLeftY) {
        return (bottomLeftX >= -(Integer.parseInt(config.getObsSize())) && bottomLeftX < 0) && (bottomLeftY >= -(Integer.parseInt(config.getObsSize())) && bottomLeftY < 0);
    }

    /**
     * Checks if it overlaps another obstacle in its positions. checking each of the za.co.wethinkcode.server.position (so that nothing at all overlaps.
     * @param bottomLeftX: the bottomleft X co-ordinate.
     * @param bottomLeftY: the bottomleft Y co-ordinate.
     * @return: true if it overlaps an obstacle.
     * */
    public boolean overLappingObs(int bottomLeftX, int bottomLeftY) {
        for (Obstacle obstacle : this.getObstacles()) {
            obsSize = obstacle.getSize();
            if ((obstacle.blocksPosition(new Position(bottomLeftX, bottomLeftY))) ||
                    (obstacle.blocksPosition(new Position(bottomLeftX + obstacle.getSize(), bottomLeftY))) ||
                    (obstacle.blocksPosition(new Position(bottomLeftX, bottomLeftY + obstacle.getSize()))) ||
                    (obstacle.blocksPosition(new Position(bottomLeftX + obstacle.getSize(), bottomLeftY + obstacle.getSize())))){
                return true;
            }
        }
        return false;
    }

    /**
     * Generate a random integer between bounds of the X or Y,
     * @param upper: highest possibly point of an obstacle;
     * @param lower: lowest possibly point of an obstacle;
     * @param max: highest possible point of the entire side;
     * @return: the integer of the next random int.
     * */
    public int generateRandomNumber(int upper,int lower,int max) {
        return rand.nextInt(upper-(lower)) - max;
    }

    /**
     * Generate a random number of obstacles up to 100,
     * then create a bottom left x and y co-ord for the obstacle,
     * check if that new obstacle is not overlapping the start nor overlapping another obstacle.
     * create the obstacle if it does not overlap
     * */
    public void generateListObs() {
        int counter = 0;
        while (counter != getNumberOfObs()) {
            int bottomLeftX = generateRandomNumber(upperWidth - obsSize,-upperWidth, upperWidth);
            int bottomLeftY = generateRandomNumber(upperHeight - obsSize,-upperHeight,upperHeight);
            if (!overLappingStart(bottomLeftX, bottomLeftY) && !overLappingObs(bottomLeftX, bottomLeftY)) {
                createObstacles(new Position(bottomLeftX, bottomLeftY));
                counter++;
            }
        }
    }

    public void generateListPits() {
        int counter = 0;
        while (counter != getNumberOfPits()) {
            int bottomLeftX = generateRandomNumber(upperWidth - pitSize, -(upperWidth), upperWidth);
            int bottomLeftY = generateRandomNumber(upperHeight - pitSize, -(upperHeight), upperHeight);
            if (!overLappingStart(bottomLeftX, bottomLeftY)) {
                createPits(new Position(bottomLeftX, bottomLeftY));
                counter++;
            }
        }
    }

    public void generateListMine() {
        createMines(new Position(0, 5));
        createMines(new Position(0, 6));
        createMines(new Position(0, 7));
    }

    public int getUpperHeight(){
        return this.upperHeight;
    }

    public int getUpperWidth(){
        return this.upperWidth;
    }

    public List<Obstacle> getPits() {
        return this.pitsList;
    }
    public List<Obstacle> getMines() {
        return this.mineList;
    }
}
