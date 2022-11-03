package com.techelevator.tebucks.model;

import java.math.BigDecimal;

public class Transfer {
    private int transferId;
    private String transferType;
    private String transferStatus;
    private int userFrom;
    private int userTo;
    private BigDecimal amount;

    public static final String TRANSFER_TYPE_REQUEST = "Request";
    public static final String TRANSFER_TYPE_SEND = "Send";
    public static final String TRANSFER_STATUS_PENDING = "Pending";
    public static final String TRANSFER_STATUS_APPROVED = "Approved";
    public static final String TRANSFER_STATUS_REJECTED = "Rejected";

	public int getTransferId() {
        return transferId;
    }

    public String getTransferType() {
        return transferType;
    }

    public String getTransferStatus() {
        return transferStatus;
    }
    
    public int getUserFrom() {
    	return userFrom;
    }
    
    public int getUserTo() {
    	return userTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    public void setUserFrom(int userFrom) {
        this.userFrom = userFrom;
    }

    public void setUserTo(int userTo) {
        this.userTo = userTo;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public boolean isApproved() {

		return false; // TODO
	}
	
	public boolean isRejected() {
		return false; // TODO
	}
	
	public boolean isPending() {
		return false; // TODO
	}
	
	public boolean isRequestType() {
		return false; // TODO
	}
	
	public boolean isSendType() {
		return false; // TODO
	}
}
