package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.model.Account;
import com.techelevator.tebucks.model.Transfer;
import com.techelevator.tebucks.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

public class JdbcTransferDaoTests extends BaseDaoTests {

    protected static final User USER_1 = new User(1, "user1", "user1", "USER");
    protected static final User USER_2 = new User(2, "user2", "user2", "USER");
    private static final User USER_3 = new User(3, "user3", "user3", "USER");

    protected static final Account ACCOUNT_1 = new Account(1,1, BigDecimal.valueOf(1000));
    protected static final Account ACCOUNT_2 = new Account(2, 2, BigDecimal.valueOf(1000));
    private static final Account ACCOUNT_3 = new Account(3, 3, BigDecimal.valueOf(2000));


    protected static final Transfer TRANSFER_1 = new Transfer(1,"Send", "Approved", USER_3, USER_2, BigDecimal.valueOf(1100));
    protected static final Transfer TRANSFER_2 = new Transfer(2,"Send", "Approved", USER_1, USER_2, BigDecimal.valueOf(500));
    private static final Transfer TRANSFER_3 = new Transfer(3,"Send", "Approved", USER_3, USER_1, BigDecimal.valueOf(400));

    private JdbcTransferDao sut;
    private JdbcUserDao userDao;


    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcTransferDao(jdbcTemplate);
    }

    @Test

    public void getTransfer_returns_transfer_by_given_id(){

        int transferId = TRANSFER_2.getTransferId();

        Transfer actual = sut.getTransfer(transferId);

        Assert.assertNotNull(actual);
        assertTransfersMatch(TRANSFER_2,actual);


    }











    private void assertTransfersMatch(Transfer expected, Transfer actual) {
        Assert.assertEquals(expected.getUserFrom(), actual.getUserFrom());
        Assert.assertEquals(expected.getUserTo(), actual.getUserTo());
        Assert.assertEquals(expected.getAmount(), actual.getAmount());
        Assert.assertEquals(expected.getTransferStatus(), actual.getTransferStatus());
        Assert.assertEquals(expected.getTransferId(), actual.getTransferId());
        Assert.assertEquals(expected.getTransferType(), actual.getTransferType());
    }
}

