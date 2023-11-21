package za.co.wethinkcode.toyrobot.world;

import za.co.wethinkcode.toyrobot.Position;
import za.co.wethinkcode.toyrobot.maze.Maze;

public abstract class AbstractWorld implements IWorld{
    public final Position TOP_LEFT = new Position(-100,200);
    public final Position BOTTOM_RIGHT = new Position(100,-200);
//    public final Maze maze;

    public static final Position CENTRE = new Position(0,0);

    private Position position;
    public Direction currentDirection;

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public AbstractWorld(){
//        this.maze = maze;
        this.position = CENTRE;
        this.currentDirection = Direction.UP;
    }

    public Direction getDirection(){
        return this.currentDirection;
    }

    public Position getPPosition(){
        return this.position;
    }

    public void setPosition(Position position){
        this.position = position;
    }

    public UpdateResponse updatePosition(int nrSteps){
        int newX = this.position.getX();
        int newY = this.position.getY();

        if (Direction.UP.equals(this.currentDirection)) {
            newY = newY + nrSteps;
        }

        if (Direction.DOWN.equals(this.currentDirection)) {
            newY = newY - nrSteps;
        }

        if (Direction.RIGHT.equals(this.currentDirection)) {
            newX = newX + nrSteps;
        }

        if (Direction.LEFT.equals(this.currentDirection)) {
            newX = newX - nrSteps;
        }

        Position newPosition = new Position(newX, newY);
        if (getMaze().blocksPath(this.position, newPosition)){
            return UpdateResponse.FAILED_OBSTRUCTED;
        }
        if (newPosition.isIn(TOP_LEFT,BOTTOM_RIGHT)){
            this.position = newPosition;
            return UpdateResponse.SUCCESS;
        }
        return UpdateResponse.FAILED_OUTSIDE_WORLD;
    }

}
