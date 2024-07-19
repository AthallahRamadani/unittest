package unittest.unittest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import unittest.unittest.model.Customer;
import unittest.unittest.utils.dto.CustomerDto;

import java.util.Map;

public interface CustomerService {
    Customer create(CustomerDto newCustomer);

    Page<Customer> getAll(Pageable pageable, String name);

    Customer getById(Integer id);

    ResponseEntity<Map> updateById(Integer id, CustomerDto customerDto);

    void deleteById(Integer id);
}
