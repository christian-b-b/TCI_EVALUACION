package com.tci.evaluacion.controllers;

import com.tci.evaluacion.constants.TciConstants;
import com.tci.evaluacion.service.EmpresaService;
import com.tci.evaluacion.tdos.request.EmpresaRequestDTO;
import com.tci.evaluacion.tdos.response.EmpresaResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(TciConstants.API_VERSION + TciConstants.RESOURCE_EMPRESA)
public class EmpresaController {
    @Autowired
    private EmpresaService empresaService;

    @PostMapping(TciConstants.RESOURCE_EMPRESA_REGISTRO)
    public ResponseEntity<EmpresaResponseDTO> registro(@RequestBody EmpresaRequestDTO empresaRequestDTO){
        return new ResponseEntity<EmpresaResponseDTO>(empresaService.crear(empresaRequestDTO),null, HttpStatus.OK);
    }

    @GetMapping(TciConstants.RESOURCE_EMPRESA_LISTADO)
    public ResponseEntity<List<EmpresaResponseDTO>> listado(){
        return new ResponseEntity<List<EmpresaResponseDTO>>(empresaService.listar(), null, HttpStatus.OK);
    }
}
