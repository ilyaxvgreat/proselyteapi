package com.khomchenko.proselyteapi.controllers.companies;

import com.khomchenko.proselyteapi.models.Company;
import com.khomchenko.proselyteapi.services.CompaniesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static com.khomchenko.proselyteapi.util.constants.ControllerConstants.CompaniesController.COMPANIES;

@RestController
@RequestMapping(COMPANIES)
@RequiredArgsConstructor
public class CompaniesRestControllerV1 {

    private final CompaniesService companiesService;

    @GetMapping
    public Flux<Company> getAll(){
        return companiesService.getAll();
    }
}
