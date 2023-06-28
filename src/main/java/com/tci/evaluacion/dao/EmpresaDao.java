package com.tci.evaluacion.dao;

import com.tci.evaluacion.models.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmpresaDao extends JpaRepository<Empresa,Long> {
    public List<Empresa> findAllByOrderByIdEmpresaDesc();
}
