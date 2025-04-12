package br.com.bdk.eventsmanager.core.springdoc;

import br.com.bdk.eventsmanager.core.api.exceptionhandler.ProblemModel;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.swagger.v3.oas.models.PathItem.HttpMethod.GET;
import static io.swagger.v3.oas.models.PathItem.HttpMethod.POST;
import static io.swagger.v3.oas.models.PathItem.HttpMethod.PUT;
import static java.lang.String.valueOf;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Profile({"dev", "staging"})
@OpenAPIDefinition(
        servers = {
                @Server(url = "/", description = "Default Server URL")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "Utilizar o token JWT. O token JWT pode ser obtido a partir do endpoint '/v1/auth/token'",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@Configuration
public class SpringDocConfiguration implements WebMvcConfigurer {

    private static final String SWAGGER_UI_HTML = "/swagger-ui.html";

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", SWAGGER_UI_HTML);
        registry.addRedirectViewController("/swagger", SWAGGER_UI_HTML);
        registry.addRedirectViewController("/swagger-ui", SWAGGER_UI_HTML);
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("BDK Events Manager API")
                        .version("v1")
                        .description("REST API for BDK Events Manager Application"));
    }

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> {
            openApi.getComponents().getSchemas().putAll(this.generateProblemSchema());
            openApi.getPaths().values().forEach(path -> path.readOperationsMap().forEach((httpMethod, operation) -> {
                var responses = operation.getResponses();
                if (this.isGetOrPostOrPut(httpMethod)) {
                    this.addApiResponse(responses);
                } else {
                    this.addApiResponseIfNecessary(responses, valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), new ApiResponse().description(SpringDocConstantsUtil.API_RESPONSE_INTERNAL_ERROR_DESCRIPTION));
                }
            }));
        };
    }

    private void addApiResponse(ApiResponses responses) {
        this.addApiResponseIfNecessary(responses, valueOf(HttpStatus.NOT_FOUND.value()), new ApiResponse().description(SpringDocConstantsUtil.API_RESPONSE_RESOURCE_NOT_FOUND_DESCRIPTION));
        this.addApiResponseIfNecessary(responses, valueOf(HttpStatus.BAD_REQUEST.value()), new ApiResponse().description(SpringDocConstantsUtil.API_RESPONSE_BAD_REQUEST_DESCRIPTION));
        this.addApiResponseIfNecessary(responses, valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), new ApiResponse().description(SpringDocConstantsUtil.API_RESPONSE_INTERNAL_ERROR_DESCRIPTION));
        this.addApiResponseIfNecessary(responses, valueOf(HttpStatus.UNAUTHORIZED.value()), new ApiResponse().description(SpringDocConstantsUtil.API_RESPONSE_UNAUTHORIZED_DESCRIPTION));
    }

    private void addApiResponseIfNecessary(ApiResponses responses, String httpStatusCode, ApiResponse apiResponse) {
        if (responses.containsKey(httpStatusCode)) {
            ApiResponse response = responses.get(httpStatusCode);
            if (!valueOf(HttpStatus.NOT_FOUND.value()).equals(httpStatusCode)) {
                response.setDescription(apiResponse.getDescription());
            }
            if (!valueOf(HttpStatus.OK.value()).equals(httpStatusCode)) {
                response.setContent(this.getContentProblemModelDefault());
            }
            responses.put(httpStatusCode, response);
        } else {
            apiResponse.setContent(this.getContentProblemModelDefault());
            responses.put(httpStatusCode, apiResponse);
        }
    }

    private Content getContentProblemModelDefault() {
        return new Content().addMediaType(APPLICATION_JSON_VALUE, new MediaType().schema(new Schema<ProblemModel>().$ref(ProblemModel.class.getSimpleName())));
    }

    private boolean isGetOrPostOrPut(PathItem.HttpMethod httpMethod) {
        return List.of(GET, POST, PUT).contains(httpMethod);
    }

    @SuppressWarnings("java:S3740")
    private Map<String, Schema> generateProblemSchema() {
        final Map<String, Schema> schemaMap = new HashMap<>();

        var problemSchema = ModelConverters.getInstance().read(ProblemModel.class);
        var problemFieldSchema = ModelConverters.getInstance().read(ProblemModel.FieldModel.class);

        schemaMap.putAll(problemSchema);
        schemaMap.putAll(problemFieldSchema);

        return schemaMap;
    }

}
