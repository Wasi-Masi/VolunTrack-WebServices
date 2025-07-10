// src/main/java/com/VolunTrack/demo/Shared/Infrastructure/Persistence/UnitOfWork.java
package com.VolunTrack.demo.Shared.Infrastructure.Persistence;

import com.VolunTrack.demo.Shared.Domain.Repositories.IUnitOfWork;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class UnitOfWork implements IUnitOfWork {

    @Override
    public CompletableFuture<Void> complete() {
        // En una aplicación síncrona, esta implementación es suficiente si el servicio
        // ya tiene la anotación @Transactional.
        return CompletableFuture.completedFuture(null);
    }
}