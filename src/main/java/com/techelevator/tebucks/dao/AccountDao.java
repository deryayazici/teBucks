package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.model.Account;

import javax.print.DocFlavor;
import java.math.BigDecimal;

public interface AccountDao {
    BigDecimal getAccountBalance(int userId);
    void updateReceiverBalance(int userTo, BigDecimal amount);
    void updateSenderBalance(int userFrom, BigDecimal amount);
    int getAccountById(int accountId);
}
