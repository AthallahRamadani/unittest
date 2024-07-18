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

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepo transactionRepo;
    private final CustomerService customerService;


    @Override
    public Transaction create(TransactionDto transactionDTO) {
        validateTransactionDTO(transactionDTO);

        Customer customer = customerService.getById(transactionDTO.getCustomerId());
        return transactionRepo.save(Transaction.builder()
                .customer(customer)
                .productName(transactionDTO.getProductName())
                .quantity(transactionDTO.getQuantity())
                .price(transactionDTO.getPrice())
                .build()
        );
    }

    @Override
    public List<Transaction> getAll() {
        return transactionRepo.findAll();
    }

    @Override
    public Transaction getById(Integer id) {
        return transactionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    @Override
    public Transaction updateById(Integer id, TransactionDto transactionDTO) {
        validateTransactionDTO(transactionDTO);

        Transaction foundTransaction = getById(id);
        foundTransaction.setProductName(transactionDTO.getProductName());
        foundTransaction.setQuantity(transactionDTO.getQuantity());
        foundTransaction.setPrice(transactionDTO.getPrice());

        return transactionRepo.save(foundTransaction);
    }

    @Override
    public void deleteById(Integer id) {
        Transaction transaction = getById(id);
        transactionRepo.delete(transaction);
    }

    private void validateTransactionDTO(TransactionDto transactionDTO) {
        if (transactionDTO.getCustomerId() == null) {
            throw new IllegalArgumentException("Customer ID cannot be null");
        }
        if (transactionDTO.getProductName() == null) {
            throw new IllegalArgumentException("Product name cannot be null");
        }
        if (transactionDTO.getQuantity() == null) {
            throw new IllegalArgumentException("Quantity cannot be null");
        }
        if (transactionDTO.getPrice() == null) {
            throw new IllegalArgumentException("Price cannot be null");
        }
    }
}
