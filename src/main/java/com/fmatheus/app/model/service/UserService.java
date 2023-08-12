package com.fmatheus.app.model.service;

import com.fmatheus.app.model.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService extends GenericService<User, UUID> {

    Optional<User> findByUsername(String username);

}
