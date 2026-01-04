package com.ssbc.loanportal.controller;


import com.ssbc.loanportal.loanProduct.LoanProductService;
import com.ssbc.loanportal.dto.ApiResponse;
import com.ssbc.loanportal.dto.LoanProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loans/products")
public class LoanProductController {
    private final LoanProductService service;

    public LoanProductController(LoanProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<LoanProductResponse>>> getAll() {
        return ResponseEntity.ok(
                ApiResponse.ok("Products fetched", service.getAllProducts())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LoanProductResponse>> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.ok("Product fetched", service.getProductById(id))
        );
    }
}
