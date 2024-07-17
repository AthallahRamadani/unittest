package unittest.unittest.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import unittest.unittest.model.Customer;
import unittest.unittest.repository.CustomerRepo;
import unittest.unittest.service.serviceimpl.CustomerServiceImpl;
import unittest.unittest.utils.dto.CustomerDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Mock
    private CustomerRepo customerRepo;

    @InjectMocks
    private CustomerServiceImpl customerServiceImpl;

    @Test
    void create() {
        // Given
        CustomerDto customerCreate = new CustomerDto("Jokowi", LocalDate.of(2010, 12, 22));
        Customer mockedCustomer = new Customer(1, "Jokowi", LocalDate.of(2010, 12, 22));

        given(customerRepo.save(any(Customer.class))).willReturn(mockedCustomer);

        // When
        Customer insertCustomer = customerServiceImpl.create(customerCreate);

        // Then
        assertNotNull(insertCustomer);
        assertEquals("Jokowi", insertCustomer.getName());
        assertEquals(LocalDate.of(2010, 12, 22), insertCustomer.getBirthDate());
    }

    @Test
    void getAllPerson() {
        // Given
        Customer customer1 = new Customer(1, "Budi", LocalDate.of(2010, 12, 12));
        Customer customer2 = new Customer(2, "Michael", LocalDate.of(2010, 11, 11));
        Pageable pageable = PageRequest.of(0, 10);
        Page<Customer> page = new PageImpl<>(List.of(customer1, customer2));

        // When
        given(customerRepo.findAll(any(Specification.class), any(Pageable.class))).willReturn(page);

        // Then
        Page<Customer> result = customerServiceImpl.getAll(pageable, "Budi");
        assertEquals(2, result.getTotalElements());
        assertEquals("Budi", result.getContent().get(0).getName());
        assertEquals("Michael", result.getContent().get(1).getName());
    }

    @Test
    void getById() {
        // Given
        Customer customer1 = new Customer(1, "Budi", LocalDate.of(2010, 12, 12));
        given(customerRepo.findById(1)).willReturn(Optional.of(customer1));

        // When
        Customer foundCustomer = customerServiceImpl.getById(1);

        // Then
        assertEquals(customer1, foundCustomer);
        assertEquals("Budi", foundCustomer.getName());
    }

    @Test
    void getById_NotFound() {
        // Given
        given(customerRepo.findById(anyInt())).willReturn(Optional.empty());

        // When & Then
        Exception exception = assertThrows(RuntimeException.class, () -> customerServiceImpl.getById(1));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void updateById() {
        // Given
        Customer customer1 = new Customer(1, "Budi", LocalDate.of(2010, 12, 12));
        CustomerDto customerUpdate = new CustomerDto("Jokowi", LocalDate.of(2010, 12, 22));
        given(customerRepo.findById(1)).willReturn(Optional.of(customer1));
        given(customerRepo.save(any(Customer.class))).willReturn(customer1);

        // When
        Customer customerUpdated = customerServiceImpl.updateById(1, customerUpdate);

        // Then
        assertNotEquals("Budi", customerUpdated.getName());
        assertEquals("Jokowi", customerUpdated.getName());
        assertEquals(LocalDate.of(2010, 12, 22), customerUpdated.getBirthDate());
    }

    @Test
    void deletedById() {
        // Given
        Integer customerId = 1;

        // When
        customerServiceImpl.deleteById(customerId);

        // Then
        verify(customerRepo, times(1)).deleteById(customerId);
    }
}
