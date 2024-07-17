package unittest.unittest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import unittest.unittest.model.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer>, JpaSpecificationExecutor<Customer> {
}
