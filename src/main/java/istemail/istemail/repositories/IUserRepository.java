package istemail.istemail.repositories;

import istemail.istemail.models.User;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, UUID> {

    // FIND BY EMAIL
    Optional<User> findByEmail(String email);

    // FIND ALL
    @SuppressWarnings("null")
    Page<User> findAll(Pageable pageable);

    // FIND BY ID
    @SuppressWarnings("null")
    Optional<User> findById(UUID id);
}
