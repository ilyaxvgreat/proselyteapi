package com.khomchenko.proselyteapi.jobs;

import com.khomchenko.proselyteapi.dto.StockDto;
import com.khomchenko.proselyteapi.models.Company;
import com.khomchenko.proselyteapi.models.Stock;
import com.khomchenko.proselyteapi.services.CompaniesService;
import com.khomchenko.proselyteapi.services.StocksService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveListOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateApiKeyCacheJob {

    private final ReactiveListOperations<String, StockDto> stocksListOperations;
    private final StocksService stocksService;
    //    private final ReactiveRedisOperations<String, StockDto> redisStocksOperations;
    private final CompaniesService companiesService;
    private final ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

    @PostConstruct
    public void init() {
        log.info("Pushing all Stocks in Redis Cache");
        try {

            companiesService.getAll()
                    .map(Company::getCode)
                    .map(companyCode -> stocksService.getAllCompanyStocks(companyCode)
                            .map(stockDtos -> {
                                log.info("Pushing in Redis: {}, records quantity {}", companyCode, stockDtos.size());
                                return stocksListOperations.rightPushAll(companyCode, stockDtos);
                            }))
                    .subscribe();
        } catch (Exception e) {
            log.error("Error while pushing in redis cache on startup", e);
        }
    }

    @SneakyThrows
    @Scheduled(cron = "*/10 * * * * *")
    public void updateApiKeyCacheJobRun() {
        companiesService.getAll()
                .map(Company::getCode)
                .flatMap(companyCode -> {
                    Stock stock = Stock.builder()
                            .code(companyCode)
                            .price(threadLocalRandom.nextInt(10, 100))
                            .build();
                    return stocksService.save(stock);
                })
                .flatMap(stockDto -> stocksListOperations.rightPush(stockDto.getCode(), stockDto))
                .subscribe();
    }

}
