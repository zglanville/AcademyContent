package za.co.wethinkcode.robotworlds.Server.worldCommands.ServerAdminCommand;

import za.co.wethinkcode.robotworlds.Server.ServerMain;
import za.co.wethinkcode.robotworlds.Server.maze.RandomMaze;

import java.util.Scanner;

/**
 * Class calls the respective server commands
 */
public class ServerCommand {
    public ServerCommand(String command, RandomMaze world){
        String[] commandsList = command.split(" ");
        switch (commandsList[0]){
            case "save":
                Scanner nameInput = new Scanner(System.in);
                System.out.print("World name?: ");
                String worldName = nameInput.nextLine();
                ReadWorld data = new ReadWorld();
//                RestoreWorld data = new RestoreWorld(world, worldName);
                if(!data.isNameTaken(worldName)){
                    final SaveWorld app = new SaveWorld(world, worldName);
                    System.out.println("World " + worldName + " has been saved.");
                }
                else{
                    System.out.println("World name is taken.");
                }
                break;
            case "restore":
                Scanner restoreInput = new Scanner(System.in);
                System.out.println("World to restore: ");
                String restoreWorldName = restoreInput.nextLine();
                ReadWorld worldData = new ReadWorld();
                if(worldData.isNameTaken(restoreWorldName)){
                    new RestoreWorld(world, restoreWorldName);
                    System.out.println("World " + restoreWorldName + " has been restored.");
                    System.out.println("World obstacles: " + world.getObstacles());
                }
                else{
                    System.out.println("World does not exist.");
                }
                break;
//            case "read":
//                System.out.println("Reading world");
//                new ReadWorld(world, );
//                System.out.println("World Read");
//                break;
            case "dump":
                new DumpCommand(world);
                break;
            case "purge":
                try {
                    new PurgeCommand(commandsList[1]);
                }catch (IndexOutOfBoundsException e){System.out.println("Command requires a robot to be purged");}
                break;
            case "robots":
                new RobotsCommand();
                break;
            case "quit":
                int i = 0;
                while (i < ServerMain.robots.size())
                    new QuitCommand();
                    i++;
                System.exit(0);
                break;
            default:
                throw new IllegalArgumentException("Cannot process your request.");
        }
    }
}

