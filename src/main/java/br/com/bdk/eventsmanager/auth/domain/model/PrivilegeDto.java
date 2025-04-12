package br.com.bdk.eventsmanager.auth.domain.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class PrivilegeDto {

    @EqualsAndHashCode.Include
    private Long id;

    private String externalId;

    private String name;

    private String description;

}
