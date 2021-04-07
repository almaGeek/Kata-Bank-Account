package com.bankaccount.api.rest;

import com.bankaccount.api.dto.AccountDto;
import com.bankaccount.api.enums.Account;
import com.bankaccount.api.service.AccountService;
import org.json.JSONObject;
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
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    List<AccountDto> mockAccounts = Arrays.asList(
            AccountDto.builder().id(1L).accountNumber("25403698").typeAccount(Account.COURANT.getType()).currentBalance(0).maxTransaction(1000).build(),
            AccountDto.builder().id(2L).accountNumber("25403699").typeAccount(Account.EPARGNE.getType()).currentBalance(100).maxTransaction(1000).build(),
            AccountDto.builder().id(3L).accountNumber("25403610").typeAccount(Account.COURANT.getType()).currentBalance(1000).maxTransaction(1000).build());

     /********
     ***  these are samples of unit tests using junit and mockito
     ********/
    @Test
    void shouldReturnAllAccounts() throws Exception {
        Mockito.when(accountService.getAll()).thenReturn(mockAccounts);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/account");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String data = "[{\"id\":1,\"accountNumber\":\"25403698\",\"maxTransaction\":1000.0,\"currentBalance\":0.0,\"typeAccount\":\"Courant\"},{\"id\":2,\"accountNumber\":\"25403699\",\"maxTransaction\":1000.0,\"currentBalance\":100.0,\"typeAccount\":\"Epargne\"},{\"id\":3,\"accountNumber\":\"25403610\",\"maxTransaction\":1000.0,\"currentBalance\":1000.0,\"typeAccount\":\"Courant\"}]";
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
        Assert.assertEquals(data, result.getResponse().getContentAsString());
    }

    @Test
    void shouldAddAccount() throws Exception {
        Mockito.when(accountService.add(Mockito.any(AccountDto.class))).thenReturn(mockAccounts.get(0));
        String accountDto = "{\"accountNumber\":\"25403698\",\"maxTransaction\":1000.0,\"currentBalance\":0.0,\"typeAccount\":\"Courant\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/account")
                .accept(MediaType.APPLICATION_JSON).content(accountDto)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        JSONObject json = new JSONObject(response.getContentAsString());
        Assert.assertEquals(1,json.get("id"));
    }

    @Test
    void shouldUpdateAccount() throws Exception {
        mockAccounts.get(1).setMaxTransaction(500);
        Mockito.when(accountService.update(Mockito.any(AccountDto.class))).thenReturn(mockAccounts.get(1));
        String accountDto = "{\"id\":2,\"accountNumber\":\"25403698\",\"maxTransaction\":500.0,\"currentBalance\":0.0,\"typeAccount\":\"Courant\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/account")
                .accept(MediaType.APPLICATION_JSON).content(accountDto)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        JSONObject json = new JSONObject(response.getContentAsString());
        Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        Assert.assertEquals(2,json.get("id"));
        Assert.assertEquals(500.0,json.get("maxTransaction"));
    }

    @Test
    void shouldDeleteAccount() throws Exception {
        Mockito.when(accountService.delete(10001L)).thenReturn(true);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/account/10001"))
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
        Assert.assertEquals(response.getContentAsString(), "true");
    }

    @Test
    void shouldReturnAccountById() throws Exception {
        Mockito.when(accountService.getById(Mockito.anyLong())).thenReturn(mockAccounts.get(2));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/account/3"))
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        JSONObject json = new JSONObject(response.getContentAsString());
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
        Assert.assertEquals(3,json.get("id"));
        Assert.assertEquals("25403610",json.get("accountNumber"));
    }
}