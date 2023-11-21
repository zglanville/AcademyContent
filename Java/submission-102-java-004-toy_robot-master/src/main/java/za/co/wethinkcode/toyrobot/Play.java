package za.co.wethinkcode.toyrobot;

import za.co.wethinkcode.toyrobot.maze.Maze;
import za.co.wethinkcode.toyrobot.maze.RandomMaze;
import za.co.wethinkcode.toyrobot.maze.SimpleMaze;
import za.co.wethinkcode.toyrobot.world.IWorld;
import za.co.wethinkcode.toyrobot.world.TextWorld;
import za.co.wethinkcode.toyrobot.world.TurtleWorld;

import java.sql.SQLOutput;
import java.util.Scanner;

public class Play {
    static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        Robot robot;

        String name = getInput("What do you want to name your robot?");
        robot = new Robot(name);
        Maze maze =  new SimpleMaze();
        IWorld world = new TextWorld(maze);
        if (args.length == 0){
            world = new TextWorld(maze);
        }
        else if (args[0].equalsIgnoreCase("text")){
            world = new TextWorld(maze);
        }
//        else if (args[0].equalsIgnoreCase("turtle")){
//            world = new TurtleWorld(maze);
//        }
        robot.setWorld(world);
        world.showObstacles();

        System.out.println("Hello Kiddo!");

//        System.out.println(robot.toString());

        Command command;
        boolean shouldContinue = true;
        do {
            String instruction = getInput(robot.getName() + "> What must I do next?").strip().toLowerCase();
            try {
                command = Command.create(instruction);
                shouldContinue = robot.handleCommand(command);
            } catch (IllegalArgumentException e) {
                robot.setStatus("Sorry, I did not understand '" + instruction + "'.");
            }
            System.out.println(robot);
        } while (shouldContinue);

    }

    private static String getInput(String prompt) {
        System.out.println(prompt);
        String input = scanner.nextLine();

        while (input.isBlank()) {
            System.out.println(prompt);
            input = scanner.nextLine();
        }
        return input;
    }

    public static void printWords(String words){
        System.out.println(words);
    }
}