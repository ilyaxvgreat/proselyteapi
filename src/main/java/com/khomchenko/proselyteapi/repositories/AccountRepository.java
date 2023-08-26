package com.khomchenko.proselyteapi.repositories;

import com.khomchenko.proselyteapi.models.Account;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface AccountRepository extends R2dbcRepository<Account, Integer> {
    Mono<Account> findAccountByUsername(String username);

    Mono<Account> findAccountByApiKey(String apiKey);

    Mono<Account> findAccountByUsernameAndPassword(String username, String password);
}
