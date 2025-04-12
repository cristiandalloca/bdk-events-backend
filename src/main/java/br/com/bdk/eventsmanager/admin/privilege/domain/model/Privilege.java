package br.com.bdk.eventsmanager.admin.privilege.domain.model;

import br.com.bdk.eventsmanager.core.entity.ExposedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import static org.apache.commons.lang3.StringUtils.substringAfter;

@Getter
@Setter
@Entity
@Table(name = "privilege")
@AllArgsConstructor
@NoArgsConstructor
public class Privilege extends ExposedEntity {

    private static final char SEPARATOR_MAJOR_NAME = '.';
    private static final char SEPARATOR_DESCRIPTION = '-';

    @NotBlank
    @Size(max = 50)
    @Column(name = "name", length = 50, unique = true, nullable = false)
    private String name;

    @NotBlank
    @Size(max = 255)
    @Column(name = "description", nullable = false)
    private String description;

    public Privilege(String externalId) {
        super(externalId);
    }

    public String getMajorName() {
        if (StringUtils.isBlank(this.getName())) {
            return StringUtils.EMPTY;
        }

        return StringUtils.substringBefore(this.getName(), SEPARATOR_MAJOR_NAME);
    }

    public String getMajorDescription() {
        if (StringUtils.isBlank(this.getDescription())) {
            return StringUtils.EMPTY;
        }

        return StringUtils.substringBefore(this.getDescription(), SEPARATOR_DESCRIPTION).trim();
    }

    public String getMinorDescription() {
        if (StringUtils.isBlank(this.getDescription())) {
            return StringUtils.EMPTY;
        }

        var minorDescription = substringAfter(this.getDescription(), SEPARATOR_DESCRIPTION).trim();
        if (StringUtils.isBlank(minorDescription)) {
            return this.getDescription();
        }

        return minorDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Privilege that)) return false;
        return super.getExternalId() != null && super.getExternalId().equals(that.getExternalId());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
