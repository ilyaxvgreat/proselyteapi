package com.khomchenko.proselyteapi.services;

import com.khomchenko.proselyteapi.models.Company;
import reactor.core.publisher.Flux;

public interface CompaniesService {

    Flux<Company> getAll();
}
