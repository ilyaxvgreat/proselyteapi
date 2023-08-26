package com.khomchenko.proselyteapi.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Table(name = "accounts")
@Setter
public class Account implements Serializable {

    @Id
    private Integer id;

    private String username;

    private String password;


    private String apiKey;
}
