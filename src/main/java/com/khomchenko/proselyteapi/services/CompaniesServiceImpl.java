package com.khomchenko.proselyteapi.services;

import com.khomchenko.proselyteapi.models.Company;
import com.khomchenko.proselyteapi.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


@Service
@RequiredArgsConstructor
public class CompaniesServiceImpl implements CompaniesService {

    private final CompanyRepository companyRepository;

    @Override
    public Flux<Company> getAll() {
        return companyRepository.findAll();
    }
}
