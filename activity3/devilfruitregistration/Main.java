package activity3.devilfruitregistration;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        printMainMenu();
        int choice = getValidInt("Menu: ");
        if (choice > 7 || choice < 1) {
            main(args);
        } else {
            mainMenu(choice);
        }

    }

    public static void mainMenu(int choice) {
        switch (choice) {
            case 1:
                System.out.println("Add");
                break;
            case 2:
                System.out.println("Search");
                break;
            case 3:
                System.out.println("Edit");
                break;
            case 4:
                System.out.println("Delete");
                break;
            case 5:
                System.out.println("Sort");
                break;
            case 6:
                System.out.println("List");
                break;
            case 7:
                System.out.println("Terminating program...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice!");
                mainMenu(choice);
                break;
        }
    }

    public static void printMainMenu() {
        System.out.print("""

                ____ Main Menu ____
                [1] Add
                [2] Search
                [3] Edit
                [4] Delete
                [5] Sort
                [6] List
                [7] Exit Program

                """);
    }

    public static int getValidInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int num = sc.nextInt();
                sc.nextLine(); // clear buffer
                return num;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a whole number.");
                sc.nextLine(); // clear bad input
            }
        }
    }

    public static double getValidDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double num = sc.nextDouble();
                sc.nextLine(); // clear buffer
                return num;
            } catch (InputMismatchException e) {
                System.out.println("Invalid number format. Try again.");
                sc.nextLine(); // clear bad input
            }
        }
    }

    public static String getString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }
}