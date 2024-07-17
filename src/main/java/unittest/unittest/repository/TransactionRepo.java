package unittest.unittest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unittest.unittest.model.Transaction;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Integer> {
}
