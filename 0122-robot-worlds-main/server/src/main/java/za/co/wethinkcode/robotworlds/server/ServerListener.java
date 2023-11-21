package za.co.wethinkcode.robotworlds.server;

import za.co.wethinkcode.robotworlds.server.maze.Maze;
import za.co.wethinkcode.robotworlds.server.maze.RandomMap;
import za.co.wethinkcode.robotworlds.server.configReader.ConfigReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener implements Runnable {
    private static ConfigReader config;
    private static Maze maze;
    private BufferedReader in;

    public ServerListener (){
        this.in = new BufferedReader( new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        try (ServerSocket s = new ServerSocket(ClientHandler.PORT)) {
            System.out.println("za.co.wethinkcode.server.ClientHandler running & waiting for client connections.");
            System.out.println("Setting up the za.co.wethinkcode.server.world... ");
            worldSetUp();
            while (true) {
                Socket socket = s.accept();
                System.out.println("Connection: " + socket);

                Runnable r = new ClientHandler(socket, maze, config);

                Thread task = new Thread(r);
                task.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void worldSetUp() {
        config = new ConfigReader();
        maze = new RandomMap(config);
    }




}
