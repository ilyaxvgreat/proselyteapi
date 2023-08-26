package com.khomchenko.proselyteapi.repositories;

import com.khomchenko.proselyteapi.models.Stock;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface StocksRepository extends R2dbcRepository<Stock, Integer> {

    Flux<Stock> findAllByCode(String code);
}
