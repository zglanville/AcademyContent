package za.co.wethinkcode.robotworlds.Server.Web;

import za.co.wethinkcode.robotworlds.Server.robot.Robot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RobotsData {
    List<Robot> robots = new ArrayList<>();

    public List<Robot> getRobots() {return robots;}

    public void addRobot(Robot robot){robots.add(robot);}

    public String deleteRobotData(String robotName){
        for(Robot r : robots){
            if(Objects.equals(r.getName(), robotName)){
                r.setRobotStatus("dead");
                robots.remove(r);
                return "OK";
            }
        }
        return "ROBOT DOES NOT EXIST: " + robotName;
    }

    public Robot getRobot(String robotName) {
        for (Robot robot : robots) {
            if (robot.getName().equals(robotName))
                return robot;
        }
        return null;
    }

}
