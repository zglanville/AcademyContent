package za.co.wethinkcode.robotworlds.server.commands;

import za.co.wethinkcode.robotworlds.server.robot.Robot;

public abstract class Command {
    private final String name;
    private String argument;

    /**
     * Abstract command to execute the za.co.wethinkcode.server.commands; (will be overridden).
     * @param target: Takes the za.co.wethinkcode.server.robot.Robot target as a parameter.
     * */
    public abstract boolean execute(Robot target);

    /**
     * Constructor for Commands (with 0 arguments).
     * */
    public Command(String name) {
        this.name = name.trim().toLowerCase();
        this.argument = "";
    }

    /**
     * Constructor for Commands (with arguments).
     * */
    public Command(String name, String argument) {
        this(name);
        this.argument = argument;
    }

    /**
     * Getter to get the private name variable.
     * */
    public String getName() {
        return this.name;
    }

    /**
     * Getter to get the private argument variable.
     * */
    public String getArgument() {
        return this.argument;
    }

    public static Command create(String instruction) {
        String[] args = instruction.toLowerCase().trim().split(" ");
        if (args.length == 1) {
            switch (args[0]) {
                case "shutdown":
                    return new ShutdownCommand();
                case "help":
                    return new HelpCommand();
                case "orientation":
                    return new OrientationCommand();
                case "health":
                    return new HealthCommand();
                case "repair":
                    return new RepairCommand();
                case "fire":
                    return new FireCommand();
                case "reload":
                    return new ReloadCommand();
                case "look":
                    return new LookCommand();
                default:
                    throw new IllegalArgumentException("Unsupported command: " + instruction);
            }
        } else {
            switch (args[0]) {
                case "forward":
                    return new ForwardCommand(args[1]);
                case "back":
                    return new BackCommand(args[1]);
                case "turn":
                    return new TurnCommand(args[1]);
                default:
                    throw new IllegalArgumentException("Unsupported command: " + instruction);
            }
        }
    }


}
