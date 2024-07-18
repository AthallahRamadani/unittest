package unittest.unittest.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import unittest.unittest.model.Customer;
import unittest.unittest.repository.CustomerRepo;
import unittest.unittest.service.CustomerService;
import unittest.unittest.utils.dto.CustomerDto;
import unittest.unittest.utils.specification.CustomerSpecification;
import unittest.unittest.utils.specification.TransactionSpecification;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepo customerRepo;

    @Override
    public Customer create(CustomerDto newCustomer) {
        return customerRepo.save(Customer.builder()
                .name(newCustomer.getName())
                .birthDate(newCustomer.getBirthDate())
                .build());
    }

    @Override
    public Page<Customer> getAll(Pageable pageable, String name) {
        Specification<Customer> spec = CustomerSpecification.getSpecification(name);
        return customerRepo.findAll(spec, pageable);
    }

    @Override
    public Customer getById(Integer id) {
        return customerRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public Customer updateById(Integer id, CustomerDto updatedCustomer) {
        Customer customer = getById(id);
        customer.setName(updatedCustomer.getName());
        customer.setBirthDate(updatedCustomer.getBirthDate());
        return customerRepo.save(customer);
    }

    @Override
    public void deleteById(Integer id) {
        customerRepo.deleteById(id);
    }
}
