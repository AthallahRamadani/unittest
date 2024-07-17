package unittest.unittest.service.serviceimpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import unittest.unittest.model.Transaction;
import unittest.unittest.service.TransactionService;
import unittest.unittest.utils.dto.TransactionDto;

public class TransactionServiceImpl implements TransactionService {
    @Override
    public Transaction create(TransactionDto newTransaction) {
        return null;
    }

    @Override
    public Page<Transaction> getAll(Pageable pageable, String name) {
        return null;
    }

    @Override
    public Transaction getById(Integer id) {
        return null;
    }

    @Override
    public Transaction updateById(Integer id, TransactionDto updatedTransaction) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {

    }
}
