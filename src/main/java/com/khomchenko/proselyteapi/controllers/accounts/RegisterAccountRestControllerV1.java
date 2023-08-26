package com.khomchenko.proselyteapi.controllers.accounts;

import com.khomchenko.proselyteapi.dto.AccountDto;
import com.khomchenko.proselyteapi.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/register")
@RequiredArgsConstructor
public class RegisterAccountRestControllerV1 {

    private final AccountService accountService;

    @PostMapping
    public Mono<AccountDto> registerAccount(@RequestBody AccountDto accountDto) {
        return accountService.saveAccount(accountDto.getUsername(), accountDto.getPassword());
    }
}
