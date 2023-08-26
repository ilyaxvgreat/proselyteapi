package com.khomchenko.proselyteapi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class StockDto {

    private String code;

    private Integer price;

    private LocalDateTime createdDate;

}
