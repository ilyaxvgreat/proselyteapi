package com.khomchenko.proselyteapi.controllers.stocks;

import com.khomchenko.proselyteapi.dto.CompanyDto;
import com.khomchenko.proselyteapi.dto.StockDto;
import com.khomchenko.proselyteapi.models.Account;
import com.khomchenko.proselyteapi.models.Company;
import com.khomchenko.proselyteapi.models.Stock;
import com.khomchenko.proselyteapi.repositories.AccountRepository;
import com.khomchenko.proselyteapi.repositories.CompanyRepository;
import com.khomchenko.proselyteapi.repositories.StocksRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@PropertySource("/application-test.yml")
class StocksControllerTest {

    private final String EXISTED_COMPANY_CODE = "TEST";
    private final String NON_EXISTED_COMPANY_CODE = "WRONG";

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private StocksRepository stocksRepository;

    Account existedAccount;

    @BeforeAll
    public void setUp() {
        existedAccount = new Account();
        existedAccount.setUsername("existedUsername");
        existedAccount.setPassword("password");
        existedAccount.setApiKey(UUID.randomUUID().toString());
        existedAccount = accountRepository.save(existedAccount).block();

        Company company = Company.builder()
                .code(EXISTED_COMPANY_CODE)
                .name("name")
                .build();

        companyRepository.save(company).block();

        Stock stock = Stock.builder()
                .price(10)
                .code(EXISTED_COMPANY_CODE)
                .build();

        stocksRepository.save(stock).block();
    }

    @Test
    public void companyWithCodeIsNotPresent_getAllQuotes_returnErrorResponse() {
        webTestClient.get()
                .uri("/v1/stocks/" + NON_EXISTED_COMPANY_CODE + "/quote")
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody().jsonPath("$.message").isEqualTo("NO SUCH CODE");
    }

    @Test
    public void companyWithCodeIsPresent_getAllQuotes_returnStocks() {
        List<CompanyDto> responseBody = webTestClient.get()
                .uri("/v1/stocks/" + EXISTED_COMPANY_CODE + "/quote")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(CompanyDto.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody).hasSize(1);
    }
}