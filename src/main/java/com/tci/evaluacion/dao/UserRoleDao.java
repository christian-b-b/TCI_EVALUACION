package com.tci.evaluacion.dao;


import com.tci.evaluacion.models.User;
import com.tci.evaluacion.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleDao extends JpaRepository<UserRole, Long> {
    List<UserRole> findUserRoleByUser(User user);
}
