// src/main/java/com/VolunTrack/demo/iam/infrastructure/repositories/UserRepository.java

package com.VolunTrack.demo.IAM.Infrastructure.Repositories;

import com.VolunTrack.demo.IAM.Domain.Model.Aggregates.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}