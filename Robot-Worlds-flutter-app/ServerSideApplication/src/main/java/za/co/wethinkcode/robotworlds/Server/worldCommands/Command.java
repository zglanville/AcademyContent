/**
 * @author Thonifho, Tshepo
 */
package za.co.wethinkcode.robotworlds.Server.worldCommands;

import za.co.wethinkcode.robotworlds.Server.robot.Robot;
import za.co.wethinkcode.robotworlds.Server.worldCommands.actions.*;
import za.co.wethinkcode.robotworlds.Server.worldCommands.movements.BackCommand;
import za.co.wethinkcode.robotworlds.Server.worldCommands.movements.ForwardCommand;
import za.co.wethinkcode.robotworlds.Server.worldCommands.movements.LeftCommand;
import za.co.wethinkcode.robotworlds.Server.worldCommands.movements.RightCommand;

/**
 * Abstract class to handle all the robot commands
 * @author Thoni
 * @author Issa
 * @author Tshepo
 */
public abstract class Command {
    private final String name;
    private final String arguments;
    protected String message;

    public Command(String name, String arguments) {
        this.name = name.toLowerCase().trim();
        this.arguments = arguments.trim();
        this.message = "Done";
    }

    public Command(String name){
        this.name = name.toLowerCase().trim();
        this.arguments = "";
        this.message = "Done";
    }

    public String getName() {
        return this.name;
    }

    public String getArgument() {
        return this.arguments;
    }

    public String getMessage() {
        return this.message;
    }

    public abstract boolean execute(Robot target);

    public static Command create(String instruction) {
        String[] args = instruction.toLowerCase().trim().split(" ");
        switch (args[0]){
            case "shutdown":
                return new ShutdownCommand();
            case "help":
                return new HelpCommand();
            case "forward":
                return new ForwardCommand(args[1]);
            case "back":
                return new BackCommand(args[1]);
            case "turn":
                switch (args[1]){
                    case "left":
                        return new LeftCommand();
                    case "right":
                        return new RightCommand();
                }
            case "fire":
                return new FireCommand();
            case "look":
                return new LookCommand();
            case "state":
                return new StateCommand();
            case "mine":
                return new MineCommand();
            case "reload":
                return new ReloadCommand();
            case "repair":
                return new RepairCommand();
            default:
                throw new IllegalArgumentException("Unsupported command: " + instruction);
        }
    }
}