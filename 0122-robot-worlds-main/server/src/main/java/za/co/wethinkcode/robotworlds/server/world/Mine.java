package za.co.wethinkcode.robotworlds.server.world;

import za.co.wethinkcode.robotworlds.server.configReader.ConfigReader;
import za.co.wethinkcode.robotworlds.server.position.Position;

public class Mine implements Obstacle{

    private final int bottomLeftX;
    private final int bottomLeftY;
    private int size;

    public Mine(int x, int y){
        this.bottomLeftX = x;
        this.bottomLeftY = y;
        ConfigReader config = new ConfigReader();
        this.size = Integer.parseInt(config.getMineSize());
    }

    public int getBottomLeftX() {
        return this.bottomLeftX;
    }

    public int getBottomLeftY() {
        return this.bottomLeftY;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
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