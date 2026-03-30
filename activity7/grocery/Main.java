package activity7.grocery;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    // --- COLOR PALETTE ---
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        File dataFile = new File("grocery_data.txt");

        // --- 2D ARRAY INVENTORY ---
        // { Name, Price }
        String[][] inventory = {
                { "Banana", "15.00" },
                { "Vitamilk", "30.00" },
                { "Rice", "15.00" },
                { "Egg", "10.00" },
                { "Biscuit", "10.00" }
        };

        try (FileWriter fw = new FileWriter(dataFile, true)) {
            log("\n--- SESSION START ---", fw);

            String newCustomer;
            do {
                System.out.println(CYAN + "\n=== Welcome to EFM Grocery ===" + RESET);
                Register currentRegister = new Register();
                String newProduct;

                do {
                    // 1. Show Inventory Menu
                    System.out.println("\n" + BLUE + "--- Available Items ---" + RESET);
                    for (int i = 0; i < inventory.length; i++) {
                        System.out.printf("[%d] %-15s " + GREEN + "P%s" + RESET + "\n",
                                (i + 1), inventory[i][0], inventory[i][1]);
                    }
                    System.out.println("[0] Manual Input");

                    // 2. Ask for Selection
                    System.out.print(YELLOW + "Select Product ID: " + RESET);
                    int choice = sc.nextInt();
                    log("User selected ID: " + choice, fw);
                    sc.nextLine(); // Consume newline

                    String productName;
                    double price;

                    // 3. Determine if Manual or From Inventory
                    if (choice > 0 && choice <= inventory.length) {
                        // Array index is choice - 1
                        productName = inventory[choice - 1][0];
                        price = Double.parseDouble(inventory[choice - 1][1]);
                        System.out.println(BLUE + "Selected: " + productName + RESET);
                    } else {
                        // Manual Input Logic
                        System.out.print(YELLOW + "Input Product Name: " + RESET);
                        productName = sc.nextLine();
                        log("User selected product: " + productName, fw);
                        System.out.print(YELLOW + "Enter Price: " + RESET);
                        price = sc.nextDouble();
                        log("User entered price: " + price, fw);
                        sc.nextLine();
                    }

                    // 4. Ask for Quantity
                    System.out.print(YELLOW + "Enter Quantity: " + RESET);
                    double quantity = sc.nextDouble();
                    log("User selected quantity: " + quantity, fw);
                    sc.nextLine();

                    // 5. Add to Register
                    Product p = new Product(productName, price, quantity);
                    currentRegister.scanItem(p);

                    System.out.print(YELLOW + "\nAdd another product? (Y/N): " + RESET);
                    newProduct = sc.nextLine();
                    log("User selected to add another product: " + newProduct, fw);

                } while (newProduct.equalsIgnoreCase("y"));

                // --- Payment Section ---
                double totalBill = currentRegister.getBillTotal();
                System.out.println("\nFinal Bill: " + GREEN + totalBill + RESET);

                while (true) {
                    System.out.print(YELLOW + "Enter Payment Amount: " + RESET);
                    double payment = sc.nextDouble();
                    log("User entered payment: " + payment, fw);
                    sc.nextLine();

                    double change = currentRegister.processPayment(payment);

                    if (change != -1.0) {
                        System.out.println(GREEN + "Change: " + change + RESET);
                        currentRegister.printReceipt();
                        break;
                    } else {
                        double missing = totalBill - payment;
                        System.out.println(RED + "Error: Insufficient funds. You need P" + missing + " more." + RESET);
                    }
                }

                System.out.print(YELLOW + "\nProcess next customer? (Y/N): " + RESET);
                newCustomer = sc.nextLine();
                log("User selected to process next customer: " + newCustomer, fw);

            } while (newCustomer.equalsIgnoreCase("y"));

            System.out.println(CYAN + "Program Terminating. Goodbye!" + RESET);

            log("--- SESSION END ---\n", fw);
        } catch (Exception e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }

    }

    public static void log(String message, FileWriter fw) throws IOException {
        System.out.println(message); // Prints to your screen
        fw.write(message + "\n"); // Saves to the text file
    }
}