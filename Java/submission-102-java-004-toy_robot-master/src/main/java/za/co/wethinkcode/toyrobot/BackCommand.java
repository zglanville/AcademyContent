package za.co.wethinkcode.toyrobot;

import za.co.wethinkcode.toyrobot.world.IWorld;

public class BackCommand extends Command {
    @Override
    public boolean execute(Robot target) {
        if(!target.replaying) {
            target.replay.add("back " + getArgument());
        }
        int nrSteps = Integer.parseInt(getArgument());
        IWorld.UpdateResponse response = target.getWorld().updatePosition(-nrSteps);
        if (response.equals(IWorld.UpdateResponse.SUCCESS)){
            target.setStatus("Moved back by "+nrSteps+" steps.");
        }else if(response.equals(IWorld.UpdateResponse.FAILED_OUTSIDE_WORLD)){
            target.setStatus("Sorry, I cannot go outside my safe zone.");
        }else{
            target.setStatus("Sorry, there is an obstacle in the way.");
        }
        return true;
    }

    public BackCommand(String argument) {
        super("back", argument);
    }
}