package activity8.devilfruitregistration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static final int MAX_SIZE = 50;
    static String[] aNames = new String[MAX_SIZE];
    static String[] aDevilFruit = new String[MAX_SIZE];
    static String[] aType = new String[MAX_SIZE];
    static String[] aStatus = new String[MAX_SIZE];
    static int ctr = 0;
    static boolean dataLoaded = false;

    static String dataFileName = "activity8/devilfruit.txt";
    static String sessionFileName = "session.txt";

    public static void main(String[] args) {

        while (true) {
            printMainMenu();
            int choice = getValidInt("Menu: ");
            if (choice > 7 || choice < 1) {
                System.out.println("Invalid choice.");
                main(args);
            } else {
                mainMenu(choice);
            }
        }

    }

    public static void mainMenu(int choice) {
        switch (choice) {
            case 1:
                add();
                break;
            case 2:
                search();
                break;
            case 3:
                edit();
                break;
            case 4:
                delete();
                break;
            case 5:
                System.out.println("Sort");
                break;
            case 6:
                list();
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

    public static void appendToSession(String text) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(sessionFileName, true))) {
            writer.print(text);
        } catch (IOException e) {
            System.out.println("Error writing to session.txt");
        }
    }

    public static void printOut(String text) {
        System.out.print(text);
        appendToSession(text);
    }

    public static void printOutLn(String text) {
        System.out.println(text);
        appendToSession(text + "\n");
    }

    public static void printOutF(String format, Object... args) {
        String formatted = String.format(format, args);
        System.out.print(formatted);
        appendToSession(formatted);
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

    public static void loadDataFromFile() {
        if (dataLoaded) {
            return;
        }

        String fileName = "activity8/devilfruit.txt";
        File file = new File(fileName);

        try (Scanner fileReader = new Scanner(file)) {
            ctr = 0;
            while (fileReader.hasNextLine() && ctr < MAX_SIZE) {
                aNames[ctr] = fileReader.nextLine();

                if (!fileReader.hasNextLine()) {
                    break;
                }
                aDevilFruit[ctr] = fileReader.nextLine();

                if (!fileReader.hasNextLine()) {
                    break;
                }
                aType[ctr] = fileReader.nextLine();

                if (!fileReader.hasNextLine()) {
                    break;
                }
                aStatus[ctr] = fileReader.nextLine();

                ctr++;
            }
            dataLoaded = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertAtIndex(int index, String name, String fruit, String type, String status) {
        for (int i = ctr; i > index; i--) {
            aNames[i] = aNames[i - 1];
            aDevilFruit[i] = aDevilFruit[i - 1];
            aType[i] = aType[i - 1];
            aStatus[i] = aStatus[i - 1];
        }

        aNames[index] = name;
        aDevilFruit[index] = fruit;
        aType[index] = type;
        aStatus[index] = status;
        ctr++;
    }

    public static void saveDataToFile() {
        String fileName = "activity8/devilfruit.txt";
        File file = new File(fileName);

        try (PrintWriter writer = new PrintWriter(file)) {
            for (int i = 0; i < ctr; i++) {
                writer.println(aNames[i]);
                writer.println(aDevilFruit[i]);
                writer.println(aType[i]);
                writer.println(aStatus[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printEntry(int index) {
        printOutF("%d. Name: %s\n", index + 1, aNames[index]);
        printOutF("     Devil Fruit: %s\n     Type: %s\n     Status: %s\n",
                aDevilFruit[index], aType[index], aStatus[index]);
    }

    public static void list() {
        loadDataFromFile();

        for (int a = 0; a < ctr; a++) { // print the array
            System.out.printf("%d. Name: %s\n", a + 1, aNames[a]);
            System.out.printf("""
                         Devil Fruit: %s
                         Type: %s
                         Status: %s
                    """, aDevilFruit[a], aType[a], aStatus[a]);
        }

    }

    public static void add() {
        loadDataFromFile();

        if (ctr >= MAX_SIZE) {
            System.out.println("Array is full. Cannot add more entries.");
            return;
        }

        String name = getString("Name: ");
        String fruit = getString("Devil Fruit: ");
        String type = getString("Type: ");
        String status = getString("Status: ");

        System.out.println("Insert options:");
        System.out.println("[1] Last index available");
        System.out.println("[2] First index");
        System.out.println("[3] Middle index");
        int insertChoice = getValidInt("Choose insert position: ");

        switch (insertChoice) {
            case 1:
                insertAtIndex(ctr, name, fruit, type, status);
                saveDataToFile();
                System.out.println("Entry added at last index.");
                break;
            case 2:
                insertAtIndex(0, name, fruit, type, status);
                saveDataToFile();
                System.out.println("Entry inserted at first index.");
                break;
            case 3:
                insertAtIndex(ctr / 2, name, fruit, type, status);
                saveDataToFile();
                System.out.println("Entry inserted at middle index.");
                break;
            default:
                insertAtIndex(ctr, name, fruit, type, status);
                saveDataToFile();
                System.out.println("Invalid choice. Entry added at last index by default.");
                break;
        }

    }

    public static void search() {
        // This module will ask the user to input
        // for an index and display the contents at that index
        loadDataFromFile();
        int searchIndex = getValidInt("Choose search index: ");

        for (int i = 0; i <= ctr; i++) {
            if (i == searchIndex) {
                System.out.printf("%d. Name: %s\n", i + 1, aNames[i]);
                System.out.printf("""
                             Devil Fruit: %s
                             Type: %s
                             Status: %s
                        """, aDevilFruit[i], aType[i], aStatus[i]);
            }
        }
    }

    public static void edit() {
        loadDataFromFile();
        int searchIndex = getValidInt("Choose edit index: ");
        for (int i = 0; i <= ctr; i++) {
            if (i == searchIndex) {
                System.out.printf("%d. Name: %s\n", i + 1, aNames[i]);
                System.out.printf("""
                             Devil Fruit: %s
                             Type: %s
                             Status: %s
                        """, aDevilFruit[i], aType[i], aStatus[i]);
            }
        }

        String edit = getString("Would you like to edit this entry? (Y/N): ");
        if (edit.equalsIgnoreCase("Y")) {
            String name = getString("Name: ");
            String fruit = getString("Devil Fruit: ");
            String type = getString("Type: ");
            String status = getString("Status: ");

            insertAtIndex(ctr, name, fruit, type, status);
            saveDataToFile();
            System.out.println("Successfully edited.");
        }

    }

    public static void delete() {
        loadDataFromFile();
        if (ctr == 0) {
            printOutLn("No data available to delete.");
            return;
        }
        int delIndex = getValidInt("Choose index to delete (1 to " + ctr + "): ") - 1;

        if (delIndex >= 0 && delIndex < ctr) {
            printEntry(delIndex);

            String confirm = getString("Would you like to delete this entry? (Y/N): ");
            if (confirm.equalsIgnoreCase("Y")) {
                // Shift elements to the left to remove the blank space
                for (int i = delIndex; i < ctr - 1; i++) {
                    aNames[i] = aNames[i + 1];
                    aDevilFruit[i] = aDevilFruit[i + 1];
                    aType[i] = aType[i + 1];
                    aStatus[i] = aStatus[i + 1];
                }

                // Nullify the last entry just to be clean
                aNames[ctr - 1] = null;
                aDevilFruit[ctr - 1] = null;
                aType[ctr - 1] = null;
                aStatus[ctr - 1] = null;

                ctr--;
                saveDataToFile();
                printOutLn("Entry deleted successfully.");
            } else {
                printOutLn("Deletion cancelled.");
            }
        } else {
            printOutLn("Index out of bounds.");
        }
    }
}