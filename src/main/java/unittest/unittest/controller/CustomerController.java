package unittest.unittest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import unittest.unittest.service.CustomerService;
import unittest.unittest.utils.dto.CustomerDto;
import unittest.unittest.utils.response.PageResponse;
import unittest.unittest.utils.response.Response;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @Validated
    public ResponseEntity<?> create(@Valid @RequestBody CustomerDto newCustomer) {
        return Response.renderJSON(
                customerService.create(newCustomer),
                "New Customer Created",
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) String name
    ) {
        return Response.renderJSON(new PageResponse<>(customerService.getAll(pageable, name)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return Response.renderJSON(customerService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable Integer id, @RequestBody CustomerDto updatedCustomer) {
        return Response.renderJSON(customerService.updateById(id, updatedCustomer), "Updated");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer id) {
        customerService.deleteById(id);
    }
}
