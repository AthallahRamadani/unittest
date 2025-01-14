package unittest.unittest.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import unittest.unittest.model.Customer;
import unittest.unittest.repository.CustomerRepo;
import unittest.unittest.service.CloudinaryService;
import unittest.unittest.service.CustomerService;
import unittest.unittest.utils.dto.CustomerDto;
import unittest.unittest.utils.specification.CustomerSpecification;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepo customerRepo;
    @Autowired
    private CloudinaryService cloudinaryService;


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
    public ResponseEntity<Map> updateById(Integer id, CustomerDto customerDto) {
        try {
            if (customerDto.getName().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            if (customerDto.getImageFile().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            Customer customer = getById(id);
            customer.setName(customerDto.getName());
            customer.setBirthDate(customerDto.getBirthDate());
            customer.setUrl(cloudinaryService.uploadFile(customerDto.getImageFile(), "folder_1"));
            if (customer.getUrl() == null) {
                return ResponseEntity.badRequest().build();
            }
            customerRepo.save(customer);
            return ResponseEntity.ok().body(Map.of("url", customer.getUrl()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteById(Integer id) {
        customerRepo.deleteById(id);
    }
}
