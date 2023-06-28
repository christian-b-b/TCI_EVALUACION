package com.tci.evaluacion.service.impl;

import com.tci.evaluacion.constants.TciConstants;
import com.tci.evaluacion.dao.EmpresaDao;
import com.tci.evaluacion.models.Empresa;
import com.tci.evaluacion.service.EmpresaService;
import com.tci.evaluacion.tdos.request.EmpresaRequestDTO;
import com.tci.evaluacion.tdos.response.EmpresaResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    @Autowired
    private EmpresaDao empresaDao;


    @Override
    public List<EmpresaResponseDTO> listar() {
        List<Empresa> empresas = empresaDao.findAllByOrderByIdEmpresaDesc().stream().limit(TciConstants.MAXIMO_REGISTROS).toList();

        return empresas.stream().map(x-> EmpresaResponseDTO.builder()
                .idEmpresa(x.getIdEmpresa())
                .ruc(x.getRuc())
                .razonSocial(x.getRazonSocial())
                .direccion(x.getDireccion())
                .estado(x.getEstado()).build()).toList();
    }

    @Override
    public Empresa crear(EmpresaRequestDTO empresaDTO) {
        Empresa empresa = Empresa.builder()
                .ruc(empresaDTO.getRuc())
                .razonSocial(empresaDTO.getRazonSocial())
                .direccion(empresaDTO.getDireccion())
                .estado(TciConstants.ACTIVE_STATE)
                .build();

        return empresaDao.save(empresa);
    }


}
