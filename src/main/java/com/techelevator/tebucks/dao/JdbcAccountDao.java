package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.model.Account;
import com.techelevator.tebucks.model.Transfer;
import com.techelevator.tebucks.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao{

    private final JdbcTemplate jdbcTemplate;
    private AccountDao accountDao;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }


    @Override
    public BigDecimal getAccountBalance(int userId) {
        BigDecimal balance = null;
        String sql="SELECT * FROM account WHERE user_id= ?; ";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);

        while (results.next()) {
            Account account = mapRowToAccount(results);
            balance = account.getBalance();
        }
        return balance;
    }

    @Override
    public void updateReceiverBalance(int userTo, BigDecimal amount) {

        String sql ="UPDATE account SET balance = balance + ? " +
                "WHERE user_id = ? ;";

        jdbcTemplate.update(sql,amount,userTo);
    }

    @Override
    public void updateSenderBalance(int userFrom, BigDecimal amount) {
        String sql ="UPDATE account SET balance = balance - ? " +
                "WHERE user_id = ? ;";

        jdbcTemplate.update(sql,amount,userFrom);

    }

    @Override
    public int getAccountIdByUserId(int userId) {

        String sql= "SELECT account_id FROM account WHERE user_id = ?;";

        Integer accountId = jdbcTemplate.queryForObject(sql,Integer.class,userId);

        return accountId;
    }



    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setAccountId(rs.getInt("account_id"));
        account.setUserId(rs.getInt("user_id"));
        account.setBalance(rs.getBigDecimal("balance"));

        return account;
    }
}
