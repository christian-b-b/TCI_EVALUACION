package com.tci.evaluacion.dao;

import com.tci.evaluacion.models.User;
import com.tci.evaluacion.models.UserPassword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPasswordDao extends JpaRepository<UserPassword,Long> {
    Optional<UserPassword> findUserPasswordByUserAndState(User user, Integer state);
}
