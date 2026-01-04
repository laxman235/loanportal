package com.ssbc.loanportal.loanProduct;

import com.ssbc.loanportal.repository.LoanProductRepository;
import com.ssbc.loanportal.dto.LoanProductResponse;
import com.ssbc.loanportal.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class LoanProductServiceImpl implements LoanProductService {

    private final LoanProductRepository repo;

    public LoanProductServiceImpl(LoanProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<LoanProductResponse> getAllProducts() {
        return repo.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public LoanProductResponse getProductById(Long id) {
        LoanProduct p = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        return mapToResponse(p);
    }

    @Override
    public LoanProductResponse createProduct(LoanProductCreateRequest req) {
        LoanProduct p = new LoanProduct();

        p.setName(req.getName());
        p.setDescription(req.getDescription());
        p.setMinAmount(req.getMinAmount());
        p.setMaxAmount(req.getMaxAmount());
        p.setMinTermMonths(req.getMinTermMonths());
        p.setMaxTermMonths(req.getMaxTermMonths());
        p.setInterestRate(req.getInterestRate());
        p.setFees(req.getFees());

        repo.save(p);

        return mapToResponse(p);
    }

    @Override
    public LoanProductResponse updateProduct(Long id, LoanProductCreateRequest req) {
        LoanProduct p = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        p.setName(req.getName());
        p.setDescription(req.getDescription());
        p.setMinAmount(req.getMinAmount());
        p.setMaxAmount(req.getMaxAmount());
        p.setMinTermMonths(req.getMinTermMonths());
        p.setMaxTermMonths(req.getMaxTermMonths());
        p.setInterestRate(req.getInterestRate());
        p.setFees(req.getFees());

        repo.save(p);

        return mapToResponse(p);
    }

    private LoanProductResponse mapToResponse(LoanProduct p) {
        LoanProductResponse r = new LoanProductResponse();

        r.setId(p.getId());
        r.setName(p.getName());
        r.setDescription(p.getDescription());
        r.setMinAmount(p.getMinAmount());
        r.setMaxAmount(p.getMaxAmount());
        r.setMinTermMonths(p.getMinTermMonths());
        r.setMaxTermMonths(p.getMaxTermMonths());
        r.setInterestRate(p.getInterestRate());
        r.setFees(p.getFees());
        r.setActive(p.getActive());

        return r;
    }
}
