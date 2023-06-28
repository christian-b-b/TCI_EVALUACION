package com.tci.evaluacion.dao;

import com.tci.evaluacion.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDao extends JpaRepository<User,Long> {
    Optional<User> findByNameAndState(String name, Integer state);
}
