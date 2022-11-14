package com.techelevator.tebucks.dao;

import java.math.BigDecimal;

public interface AccountDao {
    BigDecimal getAccountBalance(int userId);
    void updateReceiverBalance(int userTo, BigDecimal amount);
    void updateSenderBalance(int userFrom, BigDecimal amount);
    int getAccountIdByUserId(int userId);
}
