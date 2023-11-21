package za.co.wethinkcode.robotworlds.clientTest;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.client.colors.AnsiColors;
import za.co.wethinkcode.robotworlds.client.Client;

import static org.junit.jupiter.api.Assertions.*;

public class RobotTests {
    @Test
    void validMake() {
        assertTrue(Client.validMake("gunner"));
        assertTrue(Client.validMake("sniper"));
        assertTrue(Client.validMake("tank"));
    }

    @Test
    void invalidMake() {
        assertFalse(Client.validMake("Bob"));
        assertFalse(Client.validMake("harry"));
        assertFalse(Client.validMake("machinegun"));
    }

    @Test
    void testHelp() {
        String helpCommand = "Please make sure you launch it with \"launch <make> <name>\"\n" +
                "Makes: 1. " + AnsiColors.ANSI_BRIGHT_BLUE + "Sniper " + AnsiColors.ANSI_RESET + " (3 shield, 3x range).\n" +
                "       2. " + AnsiColors.ANSI_CYAN + "Tank " + AnsiColors.ANSI_RESET + "   (8 shield, 0.5x range).\n" +
                "       3. " + AnsiColors.ANSI_GREEN + "Gunner " + AnsiColors.ANSI_RESET + " (5 shield, 1x range).\n" +
                "       4. " + AnsiColors.ANSI_BRIGHT_YELLOW + "Miner " + AnsiColors.ANSI_RESET + "  (5 shield, 2x sight).\n" +
                AnsiColors.ANSI_PURPLE + "for more info try `makes_help` or `mh` \n" + AnsiColors.ANSI_RESET;
        assertEquals(Client.launchHelp(), helpCommand);
    }

    @Test
    void testMakeHelp() {
        String makeHelp = "Here is some more info on the makes of robot:\n" +
                "       1. " + AnsiColors.ANSI_BRIGHT_BLUE + "Sniper: " + AnsiColors.ANSI_RESET + "Less shields, however if has 3x vision and shooting range.\n" +
                "       2. " + AnsiColors.ANSI_CYAN + "Tank: " + AnsiColors.ANSI_RESET + "  More shields, however it has 0.5 vision and shooting range.\n" +
                "       3. " + AnsiColors.ANSI_GREEN + "Gunner: " + AnsiColors.ANSI_RESET + "Has default shields of 5, and default vision and shooting range of 1.\n" +
                "       4. " + AnsiColors.ANSI_BRIGHT_YELLOW + "Miner: " + AnsiColors.ANSI_RESET + " Has default shields of 5, however it cannot shoot, it can only place mines \n" +
                "                  with the `mine` command. (when placing a mind, you have no shields and cannot move,\n" +
                "                  does not have any active weapons).\n";
        assertEquals(Client.makesHelp(), makeHelp);
    }

    @Test
    void validLaunchCommand() {
        assertTrue(Client.invalidLaunch("launch m harry"));
        assertTrue(Client.invalidLaunch("l m harry"));
    }

    @Test
    void inValidLaunchCommand() {
        assertFalse(Client.invalidLaunch("makes_help"));
        assertFalse(Client.invalidLaunch("mh"));
        assertFalse(Client.invalidLaunch("launch_help"));
        assertFalse(Client.invalidLaunch("lh"));
        assertFalse(Client.invalidLaunch("harry"));
        assertFalse(Client.invalidLaunch("lanch"));
    }
}
