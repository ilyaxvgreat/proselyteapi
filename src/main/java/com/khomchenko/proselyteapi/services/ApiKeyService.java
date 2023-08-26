package com.khomchenko.proselyteapi.services;

import com.khomchenko.proselyteapi.dto.ApiKeyDto;
import com.khomchenko.proselyteapi.models.Account;
import reactor.core.publisher.Mono;

public interface ApiKeyService {

    Mono<ApiKeyDto> getApiKeyByAccountUsernameAndPassword(String username, String password);

    Mono<Account> getAccountByApiKey(String apiKey);
}
