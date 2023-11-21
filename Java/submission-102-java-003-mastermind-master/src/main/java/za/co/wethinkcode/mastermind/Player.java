package za.co.wethinkcode.mastermind;

import java.io.InputStream;
import java.sql.SQLOutput;
import java.util.Locale;
import java.util.Scanner;

public class Player {
    private final Scanner inputScanner;

    public Player(){
        this.inputScanner = new Scanner(System.in);
    }
    public int guesses = 12;

    public Player(InputStream inputStream){
        this.inputScanner = new Scanner(inputStream);
    }

    /**
     * Gets a guess from user via text console.
     * This must prompt the user to re-enter a guess until a valid 4-digit string is entered, or until the user enters `exit` or `quit`.
     *
     * @return the value entered by the user
     */
    public String getGuess(){
        String guess;
        boolean yes;
        while(true) {
            yes = true;
            System.out.println("Input 4 digit code:");
            guess = this.inputScanner.nextLine();
            if (guess.equalsIgnoreCase("exit") || guess.equalsIgnoreCase("quit")){
                System.exit(0);
            }
            if (guess.length() == 4) {
                for (int i = 0; i < 4; i++) {
                    int letter = guess.charAt(i) - '0';
                    if (letter < 1 || letter > 8) {
                        yes = false;
                        System.out.println("Please enter exactly 4 digits (each from 1 to 8).");
                        break;
                    }
                }
                if (yes){
                    return guess;
                }
            }else{
                System.out.println("Please enter exactly 4 digits (each from 1 to 8).");
            }
        }
    }

    public void numGuesses(){
        this.guesses -= 1;
    }

    public Integer getGuesses(){
        return guesses;
    }
}
