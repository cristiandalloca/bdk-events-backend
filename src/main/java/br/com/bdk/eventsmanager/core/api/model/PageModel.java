package br.com.bdk.eventsmanager.core.api.model;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PageModel<T> {

    @ArraySchema
    private final List<T> content;

    @Schema(example = "25", description = "Quantidade de registros solicitados da página")
    private final int size;

    @Schema(example = "0", description = "Número da página atual")
    private final int page;

    @Schema(example = "100", description = "Total de elementos, sem considerar paginação")
    private final long totalElements;

    @Schema(example = "15", description = "Total de páginas, considerando a quantidade de registros solicitados")
    private final long totalPages;

    public PageModel(Page<T> page) {
        this.content = page.getContent();
        this.size = page.getSize();
        this.page = page.getNumber();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
    }

}
