package com.tci.evaluacion.tdos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
public class EmpresaResponseDTO {
    private Long idEmpresa;
    private String ruc;
    private String razonSocial;
    private String direccion;
    private Integer estado;
}
