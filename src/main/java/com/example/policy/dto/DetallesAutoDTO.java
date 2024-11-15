package com.example.policy.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetallesAutoDTO {
    private Integer idAuto;
    private Integer idPoliza;
	private String marca;
    private String modelo;
    private int anio;
    private String numeroPlaca;
    
}
