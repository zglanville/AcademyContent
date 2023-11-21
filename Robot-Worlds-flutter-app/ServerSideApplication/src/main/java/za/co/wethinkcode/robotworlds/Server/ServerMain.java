/**
 * @authors Issa & Thonifho
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.wethinkcode.robotworlds.Server;

import za.co.wethinkcode.robotworlds.Server.maze.RandomMaze;
import za.co.wethinkcode.robotworlds.Server.robot.RobotWorldConfiguration;
import za.co.wethinkcode.robotworlds.Server.robot.RobotHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import picocli.CommandLine;

/**
 * Server Main Class to run the server and create the multi thread
 * for connected clients
 * @author Thoni
 */
public class ServerMain {
    private static ServerSocket serverSocket;
    public static List<RobotHandler> robots = new ArrayList<>();

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        new CommandLine(new CommandLineConfig()).execute(args);
        RobotWorldConfiguration config = CommandLineConfig.getConfig();

        // System.out.println(config.getPort());

        final int maxClients = 20;
        serverSocket = new ServerSocket(config.getPort());
        // serverSocket.setSoTimeout(5000);
        serverSocket.setReuseAddress(false);

        System.out.println("\n[Status] World server waiting for clients on port " +
                            serverSocket.getLocalPort() + "..");

        ExecutorService service = Executors.newFixedThreadPool(maxClients);
        ExecutorService adminService = Executors.newFixedThreadPool(1);

        RandomMaze world = new RandomMaze(config);
      
        while (true) {

            // TimeUnit.MILLISECONDS.sleep(20);
            
            adminService.submit(new ServerAdminInput(world));
            
            try {

                Socket socket = serverSocket.accept();
                TimeUnit.MILLISECONDS.sleep(50);
                System.out.println("\n[Client] Connected to " + 
                                    socket.getRemoteSocketAddress());

                // Iterate through robotHandler list, remove robots that have disconnected
                System.out.println("looping through robotHandlers");
                for (int i = robots.size() - 1; i >= 0; --i) {
                    try {
                        robots.get(i).getSocket().getOutputStream().write(32);
                    } catch (IOException e) {
                        // System.out.println("loop found and disconnected" + robots.get(i).getRobot().getName());
                        robots.get(i).disconnect();
                    }
                }

                RobotHandler robotHandler = new RobotHandler(socket, world);
                robots.add(robotHandler);
                service.submit(robotHandler);
                
                System.out.println(" > Note: Connected Client(s): " +
                                    robots.size() + "/" + maxClients);
                System.out.println("[Status] Waiting for " +
                        (maxClients - robots.size()) +
                                    " more clients..");

            } catch (SocketTimeoutException e) {
                System.out.println("TIMEOUT DUE TO INACTIVITY" +
                                    (maxClients - robots.size()) +
                                    " more clients..");
            }
        }
    }
}
