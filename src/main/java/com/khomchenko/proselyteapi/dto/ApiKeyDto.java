package com.khomchenko.proselyteapi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ApiKeyDto implements Serializable {
    private String apiKey;

    public ApiKeyDto(String apiKey) {
        this.apiKey = apiKey;
    }

}
