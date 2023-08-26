package com.khomchenko.proselyteapi.dto;

import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.util.List;

@Data
public class CompanyDto {

    private String name;

    private String code;

    @Transient
    private List<StockDto> stocks;

}
