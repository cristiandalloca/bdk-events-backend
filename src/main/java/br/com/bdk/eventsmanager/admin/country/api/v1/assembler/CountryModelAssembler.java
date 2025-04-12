package br.com.bdk.eventsmanager.admin.country.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.country.api.v1.model.CountryModel;
import br.com.bdk.eventsmanager.admin.country.domain.model.Country;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CountryModelAssembler implements ModelAssembler<Country, CountryModel> {

    @NonNull
    @Override
    public CountryModel toModel(@NonNull Country country) {
        return CountryModel.builder()
                .id(country.getExternalId())
                .name(country.getName())
                .acronym(country.getAcronym())
                .build();
    }
}
