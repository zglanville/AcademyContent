package za.co.wethinkcode.toyrobot;

import za.co.wethinkcode.toyrobot.world.IWorld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Robot {
    private final Position TOP_LEFT = new Position(-200,100);
    private final Position BOTTOM_RIGHT = new Position(100,-200);
    public boolean yes = false;

    public static final Position CENTRE = new Position(0,0);

    private Position position;
    private Direction currentDirection;
    private String status;
    private String name;
    public ArrayList<String> replay = new ArrayList<>();
    public boolean replaying;

    private IWorld world;

    public Robot(String name) {
        this.name = name;
        this.status = "Ready";
        this.position = CENTRE;
        this.currentDirection = Direction.NORTH;
    }

    public ArrayList<String> getReplayList() {return replay;}

    public String getStatus() {
        return this.status;
    }

    public Direction getCurrentDirection() {
        return this.currentDirection;
    }

    public void setWorld(IWorld world) {
        this.world = world;
    }

    public IWorld getWorld() {return world;}

    public boolean handleCommand(Command command) {
        return command.execute(this);
    }

    @Override
    public String toString() {
       return "[" + this.getWorld().getPosition().getX() + "," + this.getWorld().getPosition().getY() + "] "
               + this.name + "> " + this.status;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }
}