package com.tci.evaluacion.service;

import com.tci.evaluacion.models.Empresa;
import com.tci.evaluacion.tdos.request.EmpresaRequestDTO;
import com.tci.evaluacion.tdos.response.EmpresaResponseDTO;


import java.util.List;

public interface EmpresaService {
    public List<EmpresaResponseDTO> listar();
    public Empresa crear(EmpresaRequestDTO empresaDTO);
}
