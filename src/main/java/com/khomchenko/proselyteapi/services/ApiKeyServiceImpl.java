package com.khomchenko.proselyteapi.services;

import com.khomchenko.proselyteapi.dto.ApiKeyDto;
import com.khomchenko.proselyteapi.exceptions.ApiKeyNotFoundException;
import com.khomchenko.proselyteapi.models.Account;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiKeyServiceImpl implements ApiKeyService {

    private final AccountService accountService;
    private final ReactiveHashOperations<String, String, Account> hashOperations;

    @SneakyThrows
    @Override
    public Mono<ApiKeyDto> getApiKeyByAccountUsernameAndPassword(String username, String password) {
        return accountService.getAccountByUsernameAndPassword(username, password)
                .switchIfEmpty(Mono.defer(() -> {
                    throw new ApiKeyNotFoundException("No Key for pair username:password");
                }))
                .flatMap(account -> Mono.just(new ApiKeyDto(account.getApiKey())));
    }

    @Override
    public Mono<Account> getAccountByApiKey(String apiKey) {
        return hashOperations.get("API_KEY", apiKey).doOnSuccess(account -> log.info("found key in cache"))
                .switchIfEmpty(
                        Mono.defer(() -> {
                                    log.info("going in db");
                                    return accountService.getAccountByApiKey(apiKey);
                                })
                                .switchIfEmpty(
                                        Mono.error(new ApiKeyNotFoundException("API key " + apiKey + " not found")))
                                .flatMap(account -> hashOperations.put(
                                        "API_KEY", account.getApiKey(), account).thenReturn(account)));
    }

}
