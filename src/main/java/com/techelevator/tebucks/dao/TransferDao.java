package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.model.NewTransferDto;
import com.techelevator.tebucks.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    Transfer getTransfer(int transferId);

    Transfer makeTransfer(NewTransferDto newTransferDto);
    List<Transfer> getAllTransfersByUserId(int userId);
}
