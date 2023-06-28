package com.tci.evaluacion.service;

import com.tci.evaluacion.tdos.request.EmpresaRequestDTO;
import com.tci.evaluacion.tdos.response.EmpresaResponseDTO;


import java.util.List;

public interface EmpresaService {
    public List<EmpresaResponseDTO> listar();
    public EmpresaResponseDTO crear(EmpresaRequestDTO empresaDTO);
}
