package br.com.bdk.eventsmanager.admin.country.api.v1.disassembler;

import br.com.bdk.eventsmanager.admin.country.api.v1.model.input.CountryInput;
import br.com.bdk.eventsmanager.admin.country.domain.model.Country;
import br.com.bdk.eventsmanager.core.api.disassembler.InputDisassembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class CountryInputDisassembler implements InputDisassembler<CountryInput, Country> {

    @Override
    public Country toEntity(@NonNull CountryInput input) {
        return new Country(input.getName(), input.getAcronym());
    }

    @Override
    public void copyToEntity(@NonNull CountryInput input, @NonNull Country country) {
        country.setName(input.getName());
        country.setAcronym(input.getAcronym());
    }
}
