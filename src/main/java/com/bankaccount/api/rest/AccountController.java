package com.bankaccount.api.rest;

import com.bankaccount.api.dto.AccountDto;
import com.bankaccount.api.service.AccountService;
import com.bankaccount.api.utils.AccountValidator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/account")
@CrossOrigin
public class AccountController {

    private static final String INVALID_ACCOUNT = "account info is not valid. Please try again.";

    private static final Log LOGGER = LogFactory.getLog(AccountController.class);

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAll() {
        LOGGER.info("[IN] AccountController.getAll()");
        try {
            List<AccountDto> accounts = accountService.getAll();
            LOGGER.info("[OUT] AccountController.getAll() + " + HttpStatus.OK);
            return new ResponseEntity<List<AccountDto>>(accounts, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("[OUT] AccountController.getAll() + " + HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody AccountDto accountDto) {
        LOGGER.info("[IN] AccountController.add()");
        try {
            if (AccountValidator.isAccountValid(accountDto)) {
                AccountDto account = accountService.add(accountDto);
                LOGGER.info("[OUT] AccountController.add() + status = " + HttpStatus.CREATED);
                return new ResponseEntity<>(account, HttpStatus.CREATED);
            } else {
                LOGGER.error("[OUT] AccountController.add() + status = " + HttpStatus.BAD_REQUEST);
                return new ResponseEntity<>(INVALID_ACCOUNT, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            LOGGER.error("[OUT] AccountController.add()  + status = " + HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody AccountDto accountDto) {
        LOGGER.info("[IN] AccountController.update()");
        try {
            if (AccountValidator.isAccountValid(accountDto)) {
                AccountDto account = accountService.update(accountDto);
                LOGGER.info("[OUT] AccountController.update() + status = "  + HttpStatus.CREATED);
                return new ResponseEntity<>(account, HttpStatus.CREATED);
            } else {
                LOGGER.error("[OUT] AccountController.update() + status = " + HttpStatus.BAD_REQUEST);
                return new ResponseEntity<>(INVALID_ACCOUNT, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            LOGGER.error("[OUT] AccountController.update() + status = " + HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        LOGGER.info("[IN] AccountController.delete()");
        boolean done = false;
        try {
            done = accountService.delete(id);
            LOGGER.info("[OUT] AccountController.delete() done = " + done);
            return new ResponseEntity<>(done, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("[OUT] AccountController.delete() done = " + done);
            return new ResponseEntity<>(INVALID_ACCOUNT, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<AccountDto> getById(@PathVariable("id") Long id) {
        LOGGER.info("[IN] AccountController.getById()");
        try {
            AccountDto AccountDto = accountService.getById(id);
            LOGGER.info("[OUT] AccountController.getById() : " + HttpStatus.OK);
            return new ResponseEntity<>(AccountDto, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("[OUT] AccountController.getById() : " + HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
