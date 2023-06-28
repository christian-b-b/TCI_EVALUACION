package com.tci.evaluacion.tdos.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmpresaRequestDTO {
    private String ruc;
    private String razonSocial;
    private String direccion;
}
