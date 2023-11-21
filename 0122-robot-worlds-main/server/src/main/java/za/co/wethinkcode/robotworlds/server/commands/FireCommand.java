package za.co.wethinkcode.robotworlds.server.commands;

import za.co.wethinkcode.robotworlds.server.Server;
import za.co.wethinkcode.robotworlds.server.position.Position;
import za.co.wethinkcode.robotworlds.server.robot.Robot;

public class FireCommand extends Command{


    /**
     * Constructor for fire command.
     */

    public FireCommand() {
        super("fire");
    }

    @Override
    public boolean execute(Robot target) {
        if (target.getBullets() <= 0) {
            target.setStatus("you are out of bullets, please reload");
        } else {

        int oldXPosition = target.getWorld().getPosition().getX();
        int oldYPosition = target.getWorld().getPosition().getY();
        int newXPosition = target.getWorld().getPosition().getX();
        int newYPosition = target.getWorld().getPosition().getY();

        int fireRange =target.getBulletRange();         // gets the fire  range of the robots


        Position bulletPosition;


        for(int i = 1; i < fireRange; i++){                 // this is going to loop for as long as the fire range. And its going to check each direction it will be facing.
            switch (target.getWorld().getCurrentDirection()){
                case NORTH:
                    newYPosition = oldYPosition + i;        // Then the new position will be where the robot is firing to. It will go forward depending on the fire range and how far the robot is from the old position.
                    break;
                case SOUTH:
                    newYPosition = oldYPosition -i;
                    break;
                case WEST:
                    newXPosition = oldXPosition - i;
                    break;
                case EAST:
                    newXPosition = oldXPosition + i;
                    break;
            }

            bulletPosition = new Position(newXPosition,newYPosition);   //this will get the bullet position with the newX and newY values.


            for(Robot robot: Server.getListOfRobots().values()){
                if(robot.blocksPosition(bulletPosition)){
                    target.setStatus("A robot has been hit!");
                    target.setTotalHealth((fireRange -i) +1);           // this will decrement the health depending on the fire range. And how many bullets shot.(+1 for shield)
                    target.setCurrentBullets(target.getCurrentBullets()-1);
                    return true;
                }
            }

            target.setCurrentBullets(target.getCurrentBullets()-1);
            target.setStatus("You shot into Nothing.");
//            target.setStatus("You have "+ (target.getCurrentBullets()) + " bullets left.");
        }
        }
        return true;
    }
//    use path blocked to check if robot is in path
//    check per bullet, remove health,
//    how to remove health, check mines. when bullet hits
//    check how far the robot is from you.
//    check direction, distance find out how to remove health.(same as mines)
//
//
//     Distance (steps)                           //Number of Shots
//    5                                           //1
//    4                                           //2
//    3                                           //3
//    2                                           //4
//    1                                           //5
}
