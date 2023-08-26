package com.khomchenko.proselyteapi.mappers;

import com.khomchenko.proselyteapi.dto.CompanyDto;
import com.khomchenko.proselyteapi.models.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    CompanyDto toDto(Company company);
}
