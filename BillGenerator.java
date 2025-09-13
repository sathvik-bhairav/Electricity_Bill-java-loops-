import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class ElectricityBill {
    private String customerName;
    private String customerAddress;
    private double units;
    private double amount;
    private double slab1Amount, slab2Amount, slab3Amount;
    private final double fixedCharge = 50;
    private final double taxRate = 0.10; 

    public ElectricityBill(String name, String address, double units) {
        this.customerName = name;
        this.customerAddress = address;
        this.units = units;
    }

    
    public void calculateBill() {
        slab1Amount = slab2Amount = slab3Amount = 0;

        if (units <= 100) {
            slab1Amount = units * 1.5;
        } else if (units <= 300) {
            slab1Amount = 100 * 1.5;
            slab2Amount = (units - 100) * 3.0;
        } else {
            slab1Amount = 100 * 1.5;
            slab2Amount = 200 * 3.0;
            slab3Amount = (units - 300) * 5.0;
        }

        amount = slab1Amount + slab2Amount + slab3Amount + fixedCharge;
        amount += amount * taxRate;
    }

    
    public void printBill() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dateTime = dtf.format(LocalDateTime.now());

        String line = "==============================================";
        System.out.println(line);
        System.out.println("              ELECTRICITY BILL                 ");
        System.out.println(line);
        System.out.printf("Date: %s\n", dateTime);
        System.out.printf("Customer Name   : %s\n", customerName);
        System.out.printf("Customer Address: %s\n", customerAddress);
        System.out.println(line);
        System.out.printf("Units Consumed  : %.2f units\n", units);
        System.out.println(line);
        System.out.println("Slab-wise Charges:");
        System.out.printf(" 0-100 units @ ₹1.5  : ₹%.2f\n", slab1Amount);
        System.out.printf(" 101-300 units @ ₹3.0: ₹%.2f\n", slab2Amount);
        System.out.printf(" >300 units @ ₹5.0   : ₹%.2f\n", slab3Amount);
        System.out.printf("Fixed Charge          : ₹%.2f\n", fixedCharge);
        System.out.printf("Tax (10%%) included    : ₹%.2f\n", (amount - (slab1Amount + slab2Amount + slab3Amount + fixedCharge)));
        System.out.println(line);
        System.out.printf("TOTAL AMOUNT PAYABLE  : ₹%.2f\n", amount);
        System.out.println(line);
        System.out.println("          THANK YOU FOR USING OUR SERVICE     ");
        System.out.println(line);
    }
}

public class BillGenerator {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("======= Welcome to the Electricity Bill Generator =======\n");

        boolean continueBilling = true;

        while (continueBilling) {
            String name = inputCustomerName();
            String address = inputCustomerAddress();
            double units = inputUnitsConsumed();

            ElectricityBill bill = new ElectricityBill(name, address, units);
            bill.calculateBill();
            bill.printBill();

            continueBilling = askToContinue();
        }

        System.out.println("\nExiting... Have a nice day!");
        scanner.close();
    }

    private static String inputCustomerName() {
        System.out.print("Enter Customer Name: ");
        return scanner.nextLine();
    }

    private static String inputCustomerAddress() {
        System.out.print("Enter Customer Address: ");
        return scanner.nextLine();
    }

    private static double inputUnitsConsumed() {
        double units = -1;
        do {
            System.out.print("Enter Units Consumed: ");
            while (!scanner.hasNextDouble()) {
                System.out.print("Invalid input. Please enter a numeric value for units: ");
                scanner.next();
            }
            units = scanner.nextDouble();
            scanner.nextLine();  

            if (units < 0) {
                System.out.println("Units cannot be negative. Please enter a valid number.");
            }
        } while (units < 0);

        return units;
    }

    private static boolean askToContinue() {
        System.out.print("\nDo you want to generate another bill? (Y/N): ");
        String choice = scanner.nextLine().trim().toUpperCase();
        return choice.equals("Y");
    }
}
