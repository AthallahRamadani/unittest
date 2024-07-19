package unittest.unittest.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import unittest.unittest.model.Customer;
import unittest.unittest.repository.CustomerRepo;
import unittest.unittest.service.CloudinaryService;
import unittest.unittest.service.ImageService;
import unittest.unittest.utils.dto.CustomerDto;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private CustomerRepo customerRepo;


    @Override
    public ResponseEntity<Map> uploadImage(CustomerDto customerDto) {
        try {
            if (customerDto.getName().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            if (customerDto.getImageFile().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            Customer customer = new Customer();
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
}
