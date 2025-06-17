package com.VolunTrack.demo.Shared.Domain.Repositories;

import java.util.concurrent.CompletableFuture;

/**
 * Interface for the Unit of Work pattern.
 * It provides a contract for managing transaction boundaries, ensuring that
 * all operations within a business transaction are treated as a single unit.
 */
public interface IUnitOfWork {
    /**
     * Completes the unit of work by committing all pending changes to the data store.
     * This method typically involves flushing the persistence context and committing the transaction.
     *
     * @return A CompletableFuture that completes when the unit of work is successfully completed.
     */
    CompletableFuture<Void> complete();
}