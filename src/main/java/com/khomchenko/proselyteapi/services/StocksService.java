package com.khomchenko.proselyteapi.services;

import com.khomchenko.proselyteapi.dto.StockDto;
import com.khomchenko.proselyteapi.models.Stock;
import reactor.core.publisher.Mono;

import java.util.List;

public interface StocksService {
    Mono<List<StockDto>> getAllCompanyStocks(String stockCode);


    Mono<StockDto> save(Stock stock);
}
