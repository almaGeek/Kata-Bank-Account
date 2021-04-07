package com.bankaccount.api.rest;

import com.bankaccount.api.dto.TransactionDto;
import com.bankaccount.api.service.UserTransactionService;
import com.bankaccount.api.utils.TransactionValidator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transaction")
@CrossOrigin
public class TransactionController {

      private static final String INVALID_TRANSACTION =
              "transaction info is not valid. Please try again.";
      private static final Log LOGGER = LogFactory.getLog(TransactionController.class);

      private UserTransactionService userTransactionService;

      @Autowired
      public TransactionController(UserTransactionService userTransactionService){
            this.userTransactionService = userTransactionService;
      }

      @PostMapping
      public ResponseEntity<?> addTransaction(@Valid @RequestBody TransactionDto transactionDto) {
            LOGGER.info("[IN] TransactionController.addTransaction");
            boolean done = false;
            if (TransactionValidator.isTransactionValid(transactionDto)) {
                  done = userTransactionService.addTransaction(transactionDto);
                  LOGGER.info("[OUT] TransactionController.addTransaction done = " + done);
                  return new ResponseEntity<>(done, HttpStatus.CREATED);
            } else {
                  LOGGER.error("[OUT] TransactionController.addTransaction done = " + done);
                  return new ResponseEntity<>(INVALID_TRANSACTION, HttpStatus.BAD_REQUEST);
            }
      }

      @ResponseStatus(HttpStatus.BAD_REQUEST)
      @ExceptionHandler(MethodArgumentNotValidException.class)
      public Map<String, String> handleValidationExceptions(
              MethodArgumentNotValidException ex) {
            Map<String, String> errors = new HashMap<>();
            ex.getBindingResult().getAllErrors().forEach((error) -> {
                  String fieldName = ((FieldError) error).getField();
                  String errorMessage = error.getDefaultMessage();
                  errors.put(fieldName, errorMessage);
            });
            return errors;
      }

      @GetMapping("/{id}")
      public ResponseEntity<List<TransactionDto>> getAccountTransactions(@PathVariable("id") Long id) {
            LOGGER.info("[IN] TransactionController.getAccountTransactions()");
            try {
                  List<TransactionDto> accountTransactions = userTransactionService.getAccountTransactions(id);
                  LOGGER.info("[OUT] TransactionController.getAccountTransactions() + " + HttpStatus.OK);
                  return new ResponseEntity<List<TransactionDto>>(accountTransactions, HttpStatus.OK);
            } catch (Exception e) {
                  LOGGER.error("[OUT] TransactionController.getAccountTransactions() + " + HttpStatus.INTERNAL_SERVER_ERROR);
                  return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
      }

      @GetMapping()
      public ResponseEntity<List<TransactionDto>> getHistoriqueTransactions() {
            LOGGER.info("[IN] TransactionController.getHistoriqueTransactions()");
            try {
                  List<TransactionDto> transactions = userTransactionService.getHistoriqueTransactions();
                  LOGGER.info("[OUT] TransactionController.getHistoriqueTransactions : " + HttpStatus.OK);
                  return new ResponseEntity<>(transactions, HttpStatus.OK);
            } catch (Exception e) {
                  LOGGER.info("[OUT] TransactionController.getHistoriqueTransactions : " + HttpStatus.INTERNAL_SERVER_ERROR);
                  return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
      }
}
