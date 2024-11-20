package istemail.istemail.services;

import istemail.istemail.models.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IUserQueryService {

    String NAME = "IUserQueryService";

    // GET USER BY ID
    User findById(UUID id);

    // GET USER BY EMAIL
    User findByEmail(String email);

    // GET ALL USERS
    Page<User> findAll(Pageable pageable);
}
