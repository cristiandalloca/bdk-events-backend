package br.com.bdk.eventsmanager.admin.company.parameter.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.company.parameter.api.v1.model.CompanySignatureModel;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class CompanySignatureModelAssembler implements ModelAssembler<URL, CompanySignatureModel> {

    @NonNull
    @Override
    public CompanySignatureModel toModel(@NonNull URL url) {
        return CompanySignatureModel.builder()
                .signatureURL(url.toString())
                .build();
    }

}
