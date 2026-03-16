package activity4;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        printMainMenu();
        int choice = getValidInt("Menu: ");
        if (choice > 5 || choice < 1) {
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
                System.out.println("Delete");
                break;
            case 4:
                System.out.println("Sort");
                break;
            case 5:
                list();
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
                [3] Delete
                [4] Sort
                [5] List

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

    public static void list() {
        String fileName = "activity4/devilfruit.txt";
        File file = new File(fileName);

        try (Scanner fileReader = new Scanner(file);) {
            String aNames[] = new String[50];
            String aDevilFruit[] = new String[50];
            String aType[] = new String[50];
            String aStatus[] = new String[50];
            int ctr = 0;
            while (fileReader.hasNextLine()) {
                aNames[ctr] = fileReader.nextLine();
                aDevilFruit[ctr] = fileReader.nextLine();
                aType[ctr] = fileReader.nextLine();
                aStatus[ctr] = fileReader.nextLine();
                ctr++;
            }

            for (int a = 0; a < ctr; a++) { // print the array
                System.out.printf("%d. Name: %s\n", a + 1, aNames[a]);
                System.out.printf("""
                             Devil Fruit: %s
                             Type: %s
                             Status: %s
                        """, aDevilFruit[a], aType[a], aStatus[a]);
            }

            fileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
