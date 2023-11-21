package za.co.wethinkcode.toyrobot.world;

import za.co.wethinkcode.toyrobot.Position;

public class SquareObstacle implements Obstacle{

    public int x;
    public int y;

    public SquareObstacle(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public int getBottomLeftX() {
        return x;
    }

    @Override
    public int getBottomLeftY() {
        return y;
    }

    @Override
    public int getSize() {
        return 5;
    }

    @Override
    public boolean blocksPosition(Position position) {
        if (position.getX() >= this.x && position.getX() <= this.x+4){
            if (position.getY() >= this.y && position.getY() <= this.y+4){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean blocksPath(Position a, Position b) {
        if (a.getY() == b.getY()){
            if(a.getX() > b.getX()){
                for (int i = b.getX(); i <= a.getX(); i++) {
                    if (blocksPosition(new Position(i, a.getY()))){
                        return true;
                    }
                }
            }
            if(b.getX() > a.getX()){
                for (int i = a.getX(); i <= b.getX(); i++) {
                    if (blocksPosition(new Position(i, a.getY()))){
                        return true;
                    }
                }
            }
        }
        if (a.getX() == b.getX()){
            if (a.getY() > b.getY()){
                for (int i = b.getY(); i <= a.getY(); i++) {
                    if (blocksPosition(new Position(a.getX(), i))){
                        return true;
                    }
                }
            }
            if (b.getY() > a.getY()) {
                for (int i = a.getY(); i <= b.getY(); i++) {
                    if (blocksPosition(new Position(a.getX(), i))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
