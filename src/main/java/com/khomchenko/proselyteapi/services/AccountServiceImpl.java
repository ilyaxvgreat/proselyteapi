package com.khomchenko.proselyteapi.services;

import com.khomchenko.proselyteapi.dto.AccountDto;
import com.khomchenko.proselyteapi.mappers.AccountMapper;
import com.khomchenko.proselyteapi.models.Account;
import com.khomchenko.proselyteapi.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final ReactiveHashOperations<String, String, Account> hashOperations;

    @Override
    public Mono<AccountDto> saveAccount(String username, String password) {
        return accountRepository.findAccountByUsername(username)
                .flatMap(account -> Mono.error(new RuntimeException("Account is present")))
                .switchIfEmpty(
                        Mono.defer(() -> {
                            Account account = new Account();
                            account.setUsername(username);
                            account.setPassword(password);
                            account.setApiKey(UUID.randomUUID().toString());
                            return accountRepository.save(account);
                        }))
                .flatMap(o -> Mono.just(accountMapper.toDto((Account) o)));
    }

    @Override
    public Mono<Account> getAccountByUsernameAndPassword(String username, String password) {
        return accountRepository.findAccountByUsernameAndPassword(username, password);
    }

    @Override
    public Mono<Account> getAccountByApiKey(String apiKey) {
        return accountRepository.findAccountByApiKey(apiKey);
    }
}
