package za.co.wethinkcode.examples.hangman;

import java.io.InputStream;
import java.util.Scanner;

public class Player {
    private final Scanner scanner;
    private int chances = 5;
    private boolean quit = false;

    public Player(InputStream in) {
        this.scanner = new Scanner(in);
    }

    public Player() {
        this.scanner = new Scanner(System.in);
    }

    public int getChances() {
        return chances;
    }

    public void lostChance() {
        if (!this.hasNoChances()) {
            this.chances--;
        }
    }

    public boolean hasNoChances() {
        return this.getChances() == 0;
    }

    public String getWordsFile() {
        String filename = scanner.nextLine();
        return filename.isBlank() ? "short_words.txt" : filename;
    }

    public String getGuess() {
        String text = scanner.nextLine();
        this.quit = text.equalsIgnoreCase("quit") || text.equalsIgnoreCase("exit");
        return text;
    }

    public boolean wantsToQuit() {
        return this.quit;
    }
}
