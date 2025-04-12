package br.com.bdk.eventsmanager.admin.invitationtype.domain.model;

import br.com.bdk.eventsmanager.core.entity.ExposedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "invitation_type")
@NoArgsConstructor
public class InvitationType extends ExposedEntity {

    @NotBlank
    @Size(max = 50)
    @Column(name = "name")
    private String name;

    public InvitationType(String externalId) {
        super(externalId);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
