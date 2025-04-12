package br.com.bdk.eventsmanager.admin.privilege.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.privilege.api.v1.model.PrivilegeModel;
import br.com.bdk.eventsmanager.admin.privilege.domain.model.Privilege;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class PrivilegeModelAssembler implements ModelAssembler<Privilege, PrivilegeModel> {

    @NonNull
    @Override
    public PrivilegeModel toModel(@NonNull Privilege privilege) {
        throw new UnsupportedOperationException();
    }

    @NonNull
    @Override
    public Collection<PrivilegeModel> toCollectionModel(@NonNull Collection<Privilege> sourceCollection) {
        var models = new ArrayList<PrivilegeModel>();
        sourceCollection.stream()
                .map(privilege -> PrivilegeModel.builder()
                        .name(privilege.getMajorName())
                        .description(privilege.getMajorDescription())
                        .build())
                .distinct()
                .forEach(model -> {
                    sourceCollection.stream()
                            .filter(privilege -> model.getName().equals(privilege.getMajorName()))
                            .distinct()
                            .forEach(grants -> {
                                var grant = PrivilegeModel.PrivilegeGrantModel.builder()
                                        .id(grants.getExternalId())
                                        .description(grants.getMinorDescription())
                                        .name(grants.getName())
                                        .build();
                                model.addGrant(grant);
                            });
                    models.add(model);
                });
        return models;
    }
}
