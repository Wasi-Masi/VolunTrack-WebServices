package com.VolunTrack.demo.Shared.Infrastructure.Persistence.JPA.Repositories;

import com.VolunTrack.demo.Shared.Domain.Repositories.IUnitOfWork;
import org.springframework.stereotype.Component;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

/**
 * Implementation of the Unit of Work pattern using JPA's EntityManager.
 * This component manages transaction boundaries to ensure atomicity of business operations.
 */
@Component
public class UnitOfWork implements IUnitOfWork {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Commits the current transaction.
     * {@code @Transactional} annotation ensures that this method is executed within a transaction context.
     * Spring handles the transaction lifecycle (begin, commit, rollback on error).
     *
     * @return A CompletableFuture that completes when the transaction is committed.
     */
    @Override
    @Transactional
    public CompletableFuture<Void> complete() {
        return CompletableFuture.completedFuture(null);
    }
}