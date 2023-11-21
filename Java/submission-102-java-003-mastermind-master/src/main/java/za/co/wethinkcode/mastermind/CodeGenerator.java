package za.co.wethinkcode.mastermind;

import java.util.Random;

public class CodeGenerator {
    private final Random random;

    public CodeGenerator(){
        this.random = new Random();
    }

    public CodeGenerator(Random random){
        this.random = random;
    }

    /**
     * Generates a random 4 digit code, using this.random, where each digit is in the range 1 to 8 only.
     * Duplicated digits are allowed.
     * @return the generated 4-digit code
     */
    public String generateCode(){
        //TODO: implement using this.random
        String code = "";
        int upper = 8;
        int x;
        while(code.length() < 4){
            x = this.random.nextInt(upper) + 1;
            char num = (char) (x + '0');
            code += num;
        }
        return (code);
    }
}
