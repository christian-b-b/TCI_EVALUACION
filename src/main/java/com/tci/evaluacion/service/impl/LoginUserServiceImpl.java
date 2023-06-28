package com.tci.evaluacion.service.impl;

import com.tci.evaluacion.constants.TciConstants;
import com.tci.evaluacion.dao.UserDao;
import com.tci.evaluacion.dao.UserPasswordDao;
import com.tci.evaluacion.dao.UserRoleDao;
import com.tci.evaluacion.errorhandler.TciGenericClientException;
import com.tci.evaluacion.models.Role;
import com.tci.evaluacion.models.User;
import com.tci.evaluacion.models.UserPassword;
import com.tci.evaluacion.models.UserRole;
import com.tci.evaluacion.security.JwtTokenProvider;
import com.tci.evaluacion.service.LoginUserService;
import com.tci.evaluacion.tdos.request.LoginUserRequestDTO;
import com.tci.evaluacion.tdos.response.LoginUserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoginUserServiceImpl implements LoginUserService {

    @Value("${jwt.token.time}")
    private Long tokenTime;
    @Autowired
    UserDao userDao;
    @Autowired
    UserPasswordDao userPasswordDao;
    @Autowired
    UserRoleDao userRoleDao;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Override
    public LoginUserResponseDTO login(LoginUserRequestDTO loginUserRequestDTO) {
        User user = userDao.findByNameAndState(loginUserRequestDTO.getName(),
                TciConstants.ACTIVE_STATE).orElseThrow( ()-> new TciGenericClientException(
                "User not found", HttpStatus.NOT_FOUND, null, null));


        UserPassword userPassword = userPasswordDao.findUserPasswordByUserAndState(user,
                TciConstants.ACTIVE_STATE).orElseThrow(()-> new TciGenericClientException(
                "User password not found", HttpStatus.NOT_FOUND, null, null));

        if(!(userPassword.getPassword().equals(loginUserRequestDTO.getPassword()))){
            throw new TciGenericClientException("Invalid Credentials", HttpStatus.UNAUTHORIZED, null, null);
        }


        List<Role> roles = mapRoles(userRoleDao.findUserRoleByUser(user));
        String token = jwtTokenProvider.createToken(user.getName(),roles);

        LocalDateTime dateTime = LocalDateTime.now();
        LoginUserResponseDTO loginUserResponseDTO = LoginUserResponseDTO.builder()
                .tokenType(TciConstants.TOKEN_TYPE).accessToken(token)
                .expiresIn(Instant.now().toEpochMilli() + tokenTime)
                .issuedAt(dateTime.toString()).build();

        return loginUserResponseDTO;
    }

    private List<Role> mapRoles(List<UserRole> userRoles){
        return userRoles.stream().map(userRole -> userRole.getRole()).collect(Collectors.toList());
    }
}
