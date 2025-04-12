package br.com.bdk.eventsmanager.core.api.assembler;

import br.com.bdk.eventsmanager.core.api.model.PageModel;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.Collection;

@Validated
public interface ModelAssembler<S, D> {

    D toModel(@NotNull S source);

    default Collection<D> toCollectionModel(@NotNull Collection<S> sourceCollection) {
        return sourceCollection.stream()
                .map(this::toModel)
                .toList();
    }

    default PageModel<D> toPageModel(@NotNull Page<S> sourcePage) {
        var content = sourcePage.getContent();
        var destinationContent = this.toCollectionModel(content);
        var newPage = new PageImpl<>(new ArrayList<>(destinationContent), sourcePage.getPageable(), sourcePage.getTotalElements());
        return new PageModel<>(newPage);
    }
}
