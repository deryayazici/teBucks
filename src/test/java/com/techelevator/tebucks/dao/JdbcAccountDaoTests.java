package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

public class JdbcAccountDaoTests extends BaseDaoTests{



    protected static final Account ACCOUNT_1 = new Account(1, 1, new BigDecimal(1000.00));

    private JdbcAccountDao sut;


    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcAccountDao(jdbcTemplate);
    }



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
