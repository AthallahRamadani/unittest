package unittest.unittest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import unittest.unittest.model.Customer;
import unittest.unittest.model.Transaction;
import unittest.unittest.service.TransactionService;
import unittest.unittest.utils.dto.TransactionDto;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Transaction> transactions;
    private TransactionDto transactionDto;
    private Customer customer;
    private Transaction transaction;


    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .id(1)
                .name("jong un")
                .birthDate(LocalDate.now())
                .build();

        transaction = Transaction.builder()
                .id(1)
                .price(1000)
                .quantity(5)
                .productName("gayung")
                .build();

        transactionDto = TransactionDto.builder()
                .customerId(customer.getId())
                .price(1000)
                .quantity(5)
                .productName("gayung")
                .build();
    }

    @Test
    void createTransaction() throws Exception {
        when(transactionService.create(any(TransactionDto.class))).thenReturn(transaction);

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(transaction.getId())
                );
        verify(transactionService).create(any(TransactionDto.class));
    }

    @Test
    void getAllTransaction() throws Exception {
        List<Transaction> transactions = Arrays.asList(transaction, transaction, transaction);
        when(transactionService.getAll()).thenReturn(transactions);

        mockMvc.perform(get("/api/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));

        verify(transactionService).getAll();
    }

    @Test
    void deleteTransaction() throws Exception {
        // Arrange
        doNothing().when(transactionService).deleteById(transaction.getId());

        // Act & Assert
        mockMvc.perform(delete("/api/transactions/" + transaction.getId()))
                .andExpect(status().isOk());

        verify(transactionService).deleteById(transaction.getId());
    }
}
