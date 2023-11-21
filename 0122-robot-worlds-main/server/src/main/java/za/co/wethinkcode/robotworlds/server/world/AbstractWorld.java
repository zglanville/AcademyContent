package za.co.wethinkcode.robotworlds.server.world;

import za.co.wethinkcode.robotworlds.server.configReader.ConfigReader;
import za.co.wethinkcode.robotworlds.server.position.Position;
import za.co.wethinkcode.robotworlds.server.maze.Maze;

public abstract class AbstractWorld implements IWorld {
    public abstract void updateDirection(boolean turnRight);

    protected Position TOP_LEFT;
    protected Position BOTTOM_RIGHT;

    protected static Position CENTRE = new Position(0, 0);

    protected Position position;
    protected Direction currentDirection;
    protected Maze maze;
    protected ConfigReader config;

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    public Maze getMaze(){
        return maze;
    }

    /**
     * This function checks if the za.co.wethinkcode.server.position is allowed.
     * @param position: takes in the za.co.wethinkcode.server.position.
     * @return: boolean true if allowed, false if not allwoed.
     * */
    public boolean isNewPositionAllowed(Position position) {
        return position.isIn(TOP_LEFT, BOTTOM_RIGHT);
    }

    /**
     * boolean to check if you are at the edge of the worlds boundaries.
     * */
    public boolean isAtEdge() {
        int yBoundary = Integer.parseInt(config.getHeight()) / 2;
        int xBoundary = Integer.parseInt(config.getWidth()) / 2;

        return (this.position.getX() == xBoundary || this.position.getX() == -(xBoundary))
                || (this.position.getY() == yBoundary || this.position.getY() == -(yBoundary));
    }

    /**
     * Getter to get the current za.co.wethinkcode.server.position.
     * */
    public Position getPosition() {
        return this.position;
    }

    /**
     * getter to get the current direction.
     * */
    public Direction getCurrentDirection() {
        return this.currentDirection;
    }

    /**
     * setter for the za.co.wethinkcode.server.position
     * */
    public void setPosition(Position position){
        this.position = position;
    }

    public Position setWorldBoundary(String corner) {
        int height = Integer.parseInt(config.getHeight());
        int width = Integer.parseInt(config.getWidth());

        int x = width / 2;
        int y = height / 2;

        if (corner.equalsIgnoreCase("topLeft")) {
            x *= -1;
        } else if (corner.equalsIgnoreCase("bottomRight")) {
            y *= -1;
        }

        return new Position(x, y);
    }

}