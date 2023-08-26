package com.khomchenko.proselyteapi.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table(name = "stocks")
@Builder
public class Stock {

    @Id
    private Integer id;

    private Integer price;
    @Column("created_date")
    private LocalDateTime createdDate;
    private String code;

}
