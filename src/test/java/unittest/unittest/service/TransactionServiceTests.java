package unittest.unittest.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import unittest.unittest.model.Customer;
import unittest.unittest.model.Transaction;
import unittest.unittest.repository.TransactionRepo;
import unittest.unittest.service.serviceimpl.TransactionServiceImpl;
import unittest.unittest.utils.dto.TransactionDto;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTests {
    @Mock
    private TransactionRepo transactionRepository;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private List<Transaction> transactions;
    private TransactionDto transactionDTO;
    private Customer customer;
    private Transaction transaction;

    @BeforeEach
    public void beforeEach() {
        transactions = mock(List.class);
        transactionDTO = TransactionDto.builder()
                .customerId(1)
                .productName("product")
                .quantity(1)
                .price(1)
                .build();
        customer = mock(Customer.class);
        transaction = mock(Transaction.class);
    }

    @Test
    void getAllTransaction() {
        when(transactionRepository.findAll()).thenReturn(transactions);

        List<Transaction> foundTransactions = transactionService.getAll();

        assertEquals(transactions, foundTransactions);
        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    void getTransactionById() {
        when(transactionRepository.findById(any(Integer.class)))
                .thenReturn(Optional.of(transaction));

        Transaction foundTransaction = transactionService.getById(1);

        assertEquals(transaction, foundTransaction);
        verify(transactionRepository, times(1)).findById(any(Integer.class));
    }

    @Test
    void createTransaction_success() {
        when(customerService.getById(any(Integer.class))).thenReturn(customer);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction createdTransaction = transactionService.create(transactionDTO);

        assertEquals(transaction, createdTransaction);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void createTransaction_fail() {
        Integer nonExistentId = 999;
        when(customerService.getById(nonExistentId))
                .thenThrow(new RuntimeException());

        assertThatThrownBy(() -> transactionService.create(transactionDTO))
                .isInstanceOf(RuntimeException.class);
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void updateTransaction_success() {
        when(transactionRepository.findById(any(Integer.class)))
                .thenReturn(Optional.of(transaction));
        when(transactionRepository.save(any(Transaction.class)))
                .thenReturn(transaction);

        Transaction updatedTransaction = transactionService.updateById(1, transactionDTO);

        assertEquals(transaction, updatedTransaction);
        verify(transactionRepository, times(1)).findById(any(Integer.class));
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void deleteTransaction_success() {
        when(transactionRepository.findById(1))
                .thenReturn(Optional.of(transaction))
                .thenReturn(Optional.empty());
        doNothing().when(transactionRepository).delete(transaction);

        transactionService.deleteById(1);
        Optional<Transaction> found = transactionRepository.findById(1);

        assertThat(found).isEmpty();
        verify(transactionRepository, times(2)).findById(1);
        verify(transactionRepository, times(1)).delete(transaction);
    }
}
