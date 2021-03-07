package com.mss.accountmanagementapi.user.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository to do central data operations.
 * Since JPA expects only one entity and one repository for each entity, its not recommended to implement more than a repo type per entity.
 * It may causes side effects meanwhile parallelized workload.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    boolean existsByUsernameOrEmail(String username, String email);
    Optional<UserEntity> findByUsername(String username);

}
