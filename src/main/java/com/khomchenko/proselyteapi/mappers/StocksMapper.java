package com.khomchenko.proselyteapi.mappers;

import com.khomchenko.proselyteapi.dto.StockDto;
import com.khomchenko.proselyteapi.models.Stock;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StocksMapper {

    StockDto toDto(Stock stock);

}
