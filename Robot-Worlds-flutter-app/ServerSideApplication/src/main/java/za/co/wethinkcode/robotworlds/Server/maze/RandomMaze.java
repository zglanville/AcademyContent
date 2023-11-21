/**
 * @author Thonifho
 */
package za.co.wethinkcode.robotworlds.Server.maze;

import za.co.wethinkcode.robotworlds.Server.robot.RobotWorldConfiguration;
import za.co.wethinkcode.robotworlds.Server.world.obstacles.SquareObstacle;

import java.util.ArrayList;
import java.util.List;

public class RandomMaze extends AbstractMaze {

    // private int noOfLaunches = 0;
    // List<String> openSpaces = new ArrayList<>();
    // int noOfOriginalSpaces;
    private Integer id;

    /**
     * Class creates a random list of obstacles populates in 200X400 grid.
     *  @returns list with randomly populated obstacles
     */
    public RandomMaze(RobotWorldConfiguration config) {

        // TODO: move some of this into their own functions

        this.config = config;

        this.name = "UnnamedMaze";
        this.size = config.getSize();
        // TODO: support width and height, not just size
        this.width = size;
        this.height = size;

        this.setBounds(width, height);

        this.generateOpenSpaces();
        if (!config.getPlacedObstacle().equalsIgnoreCase("none")) {
            this.addObstacle(config.getPlacedObstacle().split(","));
        }

        this.generateEdges();
        this.renderMaze();
    }

    private void renderMaze() {

        // TODO: Stuff
        
        // int pitCount = (int) ((((height > width ? height : width) * 0.4) * 0.4));
        // int xObjectCount;
        // boolean isPit;
        // int xSign;
        // int x;

        // while (height / 2 > (height / 2 * -1)) {
        //     isPit = false;
        //     xObjectCount = height < width ? (width/5/(height/5))*2
        //             : (height/5/(width/5))*2;

        //     while (xObjectCount-- > 0) {
        //         xSign = (new Random().nextInt(35) % 2 == 0 ? -1 : 1);
        //         x = xSign * new Random().nextInt(width / 2);
        //         if (pitCount > 0) {
        //             isPit = new Random().nextInt(30) % 2 != 0;
        //             if (isPit)
        //                 pitCount--;
        //         }
        //         obstacles.add(new SquareObstacle(x, height, isPit));
                   // fillPosition(x,y);
        //     }
        //     height -= 5;
        // }
    }

    public void addObstacle(String[] ob) {
        if(ob.length == 2) {
            obstacles.add(new SquareObstacle(Integer.parseInt(ob[0]), Integer.parseInt(ob[1])));
            // System.out.println("OB:"+Arrays.toString(ob));
            fillPosition(Integer.parseInt(ob[0]),Integer.parseInt(ob[1]));
            // System.out.println("OPEN SPACES:");
            // for(String obz :openSpaces) {
            //     System.out.println(obz);}
            // System.out.println("Obstacles list after adding placed ob: " + obstacles.toString());
        }
    }

//    public void addLaunch() {
//        this.noOfLaunches++;
//    }

//    public int getNoOfLaunches() {
//        return noOfLaunches;
//    }

    public void generateOpenSpaces() {
        List<String> openSpaces = new ArrayList<>();
        int size = width/2;

        int x = size;
        int y;
        while(x >= -size) {
            y = size;
            while(y >= -size) {
                String openSpace = "["+ x + ", " + y + "]";
                // List<String> openSpace = Arrays.asList(Integer.toString(x),Integer.toString(y));
                openSpaces.add(openSpace);
                y--;
            }
            x--;
        }
        this.openSpaces = openSpaces;
//        this.noOfOriginalSpaces = openSpaces.size();
        // System.out.println("OPEN SPACES:");
        // for(String ob :openSpaces) {
        // System.out.println(ob);}
    }

    public void fillPosition(int x, int y) {
        String position = "["+ x + ", " + y + "]";
        this.openSpaces.remove(position);
        System.out.println("Filling position: " + position);
        System.out.println("Number of open spaces: " +openSpaces.size());
    }

    public void freePosition(int x,int y) {
        String position = "["+ x + ", " + y + "]";
        this.openSpaces.add(position);
        System.out.println("Freeing position: " + position);
        System.out.println("Number of open spaces: " +openSpaces.size());

    }


//    public int getNoOfOriginalSpaces() {
//        return noOfOriginalSpaces;
//    }

    public boolean isPositionOpen(String position) {
        for(String space : openSpaces) {
            if (space.contains(position)) {
                return true;
            }
        }
        return false;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

}
