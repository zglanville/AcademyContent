package za.co.wethinkcode.robotworlds.server.colors;

public class AnsiColors {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_WHITE = "\u001b[37m";

    public static final String ANSI_BRIGHT_WHITE = "\u001b[37;1m";
    public static final String ANSI_BRIGHT_CYAN = "\u001b[36;1m";
    public static final String ANSI_BRIGHT_MAGENTA = "\u001b[35;1m";
    public static final String ANSI_BRIGHT_BLUE = "\u001b[34;1m";
    public static final String ANSI_BRIGHT_YELLOW = "\u001b[33;1m";
    public static final String ANSI_BRIGHT_GREEN = "\u001b[32;1m";
    public static final String ANSI_BRIGHT_RED = "\u001b[31;1m";
    public static final String ANSI_BRIGHT_BLACK = "\u001b[30;1m";

    public static void main(String[] args) {
        System.out.println(ANSI_GREEN + "GREEN" + ANSI_RESET);
        System.out.println(ANSI_BLUE + "BLUE" + ANSI_RESET);
        System.out.println(ANSI_RED + "RED" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "CYAN" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "PURPLE" + ANSI_RESET);
        System.out.println(ANSI_BLACK + "BLACK" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "YELLOW" + ANSI_RESET);
        System.out.println(ANSI_WHITE + "WHITE" + ANSI_RESET);
        System.out.println();
        System.out.println(ANSI_BRIGHT_WHITE + "BWHITE" + ANSI_RESET);
        System.out.println(ANSI_BRIGHT_CYAN + "BCYAN" + ANSI_RESET);
        System.out.println(ANSI_BRIGHT_MAGENTA + "BMAGENTA" + ANSI_RESET);
        System.out.println(ANSI_BRIGHT_BLUE + "BBLUE" + ANSI_RESET);
        System.out.println(ANSI_BRIGHT_YELLOW + "BYELLOW" + ANSI_RESET);
        System.out.println(ANSI_BRIGHT_GREEN + "BGREEN" + ANSI_RESET);
        System.out.println(ANSI_BRIGHT_RED + "BRED" + ANSI_RESET);
        System.out.println(ANSI_BRIGHT_BLACK + "BBLACK" + ANSI_RESET);
    }
}
