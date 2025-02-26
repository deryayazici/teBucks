package com.techelevator.tebucks.controller;

import com.techelevator.tebucks.AuthenticationService;
import com.techelevator.tebucks.InternalRevenueService;
import com.techelevator.tebucks.RestInternalRevenueService;
import com.techelevator.tebucks.dao.AccountDao;
import com.techelevator.tebucks.dao.TransferDao;
import com.techelevator.tebucks.dao.UserDao;
import com.techelevator.tebucks.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Component
@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TransferDao transferDao;

    @Autowired
    private RestInternalRevenueService internalRevenueService;

    @Autowired
    private AuthenticationService authenticationService;

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

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/api/transfers", method = RequestMethod.POST)
    public Transfer makeTransfer(@Valid @RequestBody NewTransferDto newTransferDto, Principal principal) {


        String user = principal.getName();
        Integer userId =userDao.findIdByUsername(user);


        Transfer newTransfer = null;

//        if (newTransferDto.getAmount().compareTo(accountDao.getAccountBalance(userId)) > 0) {
//            TxLogDto transferGt = new TxLogDto();
//
//            transferGt.setAccount_from(newTransfer.getUserFrom().getUsername());
//            transferGt.setAccount_to(newTransfer.getUserTo().getUsername());
//            transferGt.setDescription(newTransfer.getUserFrom().getUsername() + " overdraft attempt.");
//            transferGt.setAmount(newTransfer.getAmount().doubleValue());
//
//            internalRevenueService.setAuthToken(authenticationService.login("Team09", "password"));
//            internalRevenueService.logTransfer(transferGt);
//
//        }

        if (newTransferDto.getAmount().compareTo(accountDao.getAccountBalance(userId)) > 0) {
            TxLogDto txLogDto = new TxLogDto();
            txLogDto.setAccount_from(user);
            txLogDto.setAccount_to(userDao.getUserById(newTransferDto.getUserTo()).getUsername());
            txLogDto.setAmount(newTransferDto.getAmount().doubleValue());
            txLogDto.setDescription(user + " tried to overdraft.");

            internalRevenueService.setAuthToken(authenticationService.login("Team09", "password"));
            internalRevenueService.logTransfer(txLogDto);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if(newTransferDto.getAmount().compareTo(accountDao.getAccountBalance(userId)) <= 0 &&newTransferDto.getAmount().compareTo(BigDecimal.ZERO) > 0 && userId != newTransferDto.getUserTo() ){
            newTransfer =transferDao.makeTransfer(newTransferDto);
            accountDao.updateReceiverBalance(newTransferDto.getUserTo(),newTransferDto.getAmount());
            accountDao.updateSenderBalance(userId,newTransferDto.getAmount());
        }

        return newTransfer;
    }

    @RequestMapping (path = "/api/account/transfers", method =RequestMethod.GET)
    public List<Transfer> getAllTransfersByUserId ( Principal principal) {
        String username = principal.getName();
        Integer userId = userDao.findIdByUsername(username);

      return   transferDao.getAllTransfersByUserId(userId);
    }

    @RequestMapping (path = "/api/transfers/{id}", method =RequestMethod.GET)
    public Transfer getTransferByTransferId (@PathVariable int id, Principal principal) {
        String username = principal.getName();
        Integer userId = userDao.findIdByUsername(username);
        Transfer newTransfer = transferDao.getTransfer(id);

        if (newTransfer.getUserFrom().getId() == userId) {
            return newTransfer;
        } else if (newTransfer.getUserTo().getId() == userId) {
            return newTransfer;
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);

    }
//    @ResponseStatus(HttpStatus.CREATED)
//    @RequestMapping(path = "/api/Login", method = RequestMethod.POST)
//    public Transfer logTransfer(@Valid @RequestBody Transfer transfer, Principal principal) {
//
//        String user = principal.getName();
//        Integer userId =userDao.findIdByUsername(user);
//
//        Transfer newTransfer = null;
//        BigDecimal oneThousand = new BigDecimal("1000");
//
//        if(transfer.getAmount().compareTo(oneThousand)==1){
//            newTransfer =internalRevenueService.logTransfer(transfer);
//        }
//
//
//        return newTransfer;
//    }


}
