package com.example.policy.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetallesInmuebleDTO {
    private String direccion;
    private String tipoInmueble;
}
