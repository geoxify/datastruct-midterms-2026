package activity3;

import java.util.Scanner;

public class Main {

    // --- COLOR PALETTE ---
    public static final String RESET = "\u001B[0m";
    public static final String CYAN = "\u001B[36m";
    public static final String YELLOW = "\u001B[33m";
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
    public static final String WHITE_BOLD = "\033[1;37m";
    public static final String BLUE_BOLD = "\033[1;34m";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String choice;

        while (true) {
            // Clear screen (optional, works in some terminals)
            System.out.print("\033[H\033[2J");
            System.out.flush();

            printHeader();

            System.out.println(WHITE_BOLD + " Please choose one of the following:" + RESET);
            System.out.println(CYAN + " [1] " + RESET + "GEO Grocery ShopperMart POS");
            System.out.println(CYAN + " [2] " + RESET + "GEO Movie Rental Registration");
            System.out.println(CYAN + " [3] " + RESET + "GEO Devil Fruit Registration");
            System.out.println(CYAN + " [4] " + RESET + "Exit");

            System.out.println(CYAN + "------------------------------------------------" + RESET);
            System.out.print(GREEN + " Enter choice > " + RESET);

            choice = sc.nextLine().trim();

            if (choice.equals("4")) {
                printExitMessage();
                break;
            }

            String programName = "";
            if (choice.equals("1")) {
                programName = "EFM Grocery ShopperMart POS";
            } else if (choice.equals("2")) {
                programName = "EFM Movie Rental Registration";
            } else if (choice.equals("3")) {
                programName = "EFM Devil Fruit Registration";
            } else {
                System.out.println(RED + "\n Invalid choice! Please try again." + RESET);
                waitForEnter(sc);
                continue;
            }

            // Loading Animation
            System.out.print("\n" + YELLOW + " Loading " + programName + RESET);
            try {
                for (int i = 0; i < 5; i++) {
                    System.out.print(YELLOW + "." + RESET);
                    Thread.sleep(400);
                }
                System.out.println("\n"); // New line after dots
            } catch (InterruptedException e) {
                System.out.println();
            }

            // --- RUN SELECTED PROGRAM ---
            // Note: Ensure sub-programs do NOT contain 'sc.close()' or the menu will break.
            try {
                if (choice.equals("1")) {
                    activity3.grocery.Main.main(args);
                } else if (choice.equals("2")) {
                    activity3.movieregistration.MovieRegistration.main(args);
                } else if (choice.equals("3")) {
                    activity3.devilfruitregistration.Main.main(args);
                }
            } catch (Exception e) {
                System.out.println(RED + "An error occurred in the module: " + e.getMessage() + RESET);
            }

            System.out.println(CYAN + "\n------------------------------------------------" + RESET);
            System.out.println(GREEN + " Module execution finished." + RESET);
            waitForEnter(sc);
        }

        sc.close();
    }

    private static void printHeader() {
        System.out.println(BLUE_BOLD + "\n================================================" + RESET);
        System.out.println(BLUE_BOLD + "         GEO ENTERPRISE SYSTEMS v1.0            " + RESET);
        System.out.println(BLUE_BOLD + "================================================" + RESET);
        System.out.println(WHITE_BOLD + "         We've got it all for you!              " + RESET);
        System.out.println(BLUE_BOLD + "================================================\n" + RESET);
    }

    private static void printExitMessage() {
        System.out.println(BLUE_BOLD + "\n================================================" + RESET);
        System.out.println(WHITE_BOLD + "   Thank you for using GEO Enterprise Systems   " + RESET);
        System.out.println(BLUE_BOLD + "================================================" + RESET);
        System.out.println(GREEN + "               Goodbye!                         " + RESET);
        System.out.println(BLUE_BOLD + "================================================\n" + RESET);
    }

    private static void waitForEnter(Scanner sc) {
        System.out.print(YELLOW + " Press [ENTER] to return to Main Menu..." + RESET);
        sc.nextLine();
    }
}