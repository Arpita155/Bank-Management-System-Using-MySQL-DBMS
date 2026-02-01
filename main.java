import service.BankService;

import java.util.Scanner;

public class main {
    public static void main(String[] args) {

        BankService service = new BankService();
        Scanner sc = new Scanner(System.in);
        int choice;

        do{
            System.out.println("\n=======================================");
            System.out.println("Welcome to GG bank");
            System.out.println("=======================================");
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
                case 1:
                    System.out.println("Enter account holder's first name : ");
                    String fname = sc.nextLine();
                    System.out.println("Enter account holder's last name : ");
                    String lname = sc.nextLine();
                    System.out.println("Enter account holder's email : ");
                    String email = sc.nextLine();
                    System.out.println("Enter account holder's phone number : ");
                    String pho = sc.nextLine();
                    System.out.println("Enter account holder's address : ");
                    String address = sc.nextLine();
                    service.createAccount(fname,lname,email,pho,address);
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
                    service.deposite(accNumber,amount);
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
                    System.out.println("the following options can be uploaded. choose one of them.");
                    System.out.println("1. First name ");
                    System.out.println("2. last name ");
                    System.out.println("3. Email ");
                    System.out.println("4. Phone number ");
                    System.out.println("5. address ");
                    System.out.println("Enter your choice ");
                    int ch = sc.nextInt();
                    switch (ch){
                        case 1:
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                        case 4:
                            break;
                        case 5:
                            break;
                        default:
                            System.out.println("");
                    }
                case 9:
                    break;
                default:
                    System.out.println("Invalid selection");
                    System.out.println("Please enter correct option");
            }
        }while (choice != 9);

    }
}
