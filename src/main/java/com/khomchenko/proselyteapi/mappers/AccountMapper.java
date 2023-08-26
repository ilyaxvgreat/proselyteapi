package com.khomchenko.proselyteapi.mappers;

import com.khomchenko.proselyteapi.dto.AccountDto;
import com.khomchenko.proselyteapi.models.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDto toDto(Account account);

}
