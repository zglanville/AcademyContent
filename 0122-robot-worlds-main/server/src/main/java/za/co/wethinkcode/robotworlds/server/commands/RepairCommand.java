package za.co.wethinkcode.robotworlds.server.commands;

import za.co.wethinkcode.robotworlds.server.robot.Robot;

public class RepairCommand extends Command{
    public RepairCommand() {
        super("repair");
    }

    @Override
    public boolean execute(Robot target) {
        if(target.getCurrentHealth() == target.getTotalHealth()){
            target.setStatus("Sorry your za.co.wethinkcode.server.robot is already at its maximum health.");
        }else{
            int previousHealth = target.getCurrentHealth();
            target.setCurrentHeath(target.getTotalHealth());
            target.setStatus("Your za.co.wethinkcode.server.robot has been repaired by "+ (target.getTotalHealth() - previousHealth) +" shields.");
        }
        return true;
    }
}
