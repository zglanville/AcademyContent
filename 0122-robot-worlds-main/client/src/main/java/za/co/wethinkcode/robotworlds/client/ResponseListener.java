package za.co.wethinkcode.robotworlds.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

public class ResponseListener implements Runnable{
    private BufferedReader in;
    private PrintStream out;


    public ResponseListener(BufferedReader in, PrintStream out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
        while (true){
            String messageFromServer = null;
            try {
                messageFromServer = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (messageFromServer.contains("boom, you died! LOL NOOB GG REKT")) {
                out.println("removeMe");
                out.flush();
                System.out.println("< " + messageFromServer);
                System.exit(69);
            } else if (messageFromServer.contains("You have died. :(!")) {
                out.println("removeme");
                out.flush();
                System.out.println("< " + messageFromServer);
                System.exit(420);
            }
            System.out.println("< " + messageFromServer);
        }
    }
}
