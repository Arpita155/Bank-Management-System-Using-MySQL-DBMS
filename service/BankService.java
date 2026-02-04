package service;

import dao.AccountDAO;
import dao.CustomerDAO;
import dao.TransactionDAO;
import exception.AccountClosedException;
import model.Account;
import model.Customer;

import javax.security.auth.login.AccountNotFoundException;
import java.sql.SQLException;
import java.time.LocalDate;

public class BankService {
    private CustomerDAO customerDAo = new CustomerDAO();
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
            int customerID = customerDAo.createCustomer(customer);
            if(customerID == -1){
                System.out.println("Failed to create customer entry.");
                return;
            }

            // use the customerID to create a new record in BANKACCOUNT table
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
            AccountDAO.closeAccount(accountNumber);
            acc.setStatus("Closed");

        }catch (AccountNotFoundException | AccountClosedException e){
            System.out.println("Error : "+e.getMessage());
        }



    }


    public void withdraw(long accNumber,double amount){

    }

    public void deposite(long accNumber,double amount){

    }

    public void transfer(long accNumber, long recAccNumber, double amount){

    }

    public void transactionHistory(long accNumber){

    }

    public void accountDetails(long accNumber){

    }
    public void updateCustomerDetails(String fname,String lname, ){

    }
}
