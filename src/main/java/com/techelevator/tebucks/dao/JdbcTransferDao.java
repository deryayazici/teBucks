package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.model.Account;
import com.techelevator.tebucks.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Component
public class JdbcTransferDao implements TransferDao{
    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    @Override
    public Transfer getTransfer(int userId) {

        Transfer transfer = null;

        String sql = "SELECT transfer_id, transfer_type, transfer_status, user_from, user_to, amount FROM transfer WHERE user_id = ?;";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);

        if (result.next()){
            transfer = mapRowToTransfer(result);
        }
        return transfer;
    }

    public Transfer makeTransfer(int fromUserId, int toUserId, BigDecimal amount) {

        Transfer transfer = null;

        String sql = "INSERT INTO transfer (transfer_type, transfer_status, user_from, user_to, amount) VALUES (?,?,?,?,?) RETURNING transfer_id;";

        Integer transferId = jdbcTemplate.queryForObject(sql, Integer.class, transfer.getTransferType(), transfer.getTransferStatus(), transfer.getUserFrom(), transfer.getUserTo(), transfer.getAmount());


        return getTransfer(transferId);

    }

    private Transfer mapRowToTransfer(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rs.getInt("transfer_id"));
        transfer.setTransferType(rs.getString("transfer_type"));
        transfer.setTransferStatus(rs.getString("transfer_status"));
        transfer.setUserFrom(rs.getInt("user_from"));
        transfer.setUserTo(rs.getInt("user_to"));
        transfer.setAmount(rs.getBigDecimal("amount"));

        return transfer;
    }
}
