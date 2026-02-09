import service.BankService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        BankService service = new BankService();
        Scanner sc = new Scanner(System.in);
        int choice;

        do{
            System.out.println("\n=======================================");
            System.out.println("Welcome to GG bank");
            System.out.println("=========================================");
            System.out.println("Choose one of the following options");
            System.out.println("1. Create new account");
            System.out.println("2. close account");
            System.out.println("3. withdraw money");
            System.out.println("4. Deposit money");
            System.out.println("5. Transfer money to another account");
            System.out.println("6. View transaction history");
            System.out.println("7. View account details");
            System.out.println("8. Update account details");
            System.out.println("9. exit");

            System.out.println("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice){
                case 1:    // create new account
                    System.out.println("Enter account holder's first name : ");
                    String fname = sc.nextLine();
                    System.out.println("Enter account holder's last name : ");
                    String lname = sc.nextLine();
                    System.out.println("Enter account holder's email : ");
                    String email = sc.nextLine();
                    System.out.println("Enter account holder's phone number : ");
                    String pno = sc.nextLine();
                    System.out.println("Enter account holder's address : ");
                    String address = sc.nextLine();
                    service.createAccount(fname,lname,email,pno,address);
                    break;
                case 2:
                    System.out.print("Enter bank account number : ");
                    long accNumber = sc.nextLong();
                    service.closeAccount(accNumber);
                    break;
                case 3:
                    System.out.print("Enter bank account number : ");
                    accNumber = sc.nextLong();
                    System.out.print("Enter the amount to withdraw");
                    double amount = sc.nextDouble();
                    service.withdraw(accNumber,amount);
                    break;
                case 4:
                    System.out.print("Enter bank account number : ");
                    accNumber = sc.nextLong();
                    System.out.print("Enter the amount to deposit");
                    amount = sc.nextDouble();
                    service.deposit(accNumber,amount);
                    break;
                case 5:
                    System.out.println("Enter your bank account number : ");
                    accNumber = sc.nextLong();
                    System.out.println("Enter receiver's bank account number");
                    long recAccNumber = sc.nextLong();
                    System.out.println("Enter amount ");
                    amount = sc.nextDouble();
                    service.transfer(accNumber,recAccNumber,amount);
                    break;
                case 6:
                    System.out.println("Enter your bank account number : ");
                    accNumber = sc.nextLong();
                    service.transactionHistory(accNumber);
                    break;
                case 7:
                    System.out.println("Enter your bank account number : ");
                    accNumber = sc.nextLong();
                    service.accountDetails(accNumber);
                    break;
                case 8:
                    // we have to take account number
                    System.out.print("Enter first name : ");
                    fname = sc.nextLine();
                    System.out.print("Enter last name : ");
                    lname = sc.nextLine();
                    System.out.print("Enter email : ");
                    email = sc.nextLine();
                    System.out.print("Enter phone number : ");
                    pno = sc.nextLine();
                    System.out.print("Enter address : ");
                    address = sc.nextLine();
                    service.updateCustomerDetails(fname,lname,email,pno,address);

                case 9:
                    System.out.println("Thank you for visiting GG bank!\nHave a wonderful day ahead.");
                    System.out.println();
                    System.out.println("=====================================");
                    break;
                default:
                    System.out.println("Invalid selection");
                    System.out.println("Please enter correct option");
            }
        }while (choice != 9);

    }
}
