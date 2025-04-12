package br.com.bdk.eventsmanager.admin.address.domain.model.viacep;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressResponse {

    @JsonProperty("logradouro")
    private String street;

    @JsonProperty("bairro")
    private String neighborhood;

    @JsonProperty("localidade")
    private String cityName;

    @JsonProperty("uf")
    private String stateAcronym;

    @JsonProperty("estado")
    private String stateName;

    public boolean existsCityAndStateInfo() {
        return StringUtils.isNotBlank(cityName)
                && StringUtils.isNotBlank(stateName)
                && StringUtils.isNotBlank(stateAcronym);
    }

}
