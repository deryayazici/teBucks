package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.model.Account;

import javax.print.DocFlavor;
import java.math.BigDecimal;

public interface AccountDao {
    BigDecimal getAccountBalance(int userId);
    boolean updateReceiverBalance(int userTo, Account userToAccount);
}
