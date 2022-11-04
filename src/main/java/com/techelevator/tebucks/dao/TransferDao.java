package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.model.NewTransferDto;
import com.techelevator.tebucks.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    Transfer getTransfer(int userId);

    Transfer makeTransfer(int fromUserId, int toUserId, BigDecimal amount);

    List<Transfer> getAllTransfersByAccountId(int accountId);
}
