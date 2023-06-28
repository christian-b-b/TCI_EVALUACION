package com.tci.evaluacion.service;


import com.tci.evaluacion.tdos.request.LoginUserRequestDTO;
import com.tci.evaluacion.tdos.response.LoginUserResponseDTO;

public interface LoginUserService {
    LoginUserResponseDTO login(LoginUserRequestDTO loginUserRequestDTO);
}
