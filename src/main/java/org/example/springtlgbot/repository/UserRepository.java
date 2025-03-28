package org.example.springtlgbot.repository;

import org.example.springtlgbot.entity.Users;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Users, Long> {
}
