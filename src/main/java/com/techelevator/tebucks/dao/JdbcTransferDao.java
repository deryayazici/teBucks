package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.AuthenticationService;
import com.techelevator.tebucks.RestInternalRevenueService;
import com.techelevator.tebucks.model.Account;
import com.techelevator.tebucks.model.NewTransferDto;
import com.techelevator.tebucks.model.Transfer;
import com.techelevator.tebucks.model.TxLogDto;
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
    private UserDao userDao;
    private AccountDao accountDao;

    @Autowired
   private AuthenticationService authenticationService;

    @Autowired
    private RestInternalRevenueService restInternalRevenueService;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    @Override
    public Transfer getTransfer(int transferId) {

        Transfer transfer = null;

        String sql = "SELECT transfer_id, transfer_type, transfer_status, user_from, user_to, amount FROM transfer WHERE transfer_id = ?;";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferId);

        if (result.next()) {
            transfer = mapRowToTransfer(result);

            return transfer;
        }return null;

    }

    @Override
    public Transfer makeTransfer(NewTransferDto newTransferDto) {

        Transfer transfer = null;

        String sql = "INSERT INTO transfer (transfer_type, transfer_status, user_from, user_to, amount) VALUES (?,?,?,?,?) RETURNING transfer_id;";

        Integer transferId = jdbcTemplate.queryForObject(sql, Integer.class, "Send", "Approved", newTransferDto.getUserFrom(), newTransferDto.getUserTo(), newTransferDto.getAmount());

        transfer = getTransfer(transferId);


        BigDecimal oneThousand = new BigDecimal("1000");

        if (newTransferDto.getAmount().compareTo(oneThousand) > 0) {

            TxLogDto transferGt = new TxLogDto();
            transferGt.setAccount_from(transfer.getUserFrom().getUsername());
            transferGt.setAccount_to(transfer.getUserTo().getUsername());
            transferGt.setDescription(transfer.getUserFrom().getUsername() + " transferred more than $1000.");
            transferGt.setAmount(transfer.getAmount().doubleValue());

            restInternalRevenueService.setAuthToken(authenticationService.login("Team09", "password"));
            restInternalRevenueService.logTransfer(transferGt);

        }
        return transfer;
    }
//
//    public void logOverdrafts(NewTransferDto newTransferDto) {
//        TxLogDto transferGt = new TxLogDto();
//        transferGt.setAccount_from(userDao.getUsername());
//        transferGt.setAccount_to(transfer.getUserTo().getUsername());
//        transferGt.setDescription(transfer.getUserFrom().getUsername() + " transferred more than $1000.");
//        transferGt.setAmount(transfer.getAmount().doubleValue());
//
//        restInternalRevenueService.setAuthToken(authenticationService.login("Team09", "password"));
//        restInternalRevenueService.logTransfer(transferGt);
//    }

    @Override
    public List<Transfer> getAllTransfersByUserId(int userId) {
        List<Transfer> allTransfers = new ArrayList<>();

        String sql="SELECT * FROM transfer " +
                "WHERE user_from = ?;";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql,userId);
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
