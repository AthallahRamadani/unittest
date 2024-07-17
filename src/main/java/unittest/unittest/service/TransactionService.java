package unittest.unittest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import unittest.unittest.model.Customer;
import unittest.unittest.model.Transaction;
import unittest.unittest.utils.dto.CustomerDto;
import unittest.unittest.utils.dto.TransactionDto;

public interface TransactionService {
    Transaction create(TransactionDto newTransaction);

    Page<Transaction> getAll(Pageable pageable, String name);

    Transaction getById(Integer id);

    Transaction updateById(Integer id, TransactionDto updatedTransaction);

    void deleteById(Integer id);
}
