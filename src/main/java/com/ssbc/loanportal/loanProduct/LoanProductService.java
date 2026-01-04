package com.ssbc.loanportal.loanProduct;

import com.ssbc.loanportal.dto.LoanProductResponse;

import java.util.List;

public interface LoanProductService {
    List<LoanProductResponse> getAllProducts();

    LoanProductResponse getProductById(Long id);

    LoanProductResponse createProduct(LoanProductCreateRequest req);

    LoanProductResponse updateProduct(Long id, LoanProductCreateRequest req);
}
