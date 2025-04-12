package br.com.bdk.eventsmanager.core.api.disassembler;

import org.springframework.lang.NonNull;

import java.util.Collection;

public interface InputDisassembler<S, D>  {

    D toEntity(@NonNull S input);

    default Collection<D> toCollectionEntity(Collection<S> collectionInput) {
        return collectionInput.stream()
                .map(this::toEntity)
                .toList();
    }

    void copyToEntity(@NonNull S input, @NonNull D output);
}
