package com.VolunTrack.demo.Shared.Domain.Repositories;


/**
 * Base repository interface providing common CRUD operations.
 * This interface defines the core functionalities for interacting with any aggregate or entity.
 * In a Spring Data JPA context, this interface primarily serves as a marker or for
 * defining custom non-standard repository methods, as standard CRUD are inherited from Spring Data JPA.
 *
 * @param <T> The type of the aggregate/entity.
 * @param <ID> The type of the aggregate/entity's ID.
 */
public interface IBaseRepository<T, ID> {

}