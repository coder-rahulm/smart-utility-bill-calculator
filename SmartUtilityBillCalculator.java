import java.util.Scanner;

// Base class for all utility bills
abstract class Bill {
    protected String customerName;
    protected double currentReading;
    protected double previousReading;
    protected double unitsConsumed;
    
    public Bill(String customerName, double currentReading, double previousReading) {
        this.customerName = customerName;
        this.currentReading = currentReading;
        this.previousReading = previousReading;
        this.unitsConsumed = currentReading - previousReading;
    }
    
    public abstract double calculateBill();
    public abstract void displayBillDetails();
    
    public double getUnitsConsumed() {
        return unitsConsumed;
    }
}

// Residential Electricity Bill
class ResidentialElectricityBill extends Bill {
    private static final double SLAB1_RATE = 3.50;  // 0-100 units
    private static final double SLAB2_RATE = 5.00;  // 101-200 units
    private static final double SLAB3_RATE = 7.00;  // 201-400 units
    private static final double SLAB4_RATE = 9.50;  // 401+ units
    private static final double FIXED_CHARGE = 50.00;
    private static final double TAX_RATE = 0.08;    // 8% tax
    
    public ResidentialElectricityBill(String customerName, double currentReading, double previousReading) {
        super(customerName, currentReading, previousReading);
    }
    
    @Override
    public double calculateBill() {
        double billAmount = FIXED_CHARGE;
        
        if (unitsConsumed <= 100) {
            billAmount += unitsConsumed * SLAB1_RATE;
        } else if (unitsConsumed <= 200) {
            billAmount += (100 * SLAB1_RATE) + ((unitsConsumed - 100) * SLAB2_RATE);
        } else if (unitsConsumed <= 400) {
            billAmount += (100 * SLAB1_RATE) + (100 * SLAB2_RATE) + ((unitsConsumed - 200) * SLAB3_RATE);
        } else {
            billAmount += (100 * SLAB1_RATE) + (100 * SLAB2_RATE) + (200 * SLAB3_RATE) + ((unitsConsumed - 400) * SLAB4_RATE);
        }
        
        // Add tax
        billAmount += billAmount * TAX_RATE;
        return Math.round(billAmount * 100.0) / 100.0;
    }
    
    @Override
    public void displayBillDetails() {
        System.out.println("\n=== RESIDENTIAL ELECTRICITY BILL ===");
        System.out.println("Customer: " + customerName);
        System.out.printf("Units Consumed: %.2f\n", unitsConsumed);
        System.out.printf("Total Bill Amount: ₹%.2f\n", calculateBill());
        System.out.println("=====================================");
    }
}

// Commercial Electricity Bill
class CommercialElectricityBill extends Bill {
    private static final double SLAB1_RATE = 6.50;  // 0-200 units
    private static final double SLAB2_RATE = 8.50;  // 201-500 units
    private static final double SLAB3_RATE = 11.00; // 501+ units
    private static final double FIXED_CHARGE = 150.00;
    private static final double TAX_RATE = 0.12;    // 12% tax
    
    public CommercialElectricityBill(String customerName, double currentReading, double previousReading) {
        super(customerName, currentReading, previousReading);
    }
    
    @Override
    public double calculateBill() {
        double billAmount = FIXED_CHARGE;
        
        if (unitsConsumed <= 200) {
            billAmount += unitsConsumed * SLAB1_RATE;
        } else if (unitsConsumed <= 500) {
            billAmount += (200 * SLAB1_RATE) + ((unitsConsumed - 200) * SLAB2_RATE);
        } else {
            billAmount += (200 * SLAB1_RATE) + (300 * SLAB2_RATE) + ((unitsConsumed - 500) * SLAB3_RATE);
        }
        
        // Add tax
        billAmount += billAmount * TAX_RATE;
        return Math.round(billAmount * 100.0) / 100.0;
    }
    
    @Override
    public void displayBillDetails() {
        System.out.println("\n=== COMMERCIAL ELECTRICITY BILL ===");
        System.out.println("Customer: " + customerName);
        System.out.printf("Units Consumed: %.2f\n", unitsConsumed);
        System.out.printf("Total Bill Amount: ₹%.2f\n", calculateBill());
        System.out.println("===================================");
    }
}

// Main Utility Bill Calculator
public class SmartUtilityBillCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        
        System.out.println("=== SMART ELECTRICITY BILL CALCULATOR ===");
        System.out.println("This tool helps verify your utility bills accurately!");
        
        do {
            System.out.println("\n1. Residential Bill");
            System.out.println("2. Commercial Bill");
            System.out.println("0. Exit");
            System.out.print("Choose option: ");
            choice = scanner.nextInt();
            
            if (choice == 0) {
                System.out.println("Thank you for using Smart Utility Bill Calculator!");
                break;
            }
            
            if (choice == 1 || choice == 2) {
                System.out.print("Enter Customer Name: ");
                scanner.nextLine(); // consume newline
                String customerName = scanner.nextLine();
                
                System.out.print("Enter Current Meter Reading: ");
                double currentReading = scanner.nextDouble();
                
                System.out.print("Enter Previous Meter Reading: ");
                double previousReading = scanner.nextDouble();
                
                Bill bill;
                if (choice == 1) {
                    bill = new ResidentialElectricityBill(customerName, currentReading, previousReading);
                } else {
                    bill = new CommercialElectricityBill(customerName, currentReading, previousReading);
                }
                
                bill.displayBillDetails();
            } else {
                System.out.println("Invalid option! Please try again.");
            }
            
        } while (choice != 0);
        
        scanner.close();
    }
}