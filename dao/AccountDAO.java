package dao;

import model.Account;
import util.DBUtil;

import java.sql.*;
import java.time.LocalDate;

public class AccountDAO {
    public boolean createAccount(Account acc) throws SQLException {
        String sql = "INSERT INTO bankAccounts (AccountNumber, CustomerID, AccountType, Balance, Status, OpeningDate) VALUES (?,?,?,?,?,?,?)";

        try(Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            // execute the statement
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
}
