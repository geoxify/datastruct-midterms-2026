package activity9.devilfruitregistration;

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

    // File paths
    static String dataFileName = "activity9/devilfruit.txt"; // Update to your specific folder e.g., "activity9/devilfruit.txt"
    static String sessionFileName = "activity9/session.txt";

    public static void main(String[] args) {
        printOutLn("=== NEW SESSION STARTED ===");
        while (true) {
            printMainMenu();
            int choice = getValidInt("Menu: ");
            if (choice > 7 || choice < 1) {
                printOutLn("Invalid choice.");
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
                sort();
                break;
            case 6:
                list();
                break;
            case 7:
                printOutLn("Terminating program...");
                System.exit(0);
                break;
            default:
                printOutLn("Invalid choice!");
                break;
        }
    }

    public static void printMainMenu() {
        printOutLn("\n____ Main Menu ____");
        printOutLn("[1] Add");
        printOutLn("[2] Search");
        printOutLn("[3] Edit");
        printOutLn("[4] Delete");
        printOutLn("[5] Sort");
        printOutLn("[6] List");
        printOutLn("[7] Exit Program\n");
    }

    // ==========================================
    // LOGGING AND I/O WRAPPERS (SESSION RECORDING)
    // ==========================================

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
                printOut(prompt);
                int num = sc.nextInt();
                sc.nextLine(); // clear buffer
                appendToSession(num + "\n"); // log the user input
                return num;
            } catch (InputMismatchException e) {
                printOutLn("Invalid input. Please enter a whole number.");
                sc.nextLine(); // clear bad input
            }
        }
    }

    public static String getString(String prompt) {
        printOut(prompt);
        String input = sc.nextLine();
        appendToSession(input + "\n"); // log the user input
        return input;
    }

    // ==========================================
    // DATA HANDLING & ARRAY OPERATIONS
    // ==========================================

    public static void loadDataFromFile() {
        if (dataLoaded) return;

        File file = new File(dataFileName);
        if (!file.exists()) return;

        try (Scanner fileReader = new Scanner(file)) {
            ctr = 0;
            while (fileReader.hasNextLine() && ctr < MAX_SIZE) {
                aNames[ctr] = fileReader.nextLine();
                if (!fileReader.hasNextLine()) break;
                aDevilFruit[ctr] = fileReader.nextLine();
                if (!fileReader.hasNextLine()) break;
                aType[ctr] = fileReader.nextLine();
                if (!fileReader.hasNextLine()) break;
                aStatus[ctr] = fileReader.nextLine();
                ctr++;
            }
            dataLoaded = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveDataToFile() {
        File file = new File(dataFileName);
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
        if (ctr == 0) {
            printOutLn("The list is empty.");
            return;
        }
        for (int a = 0; a < ctr; a++) {
            printEntry(a);
        }
    }

    public static void add() {
        loadDataFromFile();
        if (ctr >= MAX_SIZE) {
            printOutLn("Array is full. Cannot add more entries.");
            return;
        }

        String name = getString("Name: ");
        String fruit = getString("Devil Fruit: ");
        String type = getString("Type: ");
        String status = getString("Status: ");

        printOutLn("Insert options:");
        printOutLn("[1] Last index available");
        printOutLn("[2] First index");
        printOutLn("[3] Middle index");
        int insertChoice = getValidInt("Choose insert position: ");

        int insertIndex = ctr; // default to last
        switch (insertChoice) {
            case 1: insertIndex = ctr; break;
            case 2: insertIndex = 0; break;
            case 3: insertIndex = ctr / 2; break;
            default: printOutLn("Invalid choice. Entry added at last index by default."); break;
        }

        // Shift and insert
        for (int i = ctr; i > insertIndex; i--) {
            aNames[i] = aNames[i - 1];
            aDevilFruit[i] = aDevilFruit[i - 1];
            aType[i] = aType[i - 1];
            aStatus[i] = aStatus[i - 1];
        }

        aNames[insertIndex] = name;
        aDevilFruit[insertIndex] = fruit;
        aType[insertIndex] = type;
        aStatus[insertIndex] = status;
        ctr++;

        saveDataToFile();
        printOutLn("Entry added successfully.");
    }

    // Activity 6 - Search
    public static void search() {
        loadDataFromFile();
        if (ctr == 0) {
            printOutLn("No data available to search.");
            return;
        }
        int searchIndex = getValidInt("Choose search index (1 to " + ctr + "): ") - 1;

        if (searchIndex >= 0 && searchIndex < ctr) {
            printEntry(searchIndex);
        } else {
            printOutLn("Index out of bounds.");
        }
    }

    // Activity 7 - Edit
    public static void edit() {
        loadDataFromFile();
        if (ctr == 0) {
            printOutLn("No data available to edit.");
            return;
        }
        int searchIndex = getValidInt("Choose edit index (1 to " + ctr + "): ") - 1;

        if (searchIndex >= 0 && searchIndex < ctr) {
            printEntry(searchIndex);

            String edit = getString("Would you like to edit this entry? (Y/N): ");
            if (edit.equalsIgnoreCase("Y")) {
                aNames[searchIndex] = getString("New Name: ");
                aDevilFruit[searchIndex] = getString("New Devil Fruit: ");
                aType[searchIndex] = getString("New Type: ");
                aStatus[searchIndex] = getString("New Status: ");

                saveDataToFile();
                printOutLn("Successfully edited.");
            } else {
                printOutLn("Edit cancelled.");
            }
        } else {
            printOutLn("Index out of bounds.");
        }
    }

    // Activity 8 - Delete
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

    // Activity 9 - Sort
    public static void sort() {
        loadDataFromFile();
        if (ctr == 0) {
            printOutLn("No data available to sort.");
            return;
        }

        printOutLn("\nSort Options:");
        printOutLn("[1] Ascending Order (A-Z)");
        printOutLn("[2] Descending Order (Z-A)");
        int order = getValidInt("Choose sort order: ");

        if (order != 1 && order != 2) {
            printOutLn("Invalid choice. Returning to menu.");
            return;
        }

        // Parallel Bubble Sort based on the Name
        for (int i = 0; i < ctr - 1; i++) {
            for (int j = 0; j < ctr - i - 1; j++) {
                boolean swapNeeded = false;

                int comparison = aNames[j].compareToIgnoreCase(aNames[j + 1]);
                if (order == 1 && comparison > 0) { // Ascending
                    swapNeeded = true;
                } else if (order == 2 && comparison < 0) { // Descending
                    swapNeeded = true;
                }

                if (swapNeeded) {
                    // Swap Names
                    String tempName = aNames[j]; aNames[j] = aNames[j+1]; aNames[j+1] = tempName;
                    // Swap Devil Fruit
                    String tempFruit = aDevilFruit[j]; aDevilFruit[j] = aDevilFruit[j+1]; aDevilFruit[j+1] = tempFruit;
                    // Swap Type
                    String tempType = aType[j]; aType[j] = aType[j+1]; aType[j+1] = tempType;
                    // Swap Status
                    String tempStatus = aStatus[j]; aStatus[j] = aStatus[j+1]; aStatus[j+1] = tempStatus;
                }
            }
        }

        saveDataToFile();
        printOutLn("Data has been sorted successfully!");
        list(); // Display the ordered list
    }
}