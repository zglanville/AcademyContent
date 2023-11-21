package za.co.wethinkcode.robotworlds.server;

import za.co.wethinkcode.robotworlds.server.robot.Robot;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

    private static ConcurrentHashMap<String, Robot> listOfRobots = new ConcurrentHashMap<>();

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        Scanner scanner = new Scanner(System.in);

        //create thread that listens for connections
        Runnable r = new ServerListener();
        Thread task = new Thread(r);
        task.start();
        String input;

        while (true) {
            if (scanner.hasNext()) {
                input = scanner.next();
                if (isValidWorldCommand(input)) {
                    while (!isValidWorldCommand(input)) {
                        System.out.println("Please enter valid input ");
                        input = scanner.next();
                    }
                    if (input.equalsIgnoreCase("quit")) {
                        System.exit(1);
                    } else if (input.equalsIgnoreCase("dump")) {
                        //get robots status from JSON files
                        System.out.println("Worlds state, all robots, obstacles pits from JSON file");
                    } else if (input.equalsIgnoreCase("robots")) {
                        //also from JSON file
                        System.out.println(listOfRobots);
                    } else if (input.equalsIgnoreCase("purge")) {
                        System.out.println("robot has been removed from list");
                    }
                }
            }
        }
    }

    private static boolean isValidWorldCommand(String input){
        //method that gets input, checks is user input is valid in list of commands and returns input.
        if (input.equalsIgnoreCase("quit") || input.equalsIgnoreCase("purge")
                || input.equalsIgnoreCase("robots") || input.equalsIgnoreCase("dump")) {
            System.out.println("true");
            return true;
        }
        System.out.println("false");
        return false;
    }

    public static void addRobots(String name, Robot robot) {
        listOfRobots.put(name, robot);
    }

    public static ConcurrentHashMap<String, Robot> getListOfRobots() {
        return listOfRobots;
    }

    public static void removeRobot(String name, Robot robot) {
        listOfRobots.remove(name, robot);
        System.out.println(name + " has been removed.");
        System.out.println(listOfRobots);
    }
}
