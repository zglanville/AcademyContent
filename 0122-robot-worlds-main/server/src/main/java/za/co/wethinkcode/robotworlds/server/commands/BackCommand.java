package za.co.wethinkcode.robotworlds.server.commands;


import za.co.wethinkcode.robotworlds.server.robot.Robot;
import za.co.wethinkcode.robotworlds.server.world.IWorld;
import za.co.wethinkcode.robotworlds.server.world.AbstractWorld;

public class BackCommand extends Command {
    @Override
    public boolean execute(Robot target) {
        int nrSteps = Integer.parseInt(getArgument());
        IWorld.UpdateResponse response = target.getWorld().updatePosition(-nrSteps);
        if (response == IWorld.UpdateResponse.SUCCESS){
            target.setStatus("Moved back by "+nrSteps+" steps.");
        } else if (response == AbstractWorld.UpdateResponse.FAILED_OBSTRUCTED){
            target.setStatus("Sorry, Obstacle in my way.");
        } else if (response == IWorld.UpdateResponse.BOTTOMLESS_PIT) {
            target.setStatus("boom, you died! LOL NOOB GG REKT");
            return false;
        } else if (response == IWorld.UpdateResponse.MINE) {
            target.setStatus("Ouch you stepped on a mine!");
            target.setCurrentHeath(target.getCurrentHealth() - 3);
        } else {
            target.setStatus("Sorry, I cannot go outside my safe zone.");
        }
        return true;
    }

    public BackCommand(String argument) {
        super("Back", argument);
    }
}
