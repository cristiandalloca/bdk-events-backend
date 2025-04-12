package br.com.bdk.eventsmanager.core.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
public class ProblemModel {

    private static final String DEFAULT_USER_MESSAGE = "Ocorreu um erro interno, por favor contate o administrador do sistema";

    @Schema(example = "404", description = "Código de status HTTP de resposta da requisição")
    @Builder.Default
    private int status = HttpStatus.BAD_REQUEST.value();

    @Schema
    @Builder.Default
    private ProblemType type = ProblemType.INVALID_DATA;

    @Schema(example = "Invalid data", description = "Título do problema")
    private String title;

    @Schema(example = "One or more fields are invalid. Please enter correctly and try again", description = "Detalhes do problema")
    private String detail;

    @Builder.Default
    @Schema(example = "One or more fields are invalid. Please enter correctly and try again", description = "Mensagem amigável que pode ser exibida para o usuário, referente ao problema")
    private String userMessage = DEFAULT_USER_MESSAGE;

    @Schema(example = "2024-07-15T11:21:50.902245498Z", description = "Data e hora no formato UTC que ocorreu o problema")
    @Builder.Default
    private OffsetDateTime timestamp = OffsetDateTime.now();

    @ArraySchema
    private List<FieldModel> fields;

    @Builder
    @Getter
    @Schema(name = "ProblemField", description = "Detalhes do erro por parâmetros de entrada", nullable = true)
    public static class FieldModel {

        @Schema(example = "stateId", description = "Nome do parâmetro ou propriedade que ocasionou o problema")
        private String name;

        @Schema(example = "The price is invalid", description = "Descrição amigável do problema ocorrido relacionado ao parâmetro ou propriedade")
        private String userMessage;
    }
}
