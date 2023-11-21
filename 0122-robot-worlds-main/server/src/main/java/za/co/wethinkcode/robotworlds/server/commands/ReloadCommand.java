package za.co.wethinkcode.robotworlds.server.commands;

import za.co.wethinkcode.robotworlds.server.robot.Robot;

public class ReloadCommand extends Command{

    public ReloadCommand() {
        super("reload");
    }

    @Override
    public boolean execute(Robot target) {
        if(target.getCurrentBullets() == target.getBullets()){
            target.setStatus("Sorry your robot is already is already fully loaded.");
        }else{
            int previous = target.getCurrentBullets();
            target.setCurrentBullets(target.getBullets());
            target.setStatus("Your have reloaded "+ (target.getBullets() - previous) + " bullets.");
        }
        return true;
    }
}
