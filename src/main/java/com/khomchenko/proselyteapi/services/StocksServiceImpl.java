package com.khomchenko.proselyteapi.services;

import com.khomchenko.proselyteapi.dto.StockDto;
import com.khomchenko.proselyteapi.mappers.CompanyMapper;
import com.khomchenko.proselyteapi.mappers.StocksMapper;
import com.khomchenko.proselyteapi.models.Stock;
import com.khomchenko.proselyteapi.repositories.CompanyRepository;
import com.khomchenko.proselyteapi.repositories.StocksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveListOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StocksServiceImpl implements StocksService {

    private final StocksRepository stocksRepository;
    private final CompanyRepository companyRepository;
    private final StocksMapper stocksMapper;
    private final ReactiveListOperations<String, StockDto> stocksListOperations;

    @Override
    public Mono<List<StockDto>> getAllCompanyStocks(String stockCode) {
        return companyRepository.findByCode(stockCode)
                .switchIfEmpty(Mono.error(new RuntimeException("NO SUCH CODE")))
                .flatMap(code -> stocksListOperations.range(stockCode, 0,-1).collectList())
                .switchIfEmpty(stocksRepository.findAllByCode(stockCode).map(stock -> stocksMapper.toDto(stock)).collectList());

//        return Mono.zip(companyRepository.findByCode(stockCode), stocksRepository.findAllByCode(stockCode).collectList())
//                .map(objects -> {
//                    CompanyDto company = companyMapper.toDto(objects.getT1());
//                    List<StockDto> stocks = objects.getT2().stream().map(stocksMapper::toDto).toList();
//                    company.setStocks(stocks);
//                    return company;
//                })
//                .map(CompanyDto::getStocks);
    }

    @Override
    public Mono<StockDto> save(Stock stock) {
        return stocksRepository.save(stock).map(stocksMapper::toDto);
    }
}
