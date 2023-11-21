package za.co.wethinkcode.robotworlds.server.commands;

import za.co.wethinkcode.robotworlds.server.Server;
import za.co.wethinkcode.robotworlds.server.robot.Robot;
import za.co.wethinkcode.robotworlds.server.world.Obstacle;
import za.co.wethinkcode.robotworlds.server.position.Position;
import za.co.wethinkcode.robotworlds.server.configReader.ConfigReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LookCommand extends Command{
    private ConfigReader config;
    private final ArrayList<String> northList;
    private final ArrayList<String> eastList;
    private final ArrayList<String> southList;
    private final ArrayList<String> westList;

    public LookCommand() {
        super("look");
        northList = new ArrayList<>();
        eastList = new ArrayList<>();
        southList = new ArrayList<>();
        westList = new ArrayList<>();
        config = new ConfigReader();
    }

    private void checkingHinderances(List<Obstacle> iterator, Robot target, String type) {
        float possibleVision = target.getVision();
        if (type.equals("mines")) {
            possibleVision = (int) Math.floor(possibleVision / 4);
        }
        int vision = (int) possibleVision;


        for (Obstacle hindrance : iterator) {
            if (hindrance.blocksPath(new Position(target.getWorld().getPosition().getX(), target.getWorld().getPosition().getY()), new Position(target.getWorld().getPosition().getX(), target.getWorld().getPosition().getY() + vision))) {
                northList.add(hindrance.getBottomLeftX() + " " + hindrance.getBottomLeftY());
            } else if (hindrance.blocksPath(new Position(target.getWorld().getPosition().getX(), target.getWorld().getPosition().getY()), new Position(target.getWorld().getPosition().getX() + vision, target.getWorld().getPosition().getY()))) {
                eastList.add(hindrance.getBottomLeftX() + " " + hindrance.getBottomLeftY());
            } else if (hindrance.blocksPath(new Position(target.getWorld().getPosition().getX(), target.getWorld().getPosition().getY()), new Position(target.getWorld().getPosition().getX(), target.getWorld().getPosition().getY() - target.getVision()))) {
                southList.add(hindrance.getBottomLeftX() + " " + hindrance.getBottomLeftY());
            } else if (hindrance.blocksPath(new Position(target.getWorld().getPosition().getX(), target.getWorld().getPosition().getY()), new Position(target.getWorld().getPosition().getX() - target.getVision(), target.getWorld().getPosition().getY()))) {
                westList.add(hindrance.getBottomLeftX() + " " + hindrance.getBottomLeftY());
            }
        }
    }

    @Override
    public boolean execute(Robot target) {
        Set<String> robotNames = Server.getListOfRobots().keySet();
        System.out.println(target.getVision());

        checkingHinderances(target.getWorld().getPits(), target, "pits");
        checkingHinderances(target.getWorld().getMines(), target, "mines");
        checkingHinderances(target.getWorld().getObstacles(), target, "obstacles");

        if ((target.getWorld().getPosition().getY() + target.getVision()) >= (Integer.parseInt(config.getHeight()) / 2)) {
            northList.add("North boundary");
        }
        if ((target.getWorld().getPosition().getX() + target.getVision()) >= (Integer.parseInt(config.getWidth()) / 2)) {
            eastList.add("East boundary");
        }
        if ((target.getWorld().getPosition().getY() - target.getVision()) <= -(Integer.parseInt(config.getHeight()) / 2)) {
            southList.add("South boundary");
        }
        if ((target.getWorld().getPosition().getX() - target.getVision()) <= -(Integer.parseInt(config.getWidth()) / 2)) {
            westList.add("West boundary");
        }



        for (String bob: northList) {
            System.out.println("north: " + bob);
        }
        for (String bob: eastList) {
            System.out.println("east: " + bob);
        }
        for (String bob: southList) {
            System.out.println("south: " + bob);
        }
        for (String bob: westList) {
            System.out.println("west: " + bob);
        }

        return true;
    }
}
