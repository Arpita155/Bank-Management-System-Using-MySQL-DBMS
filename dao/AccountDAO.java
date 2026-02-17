package dao;

import exception.AccountNotFoundException;
import model.Account;
import util.DBUtil;

import java.sql.*;
import java.time.LocalDate;

public class AccountDAO {
    public boolean createAccount(Account acc) throws SQLException {
        String sql = "INSERT INTO bankaccounts (AccountNumber, CustomerID, AccountType, Balance, Status, OpeningDate) VALUES (?,?,?,?,?,?)";

        try(Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setLong(1,acc.getAccountNumber());
            ps.setInt(2,acc.getCustomerId());
            ps.setString(3,acc.getAccountType());
            ps.setDouble(4,acc.getBalance());
            ps.setString(5,acc.getStatus());
            ps.setDate(6, Date.valueOf(LocalDate.now()));
            int updatedRows = ps.executeUpdate();
            if(updatedRows == 0){
                return false;
            }
        }
        return true;
    }

    public Account getAccount(long accNumber) throws SQLException, AccountNotFoundException {
        // sql query preparation
        String sql = "SELECT * FROM bankaccounts WHERE AccountNumber = ?";   // reading the data. I am not modifying anything

        // create connection. prepare the sql statement for execution.
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);)
        {
            ps.setLong(1,accNumber);

            // execute the query
            ResultSet record = ps.executeQuery();   // this will store the ROW returned by the DBMS upon execution of SQL statement.

            // extract details from the ResultSet and create an object of Account class.
            boolean flag = record.next();
            if(!flag){
                throw new AccountNotFoundException("Account does not exist at GG bank.");
            }
            Account obj = new Account(
                    record.getLong("AccountNumber"),
                    record.getInt("CustomerID"),
                    record.getString("AccountType"),
                    record.getDouble("Balance"),
                    record.getString("Status"),
                    record.getDate("OpeningDate").toLocalDate());

            return obj;
        }
    }

    public boolean closeAccount(long accNumber) throws SQLException{
        String sql = "UPDATE bankaccounts SET Status = 'Closed' WHERE AccountNumber = ?";
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);)
        {
            ps.setLong(1,accNumber);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public void updateBalance(Account acc)throws SQLException{
        String sql = "UPDATE bankaccounts SET Balance = ? WHERE AccountNumber = ?";
        try(Connection conn = DBUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);)
        {
            ps.setDouble(1,acc.getBalance());
            ps.setLong(2,acc.getAccountNumber());

            ps.executeUpdate();
        }
    }

    public boolean transactionUpdateBalance(Account acc, Connection conn) throws SQLException{
        String sql = "UPDATE bankaccounts SET Balance = ? WHERE AccountNumber = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setDouble(1,acc.getBalance());
            ps.setLong(2,acc.getAccountNumber());

            return ps.executeUpdate() > 0;
        }
    }
}
