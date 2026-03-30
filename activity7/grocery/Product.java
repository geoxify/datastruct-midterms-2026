package activity7.grocery;

public class Product {
    private String name;
    private double price;
    private double quantity;

    public Product(String name, double price, double quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public double getLineTotal() {
        return this.price * this.quantity;
    }

    public String getName() {
        return name;
    }

    public void printReceiptLine() {
        // Using %-5s for quantity to prevent rounding issues like 1.5 -> 2
        System.out.printf("%-5s %-15s %10.2f %10.2f\n",
                this.quantity, this.name, this.price, this.getLineTotal());
    }
}