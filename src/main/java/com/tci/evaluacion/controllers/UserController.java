package com.tci.evaluacion.controllers;

import com.tci.evaluacion.constants.TciConstants;
import com.tci.evaluacion.service.LoginUserService;
import com.tci.evaluacion.tdos.request.LoginUserRequestDTO;
import com.tci.evaluacion.tdos.response.LoginUserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(TciConstants.API_VERSION + TciConstants.RESOURCE_USERS)
public class UserController {

    @Autowired
    private LoginUserService loginUserService;

    @PostMapping(TciConstants.RESOURCE_LOGIN)
    ResponseEntity<LoginUserResponseDTO> login(@RequestBody LoginUserRequestDTO loginUserRequestDTO){

        return new ResponseEntity<LoginUserResponseDTO>(loginUserService.login(loginUserRequestDTO),null, HttpStatus.OK);
    }

}
