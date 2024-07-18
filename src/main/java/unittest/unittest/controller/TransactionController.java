package unittest.unittest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import unittest.unittest.service.TransactionService;
import unittest.unittest.utils.dto.TransactionDto;
import unittest.unittest.utils.response.PageResponse;
import unittest.unittest.utils.response.Response;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    @Validated
    public ResponseEntity<?> create(@Valid @RequestBody TransactionDto newTransaction) {
        return Response.renderJSON(
                transactionService.create(newTransaction),
                "New Transaction Created",
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) String name
    ) {
        return Response.renderJSON(new PageResponse<>(transactionService.getAll(pageable, name)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return Response.renderJSON(transactionService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable Integer id, @RequestBody TransactionDto updatedTransaction) {
        return Response.renderJSON(transactionService.updateById(id, updatedTransaction), "Updated");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer id) {
        transactionService.deleteById(id);
    }
}
