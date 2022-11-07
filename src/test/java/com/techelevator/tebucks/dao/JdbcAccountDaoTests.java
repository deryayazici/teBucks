package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.model.Account;
import com.techelevator.tebucks.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

public class JdbcAccountDaoTests extends BaseDaoTests{
//    protected static final User USER_1 = new User(1, "user1", "user1", "USER");
//    protected static final User USER_2 = new User(2, "user2", "user2", "USER");
//    private static final User USER_3 = new User(3, "user3", "user3", "USER");
//
//    protected static final Account ACCOUNT_1 = new Account(1,1, BigDecimal.valueOf(1000));
//    protected static final Account ACCOUNT_2 = new Account(2, 2, BigDecimal.valueOf(1000));
//    private static final Account ACCOUNT = new Account(3, 3, BigDecimal.valueOf(2000));
//
//
//    private JdbcAccountDao sut;



    protected static final Account ACCOUNT_1 = new Account(1, 1, new BigDecimal(1000.00));

    private JdbcAccountDao sut;


    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcAccountDao(jdbcTemplate);
    }

    // Happy paths

    @Test
    public void getAccountBalance_returns_correct_balance() {
        BigDecimal actualBalance = sut.getAccountBalance(ACCOUNT_1.getAccountId());

        Assert.assertEquals(ACCOUNT_1.getBalance(), actualBalance);
    }

    @Test
    public void getAccountIdByUserId_returns_correct_accountId() {
        int actualId = sut.getAccountIdByUserId(ACCOUNT_1.getUserId());

        Assert.assertEquals(ACCOUNT_1.getAccountId(), actualId);
    }

    // Unhappy paths

    @Test
    public void getAccountBalance_returns_null_given_invalid_userId() {
        BigDecimal actualBalance = sut.getAccountBalance(-1);

        Assert.assertNull(actualBalance);
    }

    @Test
    public void getAccountIdByUserId_returns_null_given_invalid_userId() {
        int actualId = sut.getAccountIdByUserId(-1);

        Assert.assertEquals(0,actualId);
    }
}
