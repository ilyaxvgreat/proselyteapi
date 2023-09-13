package com.khomchenko.proselyteapi.controllers.key;

import com.khomchenko.proselyteapi.dto.AccountDto;
import com.khomchenko.proselyteapi.models.Account;
import com.khomchenko.proselyteapi.repositories.AccountRepository;
import org.junit.jupiter.api.Test;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@PropertySource("/application-test.yml")
class ApiKeyRestControllerV1Test {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private AccountRepository accountRepository;


    @Test
    public void getApiKey_usernameIsNotPresent_errorResponseReturn() {
        AccountDto accountDto = new AccountDto();
        accountDto.setUsername("testUser");
        accountDto.setPassword("testPassword");

        webTestClient.post()
                .uri("/v1/get-api-key")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(accountDto))
                .exchange().expectStatus().is5xxServerError()
                .expectBody().jsonPath("$.message").isEqualTo("No Key for pair username:password");
    }

    @Test
    public void getApiKey_usernameIsPresent_apiKeyResponseReturn() {
        Account existedAccount = new Account();
        existedAccount.setUsername("testUser");
        existedAccount.setPassword("testPassword");
        existedAccount.setApiKey(UUID.randomUUID().toString());
        accountRepository.save(existedAccount).subscribe();

        webTestClient.post()
                .uri("/v1/get-api-key")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(existedAccount))
                .exchange().expectStatus().is2xxSuccessful()
                .expectBody().jsonPath("$.apiKey").isEqualTo(existedAccount.getApiKey());
    }
}