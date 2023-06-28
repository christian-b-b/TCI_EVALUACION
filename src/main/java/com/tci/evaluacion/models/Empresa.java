package com.tci.evaluacion.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_EMPRESA")
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEmpresa")
    private Long idEmpresa;
    @Column(name = "ruc")
    private String ruc;
    @Column(name = "razonSocial")
    private String razonSocial;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "estado")
    private Integer estado;
}
