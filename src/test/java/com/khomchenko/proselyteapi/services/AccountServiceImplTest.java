package com.khomchenko.proselyteapi.services;

import com.khomchenko.proselyteapi.dto.AccountDto;
import com.khomchenko.proselyteapi.mappers.AccountMapper;
import com.khomchenko.proselyteapi.models.Account;
import com.khomchenko.proselyteapi.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountService;
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveAccount_accountUsernameIsNew_accountShouldBeCreated() {
        // Arrange
        String username = "newUsername";
        String password = "newPassword";
        AccountDto expectedAccountDto = new AccountDto(); // Replace with your expected DTO

        when(accountRepository.findAccountByUsername(username))
                .thenReturn(Mono.empty()); // Simulate that account does not exist
        when(accountRepository.save(any(Account.class)))
                .thenReturn(Mono.just(new Account())); // Simulate successful account save
        when(accountMapper.toDto(any(Account.class)))
                .thenReturn(expectedAccountDto);

        // Act
        Mono<AccountDto> resultMono = accountService.saveAccount(username, password);

        // Assert
        StepVerifier.create(resultMono)
                .expectNext(expectedAccountDto) // Expect the returned DTO
                .verifyComplete();

    }

    @Test
    public void saveAccount_accountUsernameIsPresent_exceptionThrows() {
        String username = "existedUser";
        String password = "newPassword";
        when(accountRepository.findAccountByUsername(username))
                .thenReturn(Mono.just(new Account()));

        Mono<AccountDto> resultMono = accountService.saveAccount(username, password);

        StepVerifier.create(resultMono)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    public void getAccountByUsernameAndPassword() {
        String username = "testUser";
        String password = "testPassword";
        Account expectedAccount = new Account();
        when(accountRepository.findAccountByUsernameAndPassword(username, password))
                .thenReturn(Mono.just(expectedAccount));

        Mono<Account> resultMono = accountService.getAccountByUsernameAndPassword(username, password);

        StepVerifier.create(resultMono)
                .expectNext(expectedAccount)
                .verifyComplete();
    }

    @Test
    public void getAccountByApiKey() {
        String apiKey = "testApiKey";
        Account expectedAccount = new Account();
        when(accountRepository.findAccountByApiKey(apiKey))
                .thenReturn(Mono.just(expectedAccount));

        Mono<Account> resultMono = accountService.getAccountByApiKey(apiKey);

        StepVerifier.create(resultMono)
                .expectNext(expectedAccount)
                .verifyComplete();
    }
}