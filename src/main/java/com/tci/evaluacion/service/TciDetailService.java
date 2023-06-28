package com.tci.evaluacion.service;

import com.tci.evaluacion.constants.TciConstants;
import com.tci.evaluacion.dao.UserDao;
import com.tci.evaluacion.dao.UserPasswordDao;
import com.tci.evaluacion.dao.UserRoleDao;
import com.tci.evaluacion.errorhandler.TciGenericClientException;
import com.tci.evaluacion.models.User;
import com.tci.evaluacion.models.UserPassword;
import com.tci.evaluacion.models.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TciDetailService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserPasswordDao userPasswordDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userDao.findByNameAndState(name,
                TciConstants.ACTIVE_STATE).orElseThrow(
                ()-> new TciGenericClientException("User not found",
                        HttpStatus.NOT_FOUND, null,null));

        UserPassword userPassword = userPasswordDao.findUserPasswordByUserAndState(user,
                TciConstants.ACTIVE_STATE).orElseThrow(
                ()-> new TciGenericClientException("UserPasswor not found",
                        HttpStatus.NOT_FOUND, null,null));

        List<UserRole> userRoles = userRoleDao.findUserRoleByUser(user);

        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(name)
                .password(userPassword.getPassword())
                .authorities(mapAuthorities(userRoles))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();

        return userDetails;
    }

    private List<GrantedAuthority> mapAuthorities(List<UserRole> userRoles) {
        return userRoles.stream().map(userRole -> {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
                    userRole.getRole().getCode());
            return grantedAuthority;
        }).collect(Collectors.toList());
    }

}
