package za.co.wethinkcode.toyrobot;

import java.util.Arrays;
import java.util.List;

public class Robot {
    public static final Position CENTRE = new Position(0,0);
    private static final List<String> VALID_COMMANDS = Arrays.asList("off", "help", "forward");

    private static Position TOP_LEFT = new Position (-100,200);
    private static Position BOTTOM_RIGHT = new Position(100, -200);

    private Direction currentDirection;
    private Position position;
    private String status;
    private String name;

    public Robot(String name) {
        this.name = name;
        this.status = "Ready";
        this.position = CENTRE;
        this.currentDirection = Direction.NORTH;
    }

    public Position getPosition() { return this.position; }

    public String getStatus() {                                                                         //<5>
        return this.status;
    }

    public Direction getCurrentDirection() {                                                               //<8>
        return this.currentDirection;
    }

    public boolean handleCommand(Command command){
        return command.execute(this);
    }

    public boolean updatePosition(int nrSteps){
        int newY = this.position.getY();
        int newX = this.position.getX();

        if (Direction.NORTH.equals(this.currentDirection)){
            newY = newY + nrSteps;
        }

        Position newPosition = new Position(newX, newY);
        if (newPosition.isIn(TOP_LEFT,BOTTOM_RIGHT)){
            this.position = newPosition;
            return true;
        }
        return false;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "[" + this.position.getX() + "," + this.position.getY() + "] "
                + this.name + "> " + this.status;
    }
}
