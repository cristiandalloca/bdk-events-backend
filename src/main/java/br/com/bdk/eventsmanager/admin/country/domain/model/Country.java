package br.com.bdk.eventsmanager.admin.country.domain.model;

import br.com.bdk.eventsmanager.core.entity.ExposedEntity;
import br.com.bdk.eventsmanager.core.validator.OnlyLetters;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;
import org.hibernate.annotations.ColumnTransformer;

@Getter
@Setter
@Entity
@Table(name = "country")
@AllArgsConstructor
@NoArgsConstructor
public class Country extends ExposedEntity {
    
    @NotBlank
    @Size(max = 255)
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotBlank
    @Size(max = 3)
    @OnlyLetters
    @Column(name = "acronym", length = 3, nullable = false)
    @ColumnTransformer(write = "upper(?)")
    private String acronym;

    public Country(String externalId) {
        super(externalId);
    }

    public String getAcronym() {
        if (this.acronym == null) {
            return Strings.EMPTY;
        }

        return this.acronym.toUpperCase();
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
