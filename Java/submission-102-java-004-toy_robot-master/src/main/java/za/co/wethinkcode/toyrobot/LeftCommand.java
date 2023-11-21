package za.co.wethinkcode.toyrobot;

public class LeftCommand extends Command {
    @Override
    public boolean execute(Robot target) {
        if(!target.replaying) {
            target.replay.add("left");
        }
        target.getWorld().updateDirection(false);
        target.setStatus("Turned left.");
        return true;
    }

    public LeftCommand() {
        super("left");
    }
}