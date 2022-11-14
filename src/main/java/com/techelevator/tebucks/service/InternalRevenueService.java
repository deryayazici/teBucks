package com.techelevator.tebucks.service;

import com.techelevator.tebucks.model.TxLogDto;

public interface InternalRevenueService {

    TxLogDto logTransfer(TxLogDto txLogDto);
}
