package com.bankaccount.api.rest;

import com.bankaccount.api.dto.TransactionDto;
import com.bankaccount.api.service.UserTransactionService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserTransactionService transactionService;

    List<TransactionDto> mockTransactions = Arrays.asList(
            TransactionDto.builder().id(10L).typeTransaction("Deposit").accountNumber("00000001").amount(500.0).dateTransaction(LocalDateTime.of(2021,04,07,10,10,10)).build(),
            TransactionDto.builder().id(11L).typeTransaction("Withdrawal").accountNumber("00000001").amount(20.0).dateTransaction(LocalDateTime.of(2021,04,07,10,10,10)).build()
    );

    @Test
    void addTransaction() throws Exception {
        Mockito.when(transactionService.addTransaction(Mockito.anyObject())).thenReturn(true);
        String transactionDto = "{\"typeTransaction\":\"Deposit\",\"amount\":500.0,\"accountNumber\":\"00000001\",\"dateTransaction\":\"2021-04-07T10:10:10\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/transaction")
                .accept(MediaType.APPLICATION_JSON).content(transactionDto)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        Assert.assertEquals("true", result.getResponse().getContentAsString());
    }

    @Test
    void shouldReturnAllAccountTransactions() throws Exception {
        Mockito.when(transactionService.getAccountTransactions(Mockito.anyLong())).thenReturn(mockTransactions);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/transaction/1");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String data = "[{\"id\":10,\"typeTransaction\":\"Deposit\",\"amount\":500.0,\"accountNumber\":\"00000001\",\"dateTransaction\":\"2021-04-07T10:10:10\"}";
        data += ",{\"id\":11,\"typeTransaction\":\"Withdrawal\",\"amount\":20.0,\"accountNumber\":\"00000001\",\"dateTransaction\":\"2021-04-07T10:10:10\"}]";
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
        Assert.assertEquals(data, result.getResponse().getContentAsString());
    }

    @Test
    void getHistoriqueTransactions() throws Exception {
        Mockito.when(transactionService.getHistoriqueTransactions()).thenReturn(mockTransactions);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/transaction");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String data = "[{\"id\":10,\"typeTransaction\":\"Deposit\",\"amount\":500.0,\"accountNumber\":\"00000001\",\"dateTransaction\":\"2021-04-07T10:10:10\"}";
        data += ",{\"id\":11,\"typeTransaction\":\"Withdrawal\",\"amount\":20.0,\"accountNumber\":\"00000001\",\"dateTransaction\":\"2021-04-07T10:10:10\"}]";
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
        Assert.assertEquals(data, result.getResponse().getContentAsString());
    }
}