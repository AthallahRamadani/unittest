package unittest.unittest.service;

import org.springframework.http.ResponseEntity;
import unittest.unittest.utils.dto.CustomerDto;

import java.util.Map;

public interface ImageService {
    ResponseEntity<Map> uploadImage(CustomerDto customerDto);
}
