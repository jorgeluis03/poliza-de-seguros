package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetallesAutoDTO {

	private String marca;
    private String modelo;
    private int anio;
    private String numeroPlaca;
    
}
