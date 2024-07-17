package unittest.unittest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import unittest.unittest.model.Customer;
import unittest.unittest.utils.dto.CustomerDto;

public interface CustomerService {
    Customer create(CustomerDto newCustomer);

    Page<Customer> getAll(Pageable pageable, String name);

    Customer getById(Integer id);

    Customer updateById(Integer id, CustomerDto updatedCustomer);

    void deleteById(Integer id);
}
