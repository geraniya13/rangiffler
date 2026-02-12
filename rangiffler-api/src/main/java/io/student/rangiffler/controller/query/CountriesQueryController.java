package io.student.rangiffler.controller.query;

import io.student.rangiffler.model.Country;
import io.student.rangiffler.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@PreAuthorize("isAuthenticated()")
public class CountriesQueryController {

    private final CountryService countryService;

    @Autowired
    public CountriesQueryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @QueryMapping
    public List<Country> countries() {
        return countryService.getAllCountries();
    }
}
