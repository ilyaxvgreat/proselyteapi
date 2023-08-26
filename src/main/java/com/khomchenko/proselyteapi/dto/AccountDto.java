package com.khomchenko.proselyteapi.dto;

import lombok.Data;

@Data
public class AccountDto {

    private Integer id;

    private String username;

    private String password;

    private String apiKey;

}
