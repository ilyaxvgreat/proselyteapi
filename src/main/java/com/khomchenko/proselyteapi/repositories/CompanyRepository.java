package com.khomchenko.proselyteapi.repositories;

import com.khomchenko.proselyteapi.models.Company;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface CompanyRepository extends R2dbcRepository<Company, Integer> {

    Mono<Company> findByCode(String code);
}
