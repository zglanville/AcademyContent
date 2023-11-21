package za.co.wethinkcode.robotworlds.Server.worldCommands.actions;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import za.co.wethinkcode.robotworlds.Server.ServerMain;
import za.co.wethinkcode.robotworlds.Server.robot.Robot;
import za.co.wethinkcode.robotworlds.Server.robot.RobotHandler;
import za.co.wethinkcode.robotworlds.Server.world.Direction;
import za.co.wethinkcode.robotworlds.Server.world.Position;
import za.co.wethinkcode.robotworlds.Server.worldCommands.Command;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Handles the logic for the fire command. Extends the command class.
 * Action: Issa
 */
public class FireCommand extends Command {
    public FireCommand(){super("fire");}


    /**
     * Executes the method to check whether the robot has shot an opponent
     * @param target robot which has shot.
     * @return true if the method was successfully executed
     */
    @Override
    public boolean execute(Robot target) {

        if (target.getShots()<1){return true;}
        target.setShots("shot");
        Direction shooterDirection = target.getCurrentDirection();
        JSONObject shotData = new JSONObject();
        JSONObject opponentStatus = new JSONObject();

        boolean shot = false;

        for (RobotHandler robotHandler: ServerMain.robots){
            String robotName = robotHandler.getRobot().getName();

            if (ServerMain.robots.size()>1 && !target.getName().equalsIgnoreCase(robotName)){
                Position targetPosition = target.getPosition();
                Position opponentPosition = robotHandler.getRobot().getPosition();
                shot = checkShotHit(opponentPosition,shooterDirection,targetPosition);
                if (shot){
                    JSONArray position = new JSONArray(); //create a json array for the position
                    position.add(opponentPosition.getX());
                    position.add(opponentPosition.getY());

                    robotHandler.getRobot().setShields("shot");//decrease the opponents shield since they have been hit
                    robotHandler.getRobot().setRobotStatus("shot");
                    opponentStatus.put("position", position);
                    opponentStatus.put("direction", robotHandler.getRobot().getCurrentDirection().toString());
                    opponentStatus.put("shields", robotHandler.getRobot().getShields());
                    opponentStatus.put("shots", robotHandler.getRobot().getShots());
                    opponentStatus.put("status", robotHandler.getRobot().getRobotStatus().toString());
                    shotData.put("message", "Hit");
                    shotData.put("robot", robotName);
                    shotData.put("state", opponentStatus);
                    break;
                }
            }
        }

        if (!shot){
            shotData.put("message", "Miss");
        }

        File file = new File("").getAbsoluteFile();
        String jsonFilePath = "/ServerSideApplication/src/main/java/za/co/wethinkcode/robotworlds/Server/worldCommands/actions/" +
                "FireHitOrMiss.json";

        try(FileWriter fileWriter = new FileWriter(file+jsonFilePath)){
            fileWriter.write(shotData.toJSONString());
        }catch (IOException e){
            System.out.println("Could not write to HitOrMiss "+ e);
        }

        return true;
    }

    /**
     * Checks whether or not the shot has hit an opponent.
     * @param opponentPosition
     * @param shooterDirection
     * @param shooterPosition
     * @return true if shot hit.
     */
    public boolean checkShotHit(Position opponentPosition, Direction shooterDirection, Position shooterPosition){
        int opponentX, opponentY, shooterX, shooterY;
        boolean xInLine, yInLine;
        opponentX = opponentPosition.getX();
        opponentY = opponentPosition.getX();
        shooterX = shooterPosition.getX();
        shooterY = shooterPosition.getY();

        xInLine = opponentX==shooterX;
        yInLine = opponentY==shooterY;

        if (shooterDirection.equals(Direction.NORTH)){
            if (xInLine && shooterY <= opponentY && shooterY+4 >= opponentY){ return true; }
        }
        else if (shooterDirection.equals(Direction.EAST)){
            if (yInLine && shooterX <= opponentX && shooterX+4 >= opponentX){ return true; }
        }
        else if (shooterDirection.equals(Direction.SOUTH)){
            if (xInLine && shooterY >= opponentY && shooterY-4 >= opponentY){ return true; }
        }
        else if (shooterDirection.equals(Direction.WEST)){
            if (yInLine && shooterX >= opponentX && shooterX-4 >= opponentX){ return true; }
        }
        return false;
    }
}

/**
 * State format
 * "message": "Hit",
 *     "distance": steps,
 *     "robot": name
 *     "state": { ... }
 *   },
 * state: {
 *     "position": [x,y],
 *     "direction": "direction that the robot is facing",
 *     "shields": number of hits that can be absorbed,
 *     "shots": number of shots left,
 *     "status": "operational status that the robot is in"
 *    }
 */

