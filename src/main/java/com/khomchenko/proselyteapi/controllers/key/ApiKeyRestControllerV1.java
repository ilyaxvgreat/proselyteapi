package com.khomchenko.proselyteapi.controllers.key;

import com.khomchenko.proselyteapi.dto.AccountDto;
import com.khomchenko.proselyteapi.dto.ApiKeyDto;
import com.khomchenko.proselyteapi.services.ApiKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/get-api-key")
@RequiredArgsConstructor
public class ApiKeyRestControllerV1 {

    private final ApiKeyService apiKeyService;

    @PostMapping
    public Mono<ApiKeyDto> getApiKey(@RequestBody AccountDto accountDto) {
        return apiKeyService.getApiKeyByAccountUsernameAndPassword(
                accountDto.getUsername(), accountDto.getPassword());
    }
}
