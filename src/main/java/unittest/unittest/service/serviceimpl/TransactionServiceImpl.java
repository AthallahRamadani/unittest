package unittest.unittest.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import unittest.unittest.model.Customer;
import unittest.unittest.model.Transaction;
import unittest.unittest.repository.TransactionRepo;
import unittest.unittest.service.CustomerService;
import unittest.unittest.service.TransactionService;
import unittest.unittest.utils.dto.TransactionDto;
import unittest.unittest.utils.specification.TransactionSpecification;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepo transactionRepo;
    private final CustomerService customerService;

    @Override
    public Transaction create(TransactionDto newTransaction) {
        Customer customer = customerService.getById(newTransaction.getCustomerId());
        return transactionRepo.save(Transaction.builder()
                .price(newTransaction.getPrice())
                .customer(customer)
                .productName(newTransaction.getProductName())
                .quantity(newTransaction.getQuantity())
                .build());
    }

    @Override
    public Page<Transaction> getAll(Pageable pageable, String name) {
        Specification<Transaction> spec = TransactionSpecification.getSpecification(name);
        return transactionRepo.findAll(spec, pageable);
    }

    @Override
    public Transaction getById(Integer id) {
        return transactionRepo.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    @Override
    public Transaction updateById(Integer id, TransactionDto updatedTransaction) {
        Customer customer = customerService.getById(updatedTransaction.getCustomerId());
        Transaction transaction = getById(id);

        transaction.setCustomer(customer);
        transaction.setPrice(updatedTransaction.getPrice());
        transaction.setQuantity(updatedTransaction.getQuantity());
        transaction.setProductName(updatedTransaction.getProductName());

        return transactionRepo.save(transaction);
    }

    @Override
    public void deleteById(Integer id) {
        transactionRepo.deleteById(id);
    }
}
