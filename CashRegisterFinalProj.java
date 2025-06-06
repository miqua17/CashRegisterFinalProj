import java.util.*;
import java.util.regex.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.*;

public class CashRegisterFinalProj {

static Scanner scan = new Scanner(System.in);
static ArrayList<String> usernames = new ArrayList<>();
static ArrayList<String> passwords = new ArrayList<>();
static ArrayList<String> products = new ArrayList<>();
static ArrayList<Double> prices = new ArrayList<>();
static ArrayList<Integer> quantity = new ArrayList<>();
static boolean running = true;
static String currentUser = null; 

public static void main(String[] args) {
while (true) {
System.out.println("\n1. Sign Up");
System.out.println("2. Login");
System.out.println("3. Logout");
System.out.print("Choose: ");
int chosen = scan.nextInt();
scan.nextLine();

if (chosen == 1) {
    signUp();
} else if (chosen == 2) {
    login();
} else {
    System.out.println("Goodbye!");
    break;
        }
    }
}

static void signUp() {
Pattern usernamePattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*[\\d])[A-Z-a-z\\d]{5,15}$");
Pattern passwordPattern = Pattern.compile("(?=.*[A-Z])(?=.*\\d)(\\w).{8,20}$");

System.out.print("Enter username (must be alphanumeric, 5-15 chars): ");
String username = scan.nextLine();

Matcher usernameMatcher = usernamePattern.matcher(username);
if (!usernameMatcher.matches()) {
    System.out.println("Invalid username format.");
    return;

}

System.out.print("Enter password (At least 1 UpperCase, At least 1 Digit, 8-20 chars): ");
String password = scan.nextLine();

Matcher passwordMatcher = passwordPattern.matcher(password);
if (!passwordMatcher.matches()) {
    System.out.println("Invalid password format.");
    return;
        
}

usernames.add(username);
passwords.add(password);
System.out.println("Sign up successful!");
    
}

static void login() {
System.out.print("Enter username: ");
String username = scan.nextLine();
System.out.print("Enter password: ");
String password = scan.nextLine();

for (int i = 0; i < usernames.size(); i++) {
if (usernames.get(i).equals(username) && passwords.get(i).equals(password)) {
    System.out.println("Login successful!");
    currentUser = username;
    CashRegisterSystem();
    return;
          
       }
}

System.out.println("The username or password is incorrect.");
  
}

public static void CashRegisterSystem() {
while (running) {
System.out.println("\n===== Cash Register System =====");
System.out.println("1. Add Product");
System.out.println("2. Display Products");
System.out.println("3. Remove Product");
System.out.println("4. Edit Quantity");
System.out.println("5. Checkout");
System.out.println("6. Exit");
System.out.print("Enter your choice: ");
int choose = scan.nextInt();

switch (choose) {
    case 1:
    addProduct();
    break;
    
    case 2:
    displayProducts();
    break;
    
    case 3:
    removeProduct();
    break;
                
    case 4:
    editQuantity();
    break;
                
    case 5:
    checkout();
    break;

    case 6:
    System.out.println("Exiting the system. Goodbye!");
    running = false;
    break;
                
    default:
    System.out.println("Invalid choice. Please try again.\n");
          
        }
    }
}

public static void addProduct() {
while (true) {
System.out.print("Enter the product name: ");
scan.nextLine();
String name = scan.nextLine();
products.add(name);

System.out.print("Enter the price: ");
double price = scan.nextDouble();
prices.add(price);

System.out.print("Enter the quantity: ");
int qty = scan.nextInt();
quantity.add(qty);

System.out.println("Product added successfully!\n");

System.out.print("Do you want to add another product? (y/n): ");
char choice = Character.toLowerCase(scan.next().charAt(0));
if (choice == 'n') {
    break;
        
        }
    }
}

public static void displayProducts() {
if (products.isEmpty()) {
    System.out.println("No products available.\n");
return;
        
}

System.out.println("\nProduct List:");
for (int i = 0; i < products.size(); i++) {
    System.out.println((i + 1) + ". " + products.get(i) + " - PHP" + prices.get(i) + " - Stock: " + quantity.get(i));
        
}

System.out.println();
    
}

public static void removeProduct() {
if (products.isEmpty()) {
System.out.println("No products available to remove.\n");
return;
        
}

displayProducts();
System.out.print("Enter the product number to remove: ");
int productNum = scan.nextInt();

if (productNum < 1 || productNum > products.size()) {
System.out.println("Invalid product number!\n");
return;
       
}

products.remove(productNum - 1);
prices.remove(productNum - 1);
quantity.remove(productNum - 1);
System.out.println("Product removed successfully!\n");
    
}

public static void editQuantity() {
    if (products.isEmpty()) {
        System.out.println("No products available to edit.\n");
        return;
}

displayProducts();
System.out.print("Enter the product number to update quantity: ");
int productNum = scan.nextInt();

if (productNum < 1 || productNum > products.size()) {
    System.out.println("Invalid product number!\n");
    return;
    
}

System.out.print("Enter the new quantity: ");
int newQty = scan.nextInt();

if (newQty < 0) {
    System.out.println("Quantity cannot be negative.\n");
    return;
    
}

quantity.set(productNum - 1, newQty);
System.out.println("Quantity updated successfully!\n");

}

public static void checkout() {
if (products.isEmpty()) {
System.out.println("No products in the cart.\n");
return;
        
}

double totalPrice = 0;
StringBuffer receipt = new StringBuffer();
receipt.append("\n===== Receipt =====\n");

for (int i = 0; i < products.size(); i++) {
double itemTotal = prices.get(i) * quantity.get(i);
totalPrice += itemTotal;
receipt.append(products.get(i))
       .append(" - ")
       .append(quantity.get(i))
       .append(" x PHP")
       .append(prices.get(i))
       .append(" = PHP")
       .append(itemTotal)
       .append("\n");
       
}

receipt.append("---------------------\n");
receipt.append("Total Price: PHP").append(totalPrice).append("\n");
System.out.println(receipt);

System.out.print("Enter payment amount: ");
double payment = scan.nextDouble();

if (payment < totalPrice) {
    System.out.println("Insufficient payment. Transaction canceled.\n");
    return;
        
}

double change = payment - totalPrice;
System.out.println("Payment accepted. Change: PHP" + change + "\n");
System.out.println("Transaction completed!\n");

        
logTransaction(receipt.toString(), totalPrice, payment, change);

        
products.clear();
prices.clear();
quantity.clear();

System.out.print("Do you want to do another transaction? (y/n): ");
char choice = Character.toLowerCase(scan.next().charAt(0));
if (choice == 'n') {
    running = false;
        
    }
}

public static void logTransaction(String receiptDetails, double total, double payment, double change) {
try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.txt", true))) {
LocalDateTime now = LocalDateTime.now();
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

writer.write("Date/Time: " + now.format(formatter));
writer.newLine();
writer.write("Cashier: " + currentUser);
writer.newLine();
writer.write(receiptDetails);
writer.write("Total Payment: PHP" + payment + "\n");
writer.write("Change: PHP" + change + "\n");
writer.write("====================================\n");
writer.newLine();

System.out.println("Transaction logged successfully.");
} catch (IOException e) {
System.out.println("Error logging transaction: ");

        }
    }
}
