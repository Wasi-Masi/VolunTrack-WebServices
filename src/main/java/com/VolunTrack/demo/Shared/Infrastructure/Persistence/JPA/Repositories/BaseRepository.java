package com.VolunTrack.demo.Shared.Infrastructure.Persistence.JPA.Repositories;

import com.VolunTrack.demo.Shared.Domain.Repositories.IBaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;


/**
 * Base implementation for repositories providing common CRUD operations.
 * This class acts as a bridge between the domain's IBaseRepository and Spring Data JPA's JpaRepository.
 * {@code @NoRepositoryBean} ensures Spring Data JPA does not create an instance of this interface directly.
 *
 * @param <T> The type of the aggregate/entity.
 * @param <ID> The type of the aggregate/entity's ID.
 */
@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID>, IBaseRepository<T, ID> {

}