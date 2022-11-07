package com.techelevator.tebucks.model;

import java.math.BigDecimal;

public class Transfer {
    private int transferId;
    private String transferType;
    private String transferStatus;
    private User userFrom;
    private User userTo;
    private BigDecimal amount;

    public static final String TRANSFER_TYPE_REQUEST = "Request";
    public static final String TRANSFER_TYPE_SEND = "Send";
    public static final String TRANSFER_STATUS_PENDING = "Pending";
    public static final String TRANSFER_STATUS_APPROVED = "Approved";
    public static final String TRANSFER_STATUS_REJECTED = "Rejected";

    public Transfer(){

    }

    public Transfer(int transferId, String transferType, String transferStatus, User userFrom, User userTo, BigDecimal amount) {
        this.transferId = transferId;
        this.transferType = transferType;
        this.transferStatus = transferStatus;
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.amount = amount;
    }

    public int getTransferId() {
        return transferId;
    }

    public String getTransferType() {
        return transferType;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public User getUserFrom() {
        return userFrom;
    }

    public User getUserTo() {
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

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    public void setUserTo(User userTo) {
        this.userTo = userTo;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public boolean isApproved() {
        if (transferStatus.equalsIgnoreCase("Approved")) {
            return true;
        }
        return false;
        // TODO
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
        if (transferType.equalsIgnoreCase("Send")) {
            return true; // TODO
        }
        return false;
    }
}
