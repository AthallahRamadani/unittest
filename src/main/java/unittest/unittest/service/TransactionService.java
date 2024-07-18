package unittest.unittest.service;

import unittest.unittest.model.Transaction;
import unittest.unittest.utils.dto.TransactionDto;

import java.util.List;

public interface TransactionService {
    Transaction create(TransactionDto transactionDTO);

    List<Transaction> getAll();

    Transaction getById(Integer id);

    Transaction updateById(Integer id, TransactionDto transactionDTO);

    void deleteById(Integer id);
}
