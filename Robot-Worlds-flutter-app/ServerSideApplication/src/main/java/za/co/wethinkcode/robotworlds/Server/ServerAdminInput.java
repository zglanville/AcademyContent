package za.co.wethinkcode.robotworlds.Server;

import za.co.wethinkcode.robotworlds.Server.maze.RandomMaze;
import za.co.wethinkcode.robotworlds.Server.worldCommands.ServerAdminCommand.ServerCommand;

import java.util.Scanner;

/**
 * Run method to be used for the server input thread
 * @author Issa
 */
public class ServerAdminInput implements Runnable{
    RandomMaze world;

    public ServerAdminInput(RandomMaze world){
        this.world = world;
    }

    @Override
    public void run() {

        Scanner adminInput = new Scanner(System.in);

        while (true) {
            System.out.print("[SERVER ADMIN]>>>");
            String input = adminInput.nextLine();
            new ServerCommand(input, this.world);
        }
    }
}

