package service;

import dao.AccountDAO;
import dao.CustomerDAO;
import dao.TransactionDAO;
import exception.AccountClosedException;
import exception.AccountNotFoundException;
import exception.InvalidAmountException;
import model.Account;
import model.Customer;
import model.Transaction;

import java.sql.SQLException;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class BankService {
    private CustomerDAO customerDAO = new CustomerDAO();
    private AccountDAO accountDAO = new AccountDAO();
    private TransactionDAO transactionDAO = new TransactionDAO();

    long generateAccountNumber(){

        long min = 1000000000L;
        long max = 9999999999L;
        return min + (long)(Math.random()*(max-min));
    }

    public void createAccount(String fname, String lname, String email, String pno, String address){
        try{
            // create a new customer record
            Customer customer = new Customer(fname,lname,email,pno,address);

            // then get the customerID
            int customerID = customerDAO.createCustomer(customer);
            if(customerID == -1){
                System.out.println("Failed to create customer entry.");
                return;
            }

            // use the customerID to create a new record in BankAccounts table.
            long accNumber = this.generateAccountNumber();
            Account account = new Account(accNumber,customerID,"Savings",0.0,"Active", LocalDate.now());
            if(accountDAO.createAccount(account)){
                System.out.println("Account created successfully. \n your account number : "+accNumber);
            }else{
                System.out.println("Failed to create account");
            }

        }catch (SQLException e){
            System.out.println("Error : "+e.getMessage());
        }
    }

    public void closeAccount(long accountNumber){
        try{
            // search the specified bank account.
            Account acc = accountDAO.getAccount(accountNumber);
            if(acc == null){
                throw new AccountNotFoundException("Account does not exist at GG bank.");
            }

            // Bank account already exist. But it's already closed.
            if(acc.getStatus().equalsIgnoreCase("closed")){
                throw new AccountClosedException("Account already closed.");
            }

            // Close the account.
            if(accountDAO.closeAccount(accountNumber)){
                System.out.println("Account closed successfully.");
            }else{
                System.out.println("Failed to close bank account.");
            }

        }catch (AccountNotFoundException | AccountClosedException | SQLException e){
            System.out.println("Error : "+e.getMessage());
        }

    }


    public void withdraw(long accNumber,double amount){
        try{
            if(amount <= 0){
                throw new InvalidAmountException("Amount must be greater than 0.");
            }
            // search the specified bank account.
            Account acc = accountDAO.getAccount(accNumber);
            if(acc == null){
                throw new AccountNotFoundException("Account does not exist at GG Bank.");
            }

            // bank account already exist. BUT it's already closed.
            if(acc.getStatus().equalsIgnoreCase("closed")){
                throw new AccountClosedException("Account already closed.");
            }

            // 2 account types  - savings , current.
            // savings account. overdraft limit - 0
            // current account overdraft limit - 5000 , bank balance: -5000
            double overdraftLimit = 0;
            double currentAccountBalance = acc.getBalance();

            if(acc.getAccountType().equalsIgnoreCase("current")){
                overdraftLimit = -5000;
            }

            if(currentAccountBalance - amount < overdraftLimit){
                throw new InvalidAmountException("Withdrawal amount exceeds the permitted limits. \nCannot initiate transactions.");
            }else{
                // first update the property - balance - in the Account object.
                acc.setBalance((acc.getBalance() - amount));

                // call AccountDAO object to update the balance field in the DB by using the Account objects.
                accountDAO.updateBalance(acc);

                Transaction t = new Transaction(accNumber, "Withdrawal", amount, LocalDateTime.now(),0,"Withdrawal from account");
                transactionDAO.addTransaction(t);

                // receipt generation

                // Completion of balance update message
                System.out.println("Withdrawal successful!\nWithdrawal amount : "+amount+"\nAvailable balance : "+acc.getBalance());
            }
        }catch (InvalidAmountException | AccountNotFoundException | AccountClosedException | SQLException e){
            System.out.println("Error : "+e.getMessage());
        }
    }

    public void deposit(long accNumber, double amount){
        try{
            if(amount <= 0){
                throw new InvalidAmountException("Amount must be greater than 0.");
            }

            // search the specified bank account.
            Account acc = accountDAO.getAccount(accNumber);
            if(acc == null){
                throw new exception.AccountNotFoundException("Account does not exist at GG bank.");
            }

            // bank account already exist. But it's already closed.
            if(acc.getStatus().equalsIgnoreCase("closed")){
                throw new AccountClosedException("Account alreday closed.");
            }

            acc.setBalance(acc.getBalance() + amount);
            accountDAO.updateBalance(acc);
            Transaction t = new Transaction(accNumber,"Deposit",amount,LocalDateTime.now(),0,"Deposit to account");
            transactionDAO.addTransaction(t);

            // receipt generation

            // completion of balance update message
            System.out.println("Deposit successful!\nDeposit amount : "+amount+"\nAvailable balance : "+acc.getBalance());

        }catch (InvalidAmountException | exception.AccountNotFoundException | AccountClosedException | SQLException e){
            System.out.println("Error : "+e.getMessage());
        }
    }

    public void transfer(long accNumber, long recAccNumber, double amount){

        // gets senders account
        // get receivers account
        // check if these accounts are valid or not.
        // check if specified withdrawal amount is less than the permitted limits.
        // if YES, then initiate transaction.
        // deduct from senders account, create a transaction.
        // add amount to receivers account, create a transaction

    }

    public void transactionHistory(long accNumber){

    }

    public void accountDetails(long accNumber){
        try {
            Account acc = accountDAO.getAccount(accNumber);
            if (acc == null) {
                throw new exception.AccountNotFoundException("Account does not exist at GG Bank.");
            }
            System.out.println("=====================================");
            System.out.println("\t\t\tACCOUNT DETAILS");
            System.out.println("=====================================");
            System.out.println("Account Number       : " + acc.getAccountNumber());
            System.out.println("Account Type         : " + acc.getAccountType());
            System.out.println("Account Status       : " + acc.getStatus());
            System.out.println("Account opening date : " + acc.getOpeningDate());
            System.out.println("Account Balance      : " + acc.getBalance());
            System.out.println("=====================================");
        } catch(exception.AccountNotFoundException | SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void updateCustomerDetails(String fname,String lname,String email,String pho,String address ){

    }
}
