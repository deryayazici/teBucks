package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.model.Account;
import com.techelevator.tebucks.model.NewTransferDto;
import com.techelevator.tebucks.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Component
public class JdbcTransferDao implements TransferDao{
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private UserDao userDao;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    @Override
    public Transfer getTransfer(int transferId) {

        Transfer transfer = null;

        String sql = "SELECT transfer_id, transfer_type, transfer_status, user_from, user_to, amount FROM transfer WHERE transfer_id = ?;";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferId);

        if (result.next()){
            transfer = mapRowToTransfer(result);
        }
        return transfer;
    }
    @Override
    public Transfer makeTransfer(NewTransferDto newTransferDto) {

        Transfer transfer = null;

        String sql = "INSERT INTO transfer (transfer_type, transfer_status, user_from, user_to, amount) VALUES (?,?,?,?,?) RETURNING transfer_id;";

        Integer transferId = jdbcTemplate.queryForObject(sql, Integer.class, "Send", "Approved", newTransferDto.getUserFrom(), newTransferDto.getUserTo(), newTransferDto.getAmount());


        return getTransfer(transferId);

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
