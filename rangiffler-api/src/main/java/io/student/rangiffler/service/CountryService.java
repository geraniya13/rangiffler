package io.student.rangiffler.service;

import io.student.rangiffler.data.repository.CountryRepository;
import io.student.rangiffler.model.Country;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    public List<Country> getAllCountries() {
        return countryRepository.findAll()
                .stream()
                .map(Country::toDto)
                .toList();
    }
}
