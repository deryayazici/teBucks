package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.model.NewTransferDto;
import com.techelevator.tebucks.model.Transfer;

import java.math.BigDecimal;

public interface TransferDao {

    Transfer getTransfer(int userId);

    Transfer makeTransfer(NewTransferDto newTransferDto);
}
