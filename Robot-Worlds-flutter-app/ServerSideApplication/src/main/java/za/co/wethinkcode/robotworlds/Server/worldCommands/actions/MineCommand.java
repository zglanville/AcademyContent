package za.co.wethinkcode.robotworlds.Server.worldCommands.actions;

import za.co.wethinkcode.robotworlds.Server.maze.AbstractMaze;
import za.co.wethinkcode.robotworlds.Server.robot.Robot;
import za.co.wethinkcode.robotworlds.Server.world.Position;
import za.co.wethinkcode.robotworlds.Server.world.obstacles.SquareMine;
import za.co.wethinkcode.robotworlds.Server.worldCommands.Command;

/**
 * Implements the deployment of mines command by the robots
 * Action: Issa
 */
public class MineCommand extends Command {
    public MineCommand(){super("mine");}

    /**
     * Method sets the thread to sleep for the configured amount of time
     * and deploys a mine by creating a new obstacle and adding it to the
     * mines list.
     * @param target
     * @return
     */
    @Override
    public boolean execute(Robot target) {
        target.setRobotStatus("mine");
        try {
            Thread.sleep(1000*target.getMine());
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        Position minePosition = target.getPosition();
        SquareMine mine = new SquareMine(minePosition.getX(), minePosition.getY());
        target.getWorld().addMine(mine);
        return true;
    }
}