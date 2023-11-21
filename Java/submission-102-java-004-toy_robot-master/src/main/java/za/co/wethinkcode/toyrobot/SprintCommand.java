package za.co.wethinkcode.toyrobot;

import za.co.wethinkcode.toyrobot.world.IWorld;

public class SprintCommand extends Command {
    @Override
    public boolean execute(Robot target) {
        if(!target.replaying) {
            target.replay.add("sprint" + getArgument());
        }
        int nrSteps = Integer.parseInt(getArgument());
        for (int i = nrSteps; i > 0; i--) {
            IWorld.UpdateResponse response = target.getWorld().updatePosition(i);
            if (response.equals(IWorld.UpdateResponse.SUCCESS)){
                target.setStatus("Moved forward by "+i+" steps.");
            }else if(response.equals(IWorld.UpdateResponse.FAILED_OUTSIDE_WORLD)){
                target.setStatus("Sorry, I cannot go outside my safe zone.");
                break;
            }else{
                target.setStatus("Sorry, there is an obstacle in the way.");
                break;
            }
            target.setStatus("Moved forward by " +i+ " steps.");
            if (i > 1) {
                Play.printWords(target.toString());
            }
        }
        return true;
    }

    public SprintCommand(String argument) {
        super("sprint", argument);
    }
}