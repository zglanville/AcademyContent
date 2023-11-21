package za.co.wethinkcode.robotworlds.server.commands;

import za.co.wethinkcode.robotworlds.server.robot.Robot;
import za.co.wethinkcode.robotworlds.server.world.IWorld;

public class ForwardCommand extends Command {
    @Override
    public boolean execute(Robot target) {
        int nrSteps = Integer.parseInt(getArgument());
        IWorld.UpdateResponse response = target.getWorld().updatePosition(nrSteps);
        if (response == IWorld.UpdateResponse.SUCCESS){
            target.setStatus("Moved forward by "+nrSteps+" steps.");
        } else if (response == IWorld.UpdateResponse.FAILED_OBSTRUCTED){
            target.setStatus("Sorry, Obstacle in my way.");
        } else if (response == IWorld.UpdateResponse.BOTTOMLESS_PIT) {
            target.setStatus("boom, you died! LOL NOOB GG REKT");
            return false;
        } else if (response == IWorld.UpdateResponse.MINE) {
            target.setCurrentHeath(target.getCurrentHealth() - 3);
//            for (Obstacle mine : target.getWorld().getMines()) {
//                if ((target.getWorld().getPosition().getX() == mine.getBottomLeftX()) && (target.getWorld().getPosition().getY() == mine.getBottomLeftY())){
//                    System.out.println("ON A MINE");
//                }
//            }
            if (target.getCurrentHealth() >= 1) {
                target.setStatus("Ouch you stepped on a mine!");
            } else {
                target.setStatus("You have died. :(!");
            }
        } else {
            target.setStatus("Sorry, I cannot go outside my safe zone.");
        }
        return true;
    }

    public ForwardCommand(String argument) {
        super("forward", argument);
    }
}
