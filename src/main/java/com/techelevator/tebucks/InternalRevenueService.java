package com.techelevator.tebucks;

import com.techelevator.tebucks.model.Transfer;
import com.techelevator.tebucks.model.TxLogDto;

public interface InternalRevenueService {

    TxLogDto logTransfer(TxLogDto txLogDto);
}
