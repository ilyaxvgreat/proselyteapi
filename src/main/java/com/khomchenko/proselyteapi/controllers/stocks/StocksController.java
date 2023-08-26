package com.khomchenko.proselyteapi.controllers.stocks;

import com.khomchenko.proselyteapi.dto.StockDto;
import com.khomchenko.proselyteapi.services.StocksService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.khomchenko.proselyteapi.util.constants.ControllerConstants.StocksController.STOCKS;

@RestController
@RequestMapping(STOCKS)
@RequiredArgsConstructor
public class StocksController {

    private final StocksService stocksService;

    @GetMapping("/{stock_code}/quote")
    public Mono<List<StockDto>> getAllQuotes(@PathVariable("stock_code") String stockCode){
        return stocksService.getAllCompanyStocks(stockCode);
    }

}
