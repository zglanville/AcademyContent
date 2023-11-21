package za.co.wethinkcode.robotworlds.client;

import za.co.wethinkcode.robotworlds.client.colors.AnsiColors;
import za.co.wethinkcode.robotworlds.client.json.encoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Locale;
import java.util.Scanner;

public class Client {
    static Scanner scanner;
    static String[] validCommands = {"back", "forward", "help", "orientation", "shutdown", "turn", "look", "health", "reload","fire","repair"};

    public static void main(String args[]) {
        try (
                Socket socket = new Socket("localhost", 5000);
                PrintStream out = new PrintStream(socket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            System.out.println(AnsiColors.ANSI_BRIGHT_MAGENTA + "Welcome to Robot Worlds!\n" + AnsiColors.ANSI_RESET +
                    "Please select a make and name for your robot, and hop right in!\n" +
                    AnsiColors.ANSI_PURPLE + "for more info on how to launch try `launch_help` or `lh`." + AnsiColors.ANSI_RESET);
            scanner = new Scanner(System.in);
            boolean validName = false;
            String name = "";
            String commands = "";
            String messageFromServer = "";


//            String requestName = (String)encoder.launchRequest().get("robot");

            while (!validName) {
                validName = invalidLaunch(commands = getInput("Launch your Robot!: "));
                if (!validName) { continue; }
                String[] arg = commands.split(" ");
                String argument = arg[1];
                String command = arg[0];
                name = arg[2];

                encoder encoder = new encoder(name, command, argument);
                System.out.println(encoder.request());
                String request = encoder.request().toJSONString();

                Runnable responseListener = new ResponseListener(in,out);
                Thread thread = new Thread(responseListener);
                thread.start();
                out.println(request);
                out.flush();
//                messageFromServer = in.readLine();
            }

//            String command;
            while (true) {
                commands = getInput("");

                if (commands.equalsIgnoreCase("quit")) {
                    out.println("removeme");
                    out.flush();
                    break;
                }
                String argument = "";
                int length = commands.split(" ").length;

                if(length == 1){
                    encoder encoder = new encoder(name, commands, argument);
                    String request = encoder.request().toJSONString();
                    Runnable responseListener = new ResponseListener(in,out);
                    Thread thread = new Thread(responseListener);
                    thread.start();
                    out.println(request);
                    out.flush();
                }else {
                    String[] arg;
                    arg = commands.split(" ");
                    argument = arg[1];
                    String command = arg[0];

                    encoder encoder = new encoder(name, command, argument);
                    System.out.println(encoder.request());
                    String request = encoder.request().toJSONString();
                    Runnable responseListener = new ResponseListener(in,out);
                    Thread thread = new Thread(responseListener);
                    thread.start();
                    out.println(request);
                    out.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getInput(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine();

        while (input.isBlank()) {
            System.out.print(prompt + " \n");
            input = scanner.nextLine();
        }
        return input;
    }

    public static boolean invalidLaunch(String launch) {
        String[] launchCommands = launch.split(" ");

        if (launchCommands[0].equalsIgnoreCase("launch_help") || launchCommands[0].equalsIgnoreCase("lh")) {
            System.out.println(launchHelp());
            return false;
        } else if (launchCommands[0].equalsIgnoreCase("makes_help") || launchCommands[0].equalsIgnoreCase("mh")) {
            System.out.println(makesHelp());
            return false;
        }

        if (((launchCommands[0].equalsIgnoreCase("launch")) || (launchCommands[0].equalsIgnoreCase("l"))) && ((validMake(launchCommands[1])) || (launchCommands[1].equalsIgnoreCase("m"))) && (launchCommands[2].length() > 0)) {
            return true;
        }

        System.out.println("Please make sure you launch correctly :)! \n" +
                "for help remember to check either `lh` or 'mh`");
        return false;
    }

    public static boolean validMake(String make) {
        make = make.toLowerCase(Locale.ROOT);
        return make.equals("sniper") || make.equals("tank") || make.equals("gunner") || make.equals("miner") || make.equals("god");
    }

    public static String launchHelp() {
        return ("Please make sure you launch it with \"launch <make> <name>\"\n" +
                "Makes: 1. " + AnsiColors.ANSI_BRIGHT_BLUE + "Sniper " + AnsiColors.ANSI_RESET + " (3 shield, 3x range).\n" +
                "       2. " + AnsiColors.ANSI_CYAN + "Tank " + AnsiColors.ANSI_RESET + "   (8 shield, 0.5x range).\n" +
                "       3. " + AnsiColors.ANSI_GREEN + "Gunner " + AnsiColors.ANSI_RESET + " (5 shield, 1x range).\n" +
                "       4. " + AnsiColors.ANSI_BRIGHT_YELLOW + "Miner " + AnsiColors.ANSI_RESET + "  (5 shield, 2x sight).\n" +
                AnsiColors.ANSI_PURPLE + "for more info try `makes_help` or `mh` \n" + AnsiColors.ANSI_RESET);
    }

    public static String makesHelp() {
        return("Here is some more info on the makes of robot:\n" +
                "       1. " + AnsiColors.ANSI_BRIGHT_BLUE + "Sniper: " + AnsiColors.ANSI_RESET + "Less shields, however if has 3x vision and shooting range.\n" +
                "       2. " + AnsiColors.ANSI_CYAN + "Tank: " + AnsiColors.ANSI_RESET + "  More shields, however it has 0.5 vision and shooting range.\n" +
                "       3. " + AnsiColors.ANSI_GREEN + "Gunner: " + AnsiColors.ANSI_RESET + "Has default shields of 5, and default vision and shooting range of 1.\n" +
                "       4. " + AnsiColors.ANSI_BRIGHT_YELLOW + "Miner: " + AnsiColors.ANSI_RESET + " Has default shields of 5, however it cannot shoot, it can only place mines \n" +
                "                  with the `mine` command. (when placing a mind, you have no shields and cannot move,\n" +
                "                  does not have any active weapons).\n");
    }
}