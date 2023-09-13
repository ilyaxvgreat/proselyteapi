package com.khomchenko.proselyteapi.controllers.accounts;

import com.khomchenko.proselyteapi.controllers.accounts.RegisterAccountRestControllerV1;
import com.khomchenko.proselyteapi.dto.AccountDto;
import com.khomchenko.proselyteapi.models.Account;
import com.khomchenko.proselyteapi.repositories.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@PropertySource("/application-test.yml")
public class RegisterAccountRestControllerV1Test {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void registerAccount_usernameIsNotPresent_registerAccount() {
        AccountDto accountDto = new AccountDto();
        accountDto.setUsername("testUser");
        accountDto.setPassword("testPassword");

        webTestClient.post()
                .uri("/v1/register")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(accountDto))
                .exchange()
                .expectStatus().isOk()
                .expectBody(AccountDto.class)
                .value(returnedAccountDto -> {
                    assertThat(returnedAccountDto.getUsername()).isEqualTo(accountDto.getUsername());
                });
    }

    @Test
    public void registerAccount_usernameIsPresent_errorResponseReturn() {
        Account existedAccount = new Account();
        existedAccount.setUsername("existedUsername");
        existedAccount.setPassword("password");
        existedAccount.setApiKey(UUID.randomUUID().toString());
        accountRepository.save(existedAccount).subscribe();

        AccountDto accountDto = new AccountDto();
        accountDto.setUsername("existedUsername");
        accountDto.setPassword("password");

        webTestClient.post()
                .uri("/v1/register")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(accountDto))
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody().jsonPath("$.message").isEqualTo("Account is present");
    }
}
