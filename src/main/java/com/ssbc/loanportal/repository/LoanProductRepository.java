package com.ssbc.loanportal.repository;

import com.ssbc.loanportal.loanProduct.LoanProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface LoanProductRepository extends JpaRepository<LoanProduct, Long> {
}
