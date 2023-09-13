package com.khomchenko.proselyteapi.controllers.companies;

import com.khomchenko.proselyteapi.models.Account;
import com.khomchenko.proselyteapi.models.Company;
import com.khomchenko.proselyteapi.repositories.AccountRepository;
import com.khomchenko.proselyteapi.repositories.CompanyRepository;
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
class CompaniesRestControllerV1Test {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void getAll_returnAllCompanies(){
        Account existedAccount = new Account();
        existedAccount.setUsername("existedUsername");
        existedAccount.setPassword("password");
        existedAccount.setApiKey(UUID.randomUUID().toString());
        Account newAccount = accountRepository.save(existedAccount).block();

        assertNotNull(newAccount);
        assertNotNull(newAccount.getApiKey());

        Company company = Company.builder()
                .code("TEST")
                .name("test")
                .build();
        companyRepository.save(company).block();

        List<Company> responseBody = webTestClient.get()
                .uri("/v1/companies?api_key=" + newAccount.getApiKey())
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Company.class)
                .returnResult()
                .getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody).hasSize(1);
    }
}