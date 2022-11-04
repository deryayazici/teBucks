package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.model.NewTransferDto;
import com.techelevator.tebucks.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    Transfer getTransfer(int transferId);

<<<<<<< HEAD
    Transfer makeTransfer(int fromUserId, int toUserId, BigDecimal amount);

    List<Transfer> getAllTransfersByAccountId(int accountId);
=======
    Transfer makeTransfer(NewTransferDto newTransferDto);
    List<Transfer> getAllTransfersByUserId(int userId);
>>>>>>> 52c4de9d8e7d9148e650e6123dbf465cea177031
}
