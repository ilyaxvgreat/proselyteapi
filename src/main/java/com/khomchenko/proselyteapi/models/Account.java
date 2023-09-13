package com.khomchenko.proselyteapi.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Table(name = "accounts")
@Setter
public class Account implements Serializable {

    @Id
    private Integer id;

    @Column("username")
    private String password;
    @Column("password")

    private String username;

    @Column("api_key")

    private String apiKey;
}
