package com.khomchenko.proselyteapi.services;

import com.khomchenko.proselyteapi.dto.AccountDto;
import com.khomchenko.proselyteapi.models.Account;
import reactor.core.publisher.Mono;

public interface AccountService {

    Mono<AccountDto> saveAccount(String username, String password);

    Mono<Account> getAccountByUsernameAndPassword(String username, String password);

    Mono<Account> getAccountByApiKey(String apiKey);
}
