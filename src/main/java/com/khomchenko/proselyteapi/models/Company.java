package com.khomchenko.proselyteapi.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table(name = "companies")
@Builder(toBuilder = true)
@EqualsAndHashCode
public class Company {

    @Id
    private Integer id;

    private String name;

    private String code;

}
