package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.model.Account;
import com.techelevator.tebucks.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private UserDao userDao;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    @Override
    public Transfer getTransfer(int transferId) {

        Transfer transfer = null;

        String sql = "SELECT transfer_id, transfer_type, transfer_status, user_from, user_to, amount FROM transfer WHERE user_id = ?;";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferId);

        if (result.next()){
            transfer = mapRowToTransfer(result);
        }
        return transfer;
    }

    @Override
    public Transfer makeTransfer(int fromUserId, int toUserId, BigDecimal amount) {

        Transfer transfer = null;

        String sql = "INSERT INTO transfer (transfer_type, transfer_status, user_from, user_to, amount) VALUES (?,?,?,?,?) RETURNING transfer_id;";

        Integer transferId = jdbcTemplate.queryForObject(sql, Integer.class, "send", "Approved", transfer.getUserFrom(), transfer.getUserTo(), transfer.getAmount());


        return getTransfer(transferId);

    }

    @Override
    public List<Transfer> getAllTransfersByAccountId(int accountId) {
        List<Transfer> allTransfers = new ArrayList<>();

        String sql = "SELECT * FROM transfer WHERE account_id = ?;";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, accountId);

        while (result.next()) {
            Transfer transfer = mapRowToTransfer(result);
            allTransfers.add(transfer);
        }

        return allTransfers;
    }

    private Transfer mapRowToTransfer(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rs.getInt("transfer_id"));
        transfer.setTransferType(rs.getString("transfer_type"));
        transfer.setTransferStatus(rs.getString("transfer_status"));
        transfer.setUserFrom(userDao.getUserById(rs.getInt("user_from")));
        transfer.setUserTo(userDao.getUserById(rs.getInt("user_to")));
        transfer.setAmount(rs.getBigDecimal("amount"));

        return transfer;
    }
}
