package za.co.wethinkcode.mastermind;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CodeGeneratorTest {

    @Test
    void testLength() {
        CodeGenerator codeGenerator = new CodeGenerator();
        assertEquals(4, codeGenerator.generateCode().length());
    }

    @Test
    void testDigits() {
        for (int x = 0; x < 2000; x++) {
            CodeGenerator codeGenerator = new CodeGenerator();
            String code = codeGenerator.generateCode();
            for (int i = 0; i < code.length(); i++) {
                assertTrue(code.charAt(i) - '0' > 0
                        && code.charAt(i) - '0' < 9);
            }
        }
    }
}