package br.com.bdk.eventsmanager.admin.company.parameter.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.company.parameter.api.v1.model.CompanyLogoModel;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class CompanyLogoModelAssembler implements ModelAssembler<URL, CompanyLogoModel> {

    @NonNull
    @Override
    public CompanyLogoModel toModel(@NonNull URL url) {
        return CompanyLogoModel.builder()
                .logoURL(url.toString())
                .build();
    }

}
