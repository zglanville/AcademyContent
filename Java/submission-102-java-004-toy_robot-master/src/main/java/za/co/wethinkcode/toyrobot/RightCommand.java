package za.co.wethinkcode.toyrobot;

public class RightCommand extends Command {
    @Override
    public boolean execute(Robot target) {
        if(!target.replaying) {
            target.replay.add("right");
        }
        target.getWorld().updateDirection(true);
        target.setStatus("Turned right.");
        return true;
    }

    public RightCommand() {
        super("right");
    }
}