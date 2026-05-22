package final_program.grocery;

import activity9.grocery.Main;
import activity9.grocery.Product;

public class Register {
    private double currentBill;
    private activity9.grocery.Product[] cart;
    private int itemCount;

    public Register() {
        this.currentBill = 0;
        this.cart = new activity9.grocery.Product[50];
        this.itemCount = 0;
    }

    public void scanItem(Product p) {
        double itemCost = p.getLineTotal();
        this.currentBill += itemCost;

        if (itemCount < cart.length) {
            cart[itemCount] = p;
            itemCount++;
        }

        // Using BLUE for a subtle confirmation
        System.out.println(activity9.grocery.Main.BLUE + "   > Added: " + p.getName() + " (P" + itemCost + ")" + activity9.grocery.Main.RESET);
        System.out.println(activity9.grocery.Main.BLUE + "   > Running Total: " + this.currentBill + activity9.grocery.Main.RESET);
    }

    public void printReceipt() {
        // CYAN Border
        System.out.println(activity9.grocery.Main.CYAN + "\n============================================" + activity9.grocery.Main.RESET);
        System.out.println(activity9.grocery.Main.CYAN + "              OFFICIAL RECEIPT              " + activity9.grocery.Main.RESET);
        System.out.println(activity9.grocery.Main.CYAN + "============================================" + activity9.grocery.Main.RESET);
        
        System.out.printf("%-5s %-15s %10s %10s\n", "Qty", "Item", "Price", "Total");
        System.out.println(activity9.grocery.Main.CYAN + "--------------------------------------------" + activity9.grocery.Main.RESET);

        for (int i = 0; i < itemCount; i++) {
            cart[i].printReceiptLine();
        }

        System.out.println(activity9.grocery.Main.CYAN + "--------------------------------------------" + activity9.grocery.Main.RESET);
        System.out.print("GRAND TOTAL:                   ");
        // GREEN Total
        System.out.println(activity9.grocery.Main.GREEN + String.format("%10.2f", this.currentBill) + activity9.grocery.Main.RESET);
        System.out.println(activity9.grocery.Main.CYAN + "============================================" + Main.RESET);
    }

    public double getBillTotal() {
        return this.currentBill;
    }

    public double processPayment(double payment) {
        if (payment >= this.currentBill) {
            return payment - this.currentBill;
        } else {
            return -1.0;
        }
    }
}