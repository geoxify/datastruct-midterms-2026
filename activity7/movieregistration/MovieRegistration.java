package activity7.movieregistration;

import java.util.Scanner;

public class MovieRegistration {
    
    // --- COLOR PALETTE ---
    public static final String RESET = "\u001B[0m";
    public static final String CYAN = "\u001B[36m";
    public static final String YELLOW = "\u001B[33m";
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
    public static final String BLUE_BOLD = "\033[1;34m";
    public static final String WHITE_BOLD = "\033[1;37m";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Step 1: Variables
        int rent = 0, sales = 0;
        int comedy = 0, Horror = 0, Scifi = 0, Drama = 0, Cartoons = 0;
        int dvdtotal = 0, vcdtotal = 0, tapetotal = 0;

        String answer;
        
        do {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            
            System.out.println(BLUE_BOLD + "\n================================================" + RESET);
            System.out.println(BLUE_BOLD + "        EFM MOVIE RENTAL REGISTRATION           " + RESET);
            System.out.println(BLUE_BOLD + "================================================" + RESET);

            System.out.println(WHITE_BOLD + " Select Media Type:" + RESET);
            System.out.println(CYAN + " [1] " + RESET + "DVD");
            System.out.println(CYAN + " [2] " + RESET + "VCD");
            System.out.println(CYAN + " [3] " + RESET + "Tape");
            System.out.print(YELLOW + " Choice: " + RESET);
            
            int code = sc.nextInt();
            String typeLabel = "";
            if (code == 1) {
                typeLabel = "DVD";
                dvdtotal++;
            } else if (code == 2) {
                typeLabel = "VCD";
                vcdtotal++;
            } else if (code == 3) {
                typeLabel = "Tape";
                tapetotal++;
            }
            System.out.println(GREEN + " >>> Type Selected: " + typeLabel + RESET);
            sc.nextLine(); // Consume newline

            // Step 10: Details
            System.out.print(YELLOW + " Input title: " + RESET);
            String title = sc.nextLine();

            System.out.println("\n" + WHITE_BOLD + " Select Category:" + RESET);
            System.out.println(CYAN + " [1] " + RESET + "Horror");
            System.out.println(CYAN + " [2] " + RESET + "Scifi");
            System.out.println(CYAN + " [3] " + RESET + "Drama");
            System.out.println(CYAN + " [4] " + RESET + "Comedy");
            System.out.println(CYAN + " [5] " + RESET + "Cartoons");
            System.out.print(YELLOW + " Category: " + RESET);
            
            int category = sc.nextInt();
            if (category == 1) Horror++;
            else if (category == 2) Scifi++;
            else if (category == 3) Drama++;
            else if (category == 4) comedy++;
            else if (category == 5) Cartoons++;

            // Step 22: Technical Details
            System.out.print(YELLOW + " Minutes: " + RESET);
            int minutes = sc.nextInt();
            sc.nextLine(); // Consume newline

            System.out.print(YELLOW + " Setting: " + RESET);
            String setting = sc.nextLine();

            System.out.println("\n" + WHITE_BOLD + " Transaction Type:" + RESET);
            System.out.println(CYAN + " [1] " + RESET + "Rental");
            System.out.println(CYAN + " [2] " + RESET + "Sales");
            System.out.print(YELLOW + " Transaction: " + RESET);
            
            int transactionType = sc.nextInt();
            if (transactionType == 1) rent++;
            else if (transactionType == 2) sales++;

            // Step 29: Price
            System.out.print(YELLOW + " Input price: " + RESET);
            int price = sc.nextInt();
            sc.nextLine(); // Consume newline

            System.out.println(CYAN + "------------------------------------------------" + RESET);
            System.out.print(YELLOW + " Register another movie? (yes/no): " + RESET);
            answer = sc.nextLine().toLowerCase();

        } while (answer.equals("yes"));

        // Final Report
        System.out.println(BLUE_BOLD + "\n================================================" + RESET);
        System.out.println(BLUE_BOLD + "               SESSION REPORTS                  " + RESET);
        System.out.println(BLUE_BOLD + "================================================" + RESET);
        
        System.out.printf(" %-20s : " + GREEN + "%d" + RESET + "\n", "For Rent", rent);
        System.out.printf(" %-20s : " + GREEN + "%d" + RESET + "\n", "For Sale", sales);
        System.out.println(CYAN + "------------------------------------------------" + RESET);
        System.out.printf(" %-20s : " + WHITE_BOLD + "%d" + RESET + "\n", "VCD Total", vcdtotal);
        System.out.printf(" %-20s : " + WHITE_BOLD + "%d" + RESET + "\n", "DVD Total", dvdtotal);
        System.out.printf(" %-20s : " + WHITE_BOLD + "%d" + RESET + "\n", "Tape Total", tapetotal);
        System.out.println(CYAN + "------------------------------------------------" + RESET);
        System.out.printf(" %-20s : %d\n", "Horror movies", Horror);
        System.out.printf(" %-20s : %d\n", "Scifi movies", Scifi);
        System.out.printf(" %-20s : %d\n", "Drama movies", Drama);
        System.out.printf(" %-20s : %d\n", "Comedy movies", comedy);
        System.out.printf(" %-20s : %d\n", "Cartoons", Cartoons);
        System.out.println(BLUE_BOLD + "================================================\n" + RESET);

        // sc.close() 
    }
}