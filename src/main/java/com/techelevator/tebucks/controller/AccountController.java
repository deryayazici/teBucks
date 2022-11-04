package com.techelevator.tebucks.controller;

import com.techelevator.tebucks.dao.AccountDao;
import com.techelevator.tebucks.dao.TransferDao;
import com.techelevator.tebucks.dao.UserDao;
import com.techelevator.tebucks.model.Account;
import com.techelevator.tebucks.model.NewTransferDto;
import com.techelevator.tebucks.model.Transfer;
import com.techelevator.tebucks.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TransferDao transferDao;

    @RequestMapping(path = "/api/account/balance", method = RequestMethod.GET)
    public BigDecimal getAccountBalance( Principal principal) {
        String username = principal.getName();
       Integer userId =userDao.findIdByUsername(username);

        return accountDao.getAccountBalance(userId);
    }

    @RequestMapping(path = "/api/users", method = RequestMethod.GET)
    public List<User> findAll(Principal principal) {

        String userName = principal.getName();
        Integer userId = userDao.findIdByUsername(userName);

        List<User> usersList = userDao.findAll();

        List<User> listOfOthers = new ArrayList<>();

        for (User user : usersList) {
            if (userId != user.getId()) {
                listOfOthers.add(user);
            }

        }return listOfOthers;

    }
    @RequestMapping(path = "/api/transfers", method = RequestMethod.POST)
    public Transfer makeTransfer(@Valid @RequestBody NewTransferDto newTransferDto, Principal principal) {


        String user = principal.getName();
        Integer userId =userDao.findIdByUsername(user);

        Transfer newTransfer = null;

        if(newTransferDto.getAmount().compareTo(accountDao.getAccountBalance(userId)) <=0 && newTransferDto.getAmount().compareTo(BigDecimal.ZERO) > 0 && userId != newTransferDto.getUserTo() ){
            newTransfer =transferDao.makeTransfer(newTransfer.getUserFrom(), newTransfer.getUserTo(), newTransferDto.getAmount());
            accountDao.updateReceiverBalance(newTransferDto.getUserTo(),newTransferDto.getAmount());
            accountDao.updateSenderBalance(userId,newTransferDto.getAmount());
        }


        return newTransfer;
    }

    @RequestMapping(path = "/api/account/transfers", method = RequestMethod.GET)
    public List<Transfer> getAllTransfersByAccount(Principal principal) {

        String username = principal.getName();
        Integer accountId = accountDao.getAccountById(username);

        return transferDao.getAllTransfersByAccountId(accountId);
    }


    @RequestMapping(path = "/api/transfers/{id}", method = RequestMethod.GET)
    public Transfer getTransferByTransferId(@PathVariable int id) {
        return transferDao.getTransfer(id);
    }


}
