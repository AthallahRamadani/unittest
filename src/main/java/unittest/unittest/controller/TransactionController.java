package unittest.unittest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import unittest.unittest.model.Transaction;
import unittest.unittest.service.TransactionService;
import unittest.unittest.utils.dto.TransactionDto;
import unittest.unittest.utils.response.PageResponse;
import unittest.unittest.utils.response.Response;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public Transaction create(
            @RequestBody TransactionDto transactionDTO
    ) {
        return transactionService.create(transactionDTO);
    }

    @GetMapping
    public List<Transaction> getAll() {
        return transactionService.getAll();
    }

    @GetMapping("/{id}")
    public Transaction getById(
            @PathVariable Integer id
    ) {
        return transactionService.getById(id);
    }

    @PutMapping("/{id}")
    public Transaction updateById(
            @PathVariable Integer id,
            @RequestBody TransactionDto transactionDTO
    ) {
        return transactionService.updateById(id, transactionDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteById(
            @PathVariable Integer id
    ) {
        transactionService.deleteById(id);
    }
}