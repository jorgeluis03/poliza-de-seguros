package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PolizaInmuebleDTO {
    private String direccion;
    private String tipoInmueble;
}
