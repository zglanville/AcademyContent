package za.co.wethinkcode.mastermind;

import java.util.ArrayList;

public class Mastermind {
    private final String code;
    private final Player player;

    public Mastermind(CodeGenerator generator, Player player){
        this.code = generator.generateCode();
        this.player = player;
    }
    public Mastermind(){
        this(new CodeGenerator(), new Player());
    }

    public void runGame(){
        //TODO: implement the main run loop logic
        System.out.println("4-digit Code has been set. Digits in range 1 to 8. You have 12 turns to break it.");
        while(player.getGuesses() > 0) {
            String guess = this.player.getGuess();
            ArrayList<Integer> numbers = correct_digits(guess, this.code);
            System.out.println("Number of correct digits in correct place: " + numbers.get(0));
            System.out.println("Number of correct digits not in correct place: " + numbers.get(1));
            if (numbers.get(0) == 4){
                System.out.println("Congratulations! You are a codebreaker!");
                break;
            }else {
                player.numGuesses();
                if (player.getGuesses() > 0) {
                    System.out.println("Turns left: " + player.getGuesses());
                }
            }
        }
        if(player.getGuesses() == 0){
            System.out.println("No more turns left.");
        }
        System.out.println("The code was: " + this.code);
    }

    public static void main(String[] args){
        Mastermind game = new Mastermind();
        game.runGame();
    }

    public ArrayList<Integer> correct_digits(String guess, String code){
        ArrayList<Integer> numbers = new ArrayList<>();
        int correct_digits_and_position = 0;
        int correct_digits_only = 0;
        for (int i = 0; i < 4; i++) {
            if (code.charAt(i) == guess.charAt(i)){
                correct_digits_and_position += 1;
            }else if (guess.contains(String.valueOf(code.charAt(i)))){
                correct_digits_only += 1;
            }
        }
        numbers.add(correct_digits_and_position);
        numbers.add(correct_digits_only);
        return numbers;
    }
}
