package io.student.rangiffler.service;

import io.student.rangiffler.data.entity.CountryEntity;
import io.student.rangiffler.data.repository.CountryRepository;
import io.student.rangiffler.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> getAllCountries() {
        //                            return new Country(
        //                                    countryEntity.getCode(),
        //                                    countryEntity.getName(),
        //                                    Base64.getEncoder().encodeToString(countryEntity.getFlag())
        //                            );
        return countryRepository.findAll()
                .stream()
                .map(CountryEntity::toDto
                ).toList();
    }
}
