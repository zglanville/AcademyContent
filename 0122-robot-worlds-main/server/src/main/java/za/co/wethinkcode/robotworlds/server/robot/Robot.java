package za.co.wethinkcode.robotworlds.server.robot;

import za.co.wethinkcode.robotworlds.server.commands.Command;
import za.co.wethinkcode.robotworlds.server.world.IWorld;
import za.co.wethinkcode.robotworlds.server.configReader.ConfigReader;
import za.co.wethinkcode.robotworlds.server.position.Position;

import java.util.Random;

public class Robot {
    protected final Random rand = new Random();
    protected IWorld.Direction currentDirection;
    protected String status;
    protected String name;
    protected IWorld world;
    protected ConfigReader config;
    protected Position position;
    protected String make;

    protected int bottomLeftX;
    protected int bottomLeftY;
    protected int size;
    protected int y;
    protected int x;

    protected int currentHealth;
    protected int vision;
    protected int bulletRange;
    protected int totalHealth;
    protected int bullets;
    protected int currentBullets;

    public Robot(String name) {
        this.name = name;
        this.bottomLeftX = x;
        this.bottomLeftY = y;
//        this.status = "Ready";
//        this.currentDirection = NORTH;
    }

    public int getCurrentBullets() {
        return currentBullets;
    }

    public void setCurrentBullets(int currentBullets) {
         this.currentBullets = currentBullets;
    }

    public void setCurrentHeath(int newHealth) {
        this.currentHealth = newHealth;
    }

    public int getBullets() {
        return bullets;
    }

    public void setTotalHealth(int totalHealth) {
        this.totalHealth = totalHealth;
    }

    public int getTotalHealth() {
        return totalHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getBulletRange() {
        return bulletRange;
    }

    public void setWorld(IWorld world) {
        this.world = world;
    }

    public IWorld getWorld() {return world;}

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean handleCommand(Command command) {
        return command.execute(this);
    }

    @Override
    public String toString() {
        return "[" + this.getWorld().getPosition().getX() + "," + this.getWorld().getPosition().getY() + "] "
                + this.name + "> " + this.status;
    }

    public IWorld.Direction getCurrentDirection(){
        return this.currentDirection;
    }

    public void setCurrentDirection(IWorld.Direction direction){
        this.currentDirection = direction;
    }

    private int generateRandomNumber(int upper,int lower,int max) {
        return rand.nextInt(upper-(lower)) - max;
    }

    public String getMake() {
        return this.make;
    }

    public int getVision () {
        return this.vision;
    }

    public boolean blocksPosition(Position position) {
        return (position.getX() >= bottomLeftX && position.getX() <= (bottomLeftX + size)) && (position.getY() >= bottomLeftY && position.getY() <= (bottomLeftY + size));
    }

    public boolean blocksPath(Position a, Position b) {
        int startX;
        int endX;
        int startY;
        int endY;

        if (a.getX() == b.getX()) {
            startY = Math.min(a.getY(), b.getY()); //get min between 2 y's
            endY = Math.max(a.getY(), b.getY()); //get max between 2 y's
            for (int y = startY; y <= endY; y++) { //checks from min to max y's if za.co.wethinkcode.server.position is blocked
                if (blocksPosition(new Position(a.getX(), y)))
                    return true;
            }
        } else {
            startX = Math.min(a.getX(), b.getX());
            endX = Math.max(a.getX(), b.getX());
            for (int x = startX; x <= endX; x++) {
                if (blocksPosition(new Position(x, a.getY()))) return true;
            }
        }
        return false;
    }
}
