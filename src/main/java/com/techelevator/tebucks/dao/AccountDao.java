package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.model.Account;

import javax.print.DocFlavor;
import java.math.BigDecimal;

public interface AccountDao {
    BigDecimal getAccountBalance(int userId);
    void updateReceiverBalance(int userTo, BigDecimal amount);
    void updateSenderBalance(int userFrom, BigDecimal amount);
<<<<<<< HEAD
    int getAccountById(int accountId);
=======
    int getAccountIdByUserId(int userId);
>>>>>>> 52c4de9d8e7d9148e650e6123dbf465cea177031
}
